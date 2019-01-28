package de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl;

import de.htw.berlin.maumau.errorHandling.technischeExceptions.*;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartentyp;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartenwert;
import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.IdNichtVorhandenException;
import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.KeinSpielerException;
import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.LeereInitialeSpielerlisteException;
import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.InkorrekterStapelException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.IKartenverwaltung;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl.SpielerDao;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.ISpielerverwaltung;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielregeln.spielregelnInterface.ISpielregeln;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.ISpielverwaltung;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class SpielverwaltungImpl implements ISpielverwaltung {

    private Log log = LogFactory.getLog(SpielverwaltungImpl.class);
    private static final String KEINEKARTEN_EXCEPTION_MESSAGE = "Keine Karten sind an der Aktion beteiligt";
    private static final String NEUE_RUNDE_MESSAGE = "Neue Runde im Spiel";
    private static final String KARTE_ZIEHEN_MESSAGE = "Eine Karte wurde zogen";
    private static final String MAU_GERUFEN_MESSAGE = "MauMau wurde gerufen";
    private static final String MAU_NICHT_GERUFEN_MESSAGE = "MauMau wurde nicht gerufen";
    private static final String NEUES_SPIEL_MESSAGE = "Happy game!";
    private static final String KARTE_ABLEGEN_MESSAGE = "Karte aus Hand in den Ablagestapel gelegt";
    private static final String MAU_RUFEN_MESSAGE = "Spieler ruft MauMau!";
    private static final String MINUSPUNKTE_MESSAGE = "Der Spieler hat so viele Minuspunkte erhalten: ";
    private static final String ABLAGESTAPEL_LEER_MESSAGE = "Ablagestapel ist leer";

    private IKartenverwaltung kartenverwaltung;
    private ISpielregeln spielregeln;
    private ISpielerverwaltung spielerverwaltung;
    private MauMauSpielDao maumauspielDao;
    private SpielerDao spielerDao;


    public SpielverwaltungImpl(final IKartenverwaltung kartenverwaltungImpl, final ISpielregeln spielregelnImpl, final ISpielerverwaltung spielerverwaltungImpl, final MauMauSpielDao maumauspielDaoimpl, final SpielerDao spielerDaoImpl) {
        log.info("SpielverwaltungsImpl Konstruktor called");
        this.kartenverwaltung = kartenverwaltungImpl;
        this.spielregeln = spielregelnImpl;
        this.spielerverwaltung = spielerverwaltungImpl;
        this.maumauspielDao = maumauspielDaoimpl;
        this.spielerDao = spielerDaoImpl;
    }

    /**
     * Der Punktestand der {@link Spieler} wird zurückgesetzt und ein neues Spiel wird eingeleitet.
     *
     * @param spielerliste - Die Liste der teilnehmenden Spieler
     * @return das MauMau Spiel
     * @throws Exception - Eine Exception kann geworfen werden
     */
    public void neuesSpielStarten(List<Spieler> spielerliste) throws DaoCreateException, DaoFindException {
        if (spielerliste.isEmpty()) {
            try {
                throw new LeereInitialeSpielerlisteException("Leere Spielerliste für das Mau Mau Spiel!");
            } catch (LeereInitialeSpielerlisteException e) {
            }
        }

        MauMauSpiel spiel = new MauMauSpiel(spielerliste);
        log.info(spiel.getRunde());
        maumauspielDao.create(spiel);
        log.info("maumauspielDao.create -> " + "SpielID: " + maumauspielDao.findById(spiel.getSpielId()));
        log.info(NEUES_SPIEL_MESSAGE);
    }


    /**
     * Eine neue Runde wird eingeleitet.
     *
     * @param spiel - das vorhandene Spiel
     */
    public void neueRundeStarten(MauMauSpiel spiel) {
        spiel.setRunde(spiel.getRunde() + 1);
        log.info(NEUE_RUNDE_MESSAGE);
    }


    /**
     * Ein {@link Spieler} zieht eine Karte von dem Kartenstapel und fügt diese seiner Hand hinzu.
     * Wenn der Kartenstapel leer ist, wird die Methode ablagestapelWiederverwenden aufgerufen.
     *
     //* @param spieler - der Spieler
     //* @param spiel   - das aktuelle MauMau-Spiel
     */
    public void karteZiehen() throws KarteNichtGezogenException, LeererStapelException, Exception, DaoFindException, DaoUpdateException {
        MauMauSpiel spiel = maumauspielDao.findById(0);
        Spieler aktuellerSpieler = spielerDao.findBys_id(spielerDao.findAktuellerSpielerId());
        List<Karte> ablagestapel = maumauspielDao.findAblagestapel();
        List<Karte> kartenstapel = maumauspielDao.findKartenstapel();
        List<Karte> hand = spielerDao.findHand(aktuellerSpieler.getS_id());

        if (kartenstapel.isEmpty()) {
            kartenverwaltung.ablagestapelWiederverwenden();
            kartenstapel = maumauspielDao.findKartenstapel();
            ablagestapel = maumauspielDao.findAblagestapel();
        }

        try {
            hand.add(kartenstapel.get(0));
            aktuellerSpieler.setHand(hand);
            kartenstapel.remove(0);
        } catch (Exception e) {
            throw new KarteNichtGezogenException("Karte konnte nicht gezogen werden, weil Kartenstapel leer ist");
        }

        spielerDao.update(aktuellerSpieler);
        spiel.setKartenstapel(kartenstapel);
        spiel.setAblagestapel(ablagestapel);
        maumauspielDao.update(spiel);
        log.info(KARTE_ZIEHEN_MESSAGE);
        spielerverwaltung.spielerWechseln();

    }


    /**
     * Diese Merhode wird verwendet, wenn der {@link Spieler} nicht Mau gerufen hat. Er zieht zwei Karten als Strafe
     * von dem Kartenstapel und fügt diese seiner Hand hinzu. Wenn der Kartenstapel leer ist, wird er neu erstellt.
     *
     //* @param spieler - der Spieler
     //* @param spiel   - das aktuelle MauMau-Spiel
     */
    public void karteZiehenMauNichtGerufen() throws KarteNichtGezogenException, LeererStapelException, Exception, DaoFindException, DaoUpdateException {
        MauMauSpiel spiel = maumauspielDao.findById(0);
        Spieler spieler = spielerDao.findBys_id(spielerDao.findAktuellerSpielerId());
        List<Karte> ablagestapel = maumauspielDao.findAblagestapel();
        List<Karte> kartenstapel = maumauspielDao.findKartenstapel();
        List<Karte> hand = spielerDao.findHand(spielerDao.findAktuellerSpielerId());

        for (int i = 0; i < 2; i++) {
            if (kartenstapel.isEmpty()) {
                kartenverwaltung.ablagestapelWiederverwenden();
                kartenstapel = maumauspielDao.findKartenstapel();
                ablagestapel = maumauspielDao.findAblagestapel();
            }
            try{
                hand.add(kartenstapel.get(0));
                kartenstapel.remove(0);
                log.info(KARTE_ZIEHEN_MESSAGE);
            } catch (Exception e) {
                throw new KarteNichtGezogenException("Karte konnte nicht gezogen werden, weil Kartenstapel leer ist");
            }
        }
        spieler.setHand(hand);
        spiel.setAblagestapel(ablagestapel);
        spiel.setKartenstapel(kartenstapel);
        spielerDao.update(spieler);
        maumauspielDao.update(spiel);

    }


    /**
     * Ein {@link Spieler} zieht eine bestimmte Anzahl von {@link Karte} vom Kartenstapel.
     * Wenn der Kartenstapel leer ist, wird er neu erstellt. Nach dem Ziehen wird die Anzahl
     * der zu ziehenden Karten wieder auf 0 gesetzt und die Regel auf inaktiv gesetzt.
     *
     //* @param spieler - der Spieler
     //* @param spiel   - das aktuelle MauMau-Spiel
     */
    public void karteZiehenSonderregel() throws KarteNichtGezogenException, LeererStapelException, Exception, DaoFindException, DaoUpdateException {
        MauMauSpiel spiel = maumauspielDao.findById(0);
        Spieler aktuellerSpieler = spielerDao.findBys_id(spielerDao.findAktuellerSpielerId());
        List<Karte> ablagestapel = maumauspielDao.findAblagestapel();
        List<Karte> kartenstapel = maumauspielDao.findKartenstapel();
        List<Karte> hand = spielerDao.findHand(aktuellerSpieler.getS_id());
        int anzahl = spiel.getAnzahlSonderregelKartenZiehen();

        for (int i = 0; i < anzahl; i++) {
            if (kartenstapel.isEmpty()) {
                kartenverwaltung.ablagestapelWiederverwenden();
                kartenstapel = maumauspielDao.findKartenstapel();
                ablagestapel = maumauspielDao.findAblagestapel();
            }

            try{
                hand.add(kartenstapel.get(0));
                kartenstapel.remove(0);
            } catch (Exception e) {
                throw new KarteNichtGezogenException("Karte konnte nicht gezogen werden, weil Kartenstapel leer ist");
            }
        }

        aktuellerSpieler.setHand(hand);
        spiel.setAnzahlSonderregelKartenZiehen(0);
        spiel.setSonderregelSiebenAktiv(false);
        spielerDao.update(aktuellerSpieler);
        maumauspielDao.update(spiel);
        spielerverwaltung.spielerWechseln();

    }


    /**
     * Die gewählte Karte wird von der Hand des Spielers auf den Ablagestapel gelegt.
     *
     * @param gewaehlteKarte - die Karte, die gelegt werden soll
     //* @param hand           - die Hand des aktuellen Spielers
     //* @param spiel          - das aktuelle MauMau-Spiel
     */
    private void karteVonHandAufStapelLegen(Karte gewaehlteKarte) throws Exception, DaoFindException, DaoUpdateException {
        Spieler aktuellerSpieler = spielerDao.findBys_id(spielerDao.findAktuellerSpielerId());
        List<Karte> hand = spielerDao.findHand(aktuellerSpieler.getS_id());
        aktuellerSpieler.setHand(hand);

        MauMauSpiel spiel = maumauspielDao.findById(0);
        spiel.setAblagestapel(maumauspielDao.findAblagestapel());

        log.info("Hand size aus DB vor remove: "+spielerDao.findHand(aktuellerSpieler.getS_id()).size());
        log.info("Hand size Objekt vor remove: "+hand.size());

        //hand.remove(gewaehlteKarte);
        removeKarte(hand,gewaehlteKarte);

        log.info("Hand size nach Objekt remove: "+hand.size());
        log.info("Hand size aus DB nach remove: "+spielerDao.findHand(aktuellerSpieler.getS_id()).size());


        spiel.getAblagestapel().add(gewaehlteKarte);

        maumauspielDao.update(spiel);
        spielerDao.update(aktuellerSpieler);
        log.info("karteVonHandAufStapel Spieler ID: "+aktuellerSpieler.getS_id());
        log.info("Hand size nach update: "+spielerDao.findHand(aktuellerSpieler.getS_id()).size());
        log.info(KARTE_ABLEGEN_MESSAGE);
    }

    private void removeKarte(List<Karte> hand, Karte gewaehlteKarte){
        for(int i=0;i<hand.size();i++){
            if(hand.get(i).getTyp().equals(gewaehlteKarte.getTyp())&&hand.get(i).getWert().equals(gewaehlteKarte.getWert())){
                log.info("gleiche Karte gefunden.");
                hand.remove(hand.get(i));
            }
        }
    }

    /**
     * Es wird geprüft, ob die gelegte Karte die Sonderregel Sieben oder Ass einleitet. Falls ja, wird die Regel
     * auf aktiv gesetzt bzw. die Anzahl der zu ziehenden Karten um 2 erhöht. Falls der Spieler nach dem Legen nur noch
     * eine Karte auf der Hand hat wird überprüft, ob er vor dem Legen "Mau" gesagt hat, indem die Methode maumauPruefen()
     * aufgerufen wird.
     *
     * @param gewaehlteKarte - die gelegte Karte
     * @param hand           - die Hand des aktuellen Spielers
     * @param spiel          - das aktuelle MauMau-Spiel
     * @param id             - die Spieler ID des aktuellen Spielers
     */

    private void regelwerkUmsetzen(Karte gewaehlteKarte, List<Karte> hand, MauMauSpiel spiel, int id) throws KeinSpielerException, KarteNichtGezogenException, LeererStapelException, Exception, DaoFindException, DaoUpdateException {

        if (gewaehlteKarte.getWert().equals(Kartenwert.SIEBEN)) {
            spiel.setSonderregelSiebenAktiv(true);
            spiel.setAnzahlSonderregelKartenZiehen(spiel.getAnzahlSonderregelKartenZiehen() + 2);
        }
        if (gewaehlteKarte.getWert().equals(Kartenwert.ASS)) {
            spiel.setSonderregelAssAktiv(true);
        }
        if (hand.size() == 1) {
            maumauPruefen();
        }
    }

    /**
     * Die Spieler ID des aktuellen Spielers wird ermittelt und zurückgegeben.
     *
     * @return id - die Spieler ID
     */
    private int aktuellerSpielerIdErmitteln() throws DaoFindException {
        int id = 0;

        for (Spieler spieler : maumauspielDao.findSpielerlist()) {
            if (spieler.isDran()) {
                id = spieler.getS_id();
            }
        }

        if (id == 0) {
            try {
                throw new IdNichtVorhandenException("Default ID 0 ist aufgetreten, warum ist kein Spieler dran?");
            } catch (IdNichtVorhandenException e) {
            }
        }

        return id;
    }


    /**
     * Es wird überprüft, ob die gewählte Karte legbar ist. Wenn dies der Fall ist, dann wird überprüft, ob für das Spiel
     * ein Wunschtyp gesetzt wurde. Für den jeweiligen Fall werden die entsprechenden Methoden aufgerufen, sofern die Karte
     * gelegt werden kann. Falls nicht, dann passiert gar nichts.
     *
     * @param gewaehlteKarte - die Karte, die gelegt werden soll
     */

    public void karteLegen(Karte gewaehlteKarte) throws KeinSpielerException, KarteNichtGezogenException, LeererStapelException, Exception, DaoFindException, DaoUpdateException {
        Spieler aktuellerSpieler = spielerDao.findBys_id(spielerDao.findAktuellerSpielerId());
        List<Karte> hand = spielerDao.findHand(aktuellerSpieler.getS_id());
        aktuellerSpieler.setHand(hand);

        MauMauSpiel spiel = maumauspielDao.findById(0);

        Karte letzteKarte = maumauspielDao.findAblagestapel().get(maumauspielDao.findAblagestapel().size() - 1);
        //Karte letzteKarte = spiel.getAblagestapel().get(spiel.getAblagestapel().size() - 1);
        Kartentyp aktuellerWunschtyp = spiel.getAktuellerWunschtyp();
        int id = aktuellerSpielerIdErmitteln();

        if (((spielregeln.sonderregelEingehaltenSieben(gewaehlteKarte, letzteKarte)) ||
                (!spiel.isSonderregelSiebenAktiv())) && spielregeln.sonderregelEingehaltenBube(gewaehlteKarte, letzteKarte)) {
            if (aktuellerWunschtyp != null) {
                if (spielregeln.istLegbar(gewaehlteKarte, aktuellerWunschtyp)) {
                    karteVonHandAufStapelLegen(gewaehlteKarte);
                    regelwerkUmsetzen(gewaehlteKarte, hand, spiel, id);
                    spielerverwaltung.spielerWechseln();
                    spiel.setAktuellerWunschtyp(null);
                }
            } else {
                if (spielregeln.istLegbar(letzteKarte, gewaehlteKarte)) {
                    karteVonHandAufStapelLegen(gewaehlteKarte);
                    regelwerkUmsetzen(gewaehlteKarte, hand, spiel, id);
                    if (!(gewaehlteKarte.getWert().equals(Kartenwert.BUBE))) {
                        spielerverwaltung.spielerWechseln();
                    }
                }
            }
        }
        maumauspielDao.update(spiel);
        //spielerDao.update(aktuellerSpieler);
    }


    /**
     * Ermittelt die letzte {@link Karte} auf dem Ablagestapel und gibt diese zurück.
     *
     //* @param ablagestapel - der Ablagestapel
     * @return die letzte Karte - die neueste Karte vom Ablagestapel
     */
    public Karte letzteKarteErmitteln() throws DaoFindException {
        List<Karte> ablagestapel = maumauspielDao.findAblagestapel();
        Karte karte;

        try {

            karte = ablagestapel.get(ablagestapel.size() - 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InkorrekterStapelException(ABLAGESTAPEL_LEER_MESSAGE);
        }

        return karte;
    }


    /**
     * Es wird geprüft, ob der Spieler Mau gesagt hat.
     * Wenn ja, muss er keinen Strafzug machen und die Variable isMauGerufen wird wieder auf false gesetzt.
     * Wenn nein, muss er zwei Karten ziehen, indem die Methode karteZiehenMauNichtGerufen() aufgerufen wird.
     *
     //* @param spieler - der Spieler
     //* @param spiel   - das aktuelle MauMau-Spiel
     */
    public void maumauPruefen() throws KarteNichtGezogenException, LeererStapelException, Exception, DaoFindException, DaoUpdateException {
        Spieler spieler = spielerDao.findBys_id(spielerDao.findAktuellerSpielerId());
        if (spieler.isMauGerufen()) {
            log.info(MAU_GERUFEN_MESSAGE);
            spieler.setMauGerufen(false);
        } else {
            log.info(MAU_NICHT_GERUFEN_MESSAGE);
            karteZiehenMauNichtGerufen();
        }
        spielerDao.update(spieler);
    }

    /**
     * Ein {@link Spieler} ruft Mau Mau und die Variable isMauGerufen wird auf true gesetzt.
     *
     //* @param spieler - der Spieler
     */
    public void maumauRufen() throws Exception, DaoUpdateException, DaoFindException {
        //spieler.setMauGerufen(true);

        for (Spieler spieler : maumauspielDao.findSpielerlist()) {
            if (spielerDao.findBys_id(spieler.getS_id()).isDran()) {
                //Spieler aktuellerSpieler = spielerDao.findBys_id(spieler.getS_id());
                //aktuellerSpieler.setMauGerufen(true);
                //spielerDao.update(aktuellerSpieler);

                spielerDao.updateHatMauGerufen(true,spieler.getS_id());

                log.info(spielerDao.findBys_id(spieler.getS_id()).getName()+" Hat Mau gerufen: "+spielerDao.findBys_id(spieler.getS_id()).isMauGerufen());
            }
        }

        log.info(MAU_RUFEN_MESSAGE);
    }


    /**
     * Der Wert der Hand des {@link Spieler}, der verloren hat, wird berechnet.
     *
     * @param hand - die Hand des Spielers
     * @return Minuswert der Hand
     */
    public int minuspunkteBerechnen(List<Karte> hand) {
        int punktzahl = 0;
        for (Karte karte : hand) {
            Kartenwert wert = karte.getWert();
            switch (wert) {
                case SIEBEN:
                    punktzahl += 7;
                    break;
                case ACHT:
                    punktzahl += 8;
                    break;
                case NEUN:
                    punktzahl += 9;
                    break;
                case ZEHN:
                    punktzahl += 10;
                    break;
                case ASS:
                    punktzahl += 11;
                    break;
                case BUBE:
                    punktzahl += 2;
                    break;
                case DAME:
                    punktzahl += 3;
                    break;
                case KOENIG:
                    punktzahl += 4;
                    break;
                default:
                    punktzahl += 0;
                    break;
            }
        }
        log.info(new StringBuilder(MINUSPUNKTE_MESSAGE + punktzahl));
        return punktzahl;
    }


    /**
     * Der Wunschtyp des Spiels wird gesetzt. Nachdem dies getan wurde, wird der Spielzug des wünschenden Spielers beendet.
     *
     * @param wunschtyp - der zu setzende Wunschtyp
     //* @param spiel     - das aktuelle Spiel
     */
    public void wunschtypFestlegen(Kartentyp wunschtyp) throws Exception, DaoFindException, DaoUpdateException {
        MauMauSpiel spiel = maumauspielDao.findById(0);
        spiel.setAktuellerWunschtyp(wunschtyp);
        maumauspielDao.update(spiel);
        spielerverwaltung.spielerWechseln();
    }
}
