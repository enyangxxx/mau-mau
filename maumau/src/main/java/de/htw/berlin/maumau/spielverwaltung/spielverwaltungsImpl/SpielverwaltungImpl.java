package de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.errorHandling.KeinWunschtypException;
import de.htw.berlin.maumau.errorHandling.KeineKarteException;
import de.htw.berlin.maumau.errorHandling.KeineSpielerException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.IKartenverwaltung;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
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
    private static final String KEINESPIELER_EXCEPTION_MESSAGE = "Keine Spielerliste im Spiel";
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

    private MauMauSpielDao maumauspielDao = new MauMauSpielDaoImpl();

    public SpielverwaltungImpl(final IKartenverwaltung kartenverwaltungImpl, final ISpielregeln spielregelnImpl, final ISpielerverwaltung spielerverwaltungImpl) {
        log.info("SpielverwaltungsImpl Konstruktor called");
        this.kartenverwaltung = kartenverwaltungImpl;
        this.spielregeln = spielregelnImpl;
        this.spielerverwaltung = spielerverwaltungImpl;
    }

    /**
     * Der Punktestand der {@link Spieler} wird zurückgesetzt und ein neues Spiel wird eingeleitet.
     *
     * @param spielerliste - Die Liste der teilnehmenden Spieler
     * @throws KeineSpielerException - Wenn kein Spieler mit der ID gefunden wurde
     * @return das MauMau Spiel
     */
    public MauMauSpiel neuesSpielStarten(List<Spieler> spielerliste) throws KeineSpielerException, Exception {
        MauMauSpiel spiel = new MauMauSpiel(spielerliste);
        log.info(NEUES_SPIEL_MESSAGE);
        if (spielerliste.isEmpty()) {
            log.error(KEINESPIELER_EXCEPTION_MESSAGE);
            throw new KeineSpielerException(KEINESPIELER_EXCEPTION_MESSAGE);
        }
        return spiel;
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
     * @param spieler - der Spieler
     * @param spiel - das aktuelle MauMau-Spiel
     */
    public void karteZiehen(Spieler spieler, MauMauSpiel spiel) {
        List<Karte> ablagestapel = spiel.getAblagestapel();
        List<Karte> kartenstapel = spiel.getKartenstapel();
        List<Karte> hand = spieler.getHand();
        //Wenn Kartenstapel leer, nutze kartenstapelGenerieren

        if (kartenstapel.isEmpty()) {
            kartenverwaltung.ablagestapelWiederverwenden(ablagestapel, kartenstapel);
        }

        hand.add(kartenstapel.get(0));
        spieler.setHand(hand);
        kartenstapel.remove(0);
        log.info(KARTE_ZIEHEN_MESSAGE);
        spielerverwaltung.spielerWechseln(spiel);
    }


    /**
     * Diese Merhode wird verwendet, wenn der {@link Spieler} nicht Mau gerufen hat. Er zieht zwei Karten als Strafe
     * von dem Kartenstapel und fügt diese seiner Hand hinzu. Wenn der Kartenstapel leer ist, wird er neu erstellt.
     *
     * @param spieler  - der Spieler
     * @param spiel - das aktuelle MauMau-Spiel
     */
    public void karteZiehenMauNichtGerufen(Spieler spieler, MauMauSpiel spiel) {
        List<Karte> ablagestapel = spiel.getAblagestapel();
        List<Karte> kartenstapel = spiel.getKartenstapel();
        List<Karte> hand = spieler.getHand();

        if (kartenstapel.isEmpty()) {
            kartenverwaltung.ablagestapelWiederverwenden(ablagestapel, kartenstapel);
        }

        hand.add(kartenstapel.get(0));
        kartenstapel.remove(0);
        log.info(KARTE_ZIEHEN_MESSAGE);
        hand.add(kartenstapel.get(0));
        kartenstapel.remove(0);
        log.info(KARTE_ZIEHEN_MESSAGE);
        spieler.setHand(hand);
    }


    /**
     * Ein {@link Spieler} zieht eine bestimmte Anzahl von {@link Karte} vom Kartenstapel.
     * Wenn der Kartenstapel leer ist, wird er neu erstellt. Nach dem Ziehen wird die Anzahl
     * der zu ziehenden Karten wieder auf 0 gesetzt und die Regel auf inaktiv gesetzt.
     *
     * @param spieler - der Spieler
     * @param spiel - das aktuelle MauMau-Spiel
     */
    public void karteZiehenSonderregel(Spieler spieler, MauMauSpiel spiel) {
        List<Karte> ablagestapel = spiel.getAblagestapel();
        List<Karte> kartenstapel = spiel.getKartenstapel();
        List<Karte> hand = spieler.getHand();
        int anzahl = spiel.getAnzahlSonderregelKartenZiehen();

        for (int i = 0; i < anzahl; i++) {
            if (kartenstapel.isEmpty()) {
                kartenverwaltung.ablagestapelWiederverwenden(ablagestapel, kartenstapel);
            }
            hand.add(kartenstapel.get(0));
            kartenstapel.remove(0);
        }

        spieler.setHand(hand);
        spielerverwaltung.spielerWechseln(spiel);
        spiel.setAnzahlSonderregelKartenZiehen(0);
        spiel.setSonderregelSiebenAktiv(false);
    }


    /**
     * Die gewählte Karte wird von der Hand des Spielers auf den Ablagestapel gelegt.
     *
     * @param gewaehlteKarte  - die Karte, die gelegt werden soll
     * @param hand - die Hand des aktuellen Spielers
     * @param spiel - das aktuelle MauMau-Spiel
     */
    private void karteVonHandAufStapelLegen(Karte gewaehlteKarte, List<Karte> hand, MauMauSpiel spiel) {
        hand.remove(gewaehlteKarte);
        spiel.getAblagestapel().add(gewaehlteKarte);
        log.info(KARTE_ABLEGEN_MESSAGE);
    }

    /**
     * Es wird geprüft, ob die gelegte Karte die Sonderregel Sieben oder Ass einleitet. Falls ja, wird die Regel
     * auf aktiv gesetzt bzw. die Anzahl der zu ziehenden Karten um 2 erhöht. Falls der Spieler nach dem Legen nur noch
     * eine Karte auf der Hand hat wird überprüft, ob er vor dem Legen "Mau" gesagt hat, indem die Methode maumauPruefen()
     * aufgerufen wird.
     *
     * @param gewaehlteKarte  - die gelegte Karte
     * @param hand - die Hand des aktuellen Spielers
     * @param spiel - das aktuelle MauMau-Spiel
     * @param id - die Spieler ID des aktuellen Spielers
     */
    private void regelwerkUmsetzen(Karte gewaehlteKarte, List<Karte> hand, MauMauSpiel spiel, int id) throws KeineSpielerException {
        if (gewaehlteKarte.getWert().equals(Kartenwert.SIEBEN)) {
            spiel.setSonderregelSiebenAktiv(true);
            spiel.setAnzahlSonderregelKartenZiehen(spiel.getAnzahlSonderregelKartenZiehen() + 2);
        }
        if (gewaehlteKarte.getWert().equals(Kartenwert.ASS)) {
            spiel.setSonderregelAssAktiv(true);
        }
        if (hand.size() == 1) {
            maumauPruefen(spielerverwaltung.getSpielerById(id, spiel.getSpielerListe()), spiel);
        }
    }

    /**
     * Die Spieler ID des aktuellen Spielers wird ermittelt und zurückgegeben.
     *
     * @param spiel - das aktuelle MauMau-Spiel
     * @return id - die Spieler ID
     */
    private int aktuellerSpielerIdErmitteln(MauMauSpiel spiel) {
        int id = 0;

        for (Spieler spieler : spiel.getSpielerListe()) {
            if (spieler.isDran()) {
                id = spieler.getS_id();
            }
        }
        return id;
    }


    /**
     * Es wird überprüft, ob die gewählte Karte legbar ist. Wenn dies der Fall ist, dann wird überprüft, ob für das Spiel
     * ein Wunschtyp gesetzt wurde. Für den jeweiligen Fall werden die entsprechenden Methoden aufgerufen, sofern die Karte
     * gelegt werden kann. Falls nicht, dann passiert gar nichts.
     *
     * @param gewaehlteKarte  - die Karte, die gelegt werden soll
     * @param hand - die Hand des aktuellen Spielers
     * @param spiel - das aktuelle MauMau-Spiel
     */
    public void karteLegen(Karte gewaehlteKarte, List<Karte> hand, MauMauSpiel spiel) throws KeinWunschtypException, KeineSpielerException {
        Karte letzteKarte = spiel.getAblagestapel().get(spiel.getAblagestapel().size() - 1);
        Kartentyp aktuellerWunschtyp = spiel.getAktuellerWunschtyp();
        int id = aktuellerSpielerIdErmitteln(spiel);

        if (((spielregeln.sonderregelEingehaltenSieben(gewaehlteKarte, letzteKarte)) ||
                (!spiel.isSonderregelSiebenAktiv())) && spielregeln.sonderregelEingehaltenBube(gewaehlteKarte, letzteKarte)) {
            if (aktuellerWunschtyp != null) {
                if (spielregeln.istLegbar(gewaehlteKarte, aktuellerWunschtyp)) {
                    karteVonHandAufStapelLegen(gewaehlteKarte, hand, spiel);
                    regelwerkUmsetzen(gewaehlteKarte, hand, spiel, id);
                    spielerverwaltung.spielerWechseln(spiel);
                    spiel.setAktuellerWunschtyp(null);
                }
            } else {
                if (spielregeln.istLegbar(letzteKarte, gewaehlteKarte)) {
                    karteVonHandAufStapelLegen(gewaehlteKarte, hand, spiel);
                    regelwerkUmsetzen(gewaehlteKarte, hand, spiel, id);
                    if (!(gewaehlteKarte.getWert().equals(Kartenwert.BUBE))) {
                        spielerverwaltung.spielerWechseln(spiel);
                    }
                }
            }
        }
    }


    /**
     * Ermittelt die letzte {@link Karte} auf dem Ablagestapel und gibt diese zurück.
     *
     * @param ablagestapel - der Ablagestapel
     * @return die letzte Karte - die neueste Karte vom Ablagestapel
     * @throws KeineKarteException - Wenn Keine Karte selektiert wurde
     */
    public Karte letzteKarteErmitteln(List<Karte> ablagestapel) throws KeineKarteException {
        if (ablagestapel.isEmpty()) {
            log.info(KEINEKARTEN_EXCEPTION_MESSAGE);
            throw new KeineKarteException(ABLAGESTAPEL_LEER_MESSAGE);
        }
        return ablagestapel.get(ablagestapel.size() - 1);
    }


    /**
     * Es wird geprüft, ob der Spieler Mau gesagt hat.
     * Wenn ja, muss er keinen Strafzug machen und die Variable hatMauGerufen wird wieder auf false gesetzt.
     * Wenn nein, muss er zwei Karten ziehen, indem die Methode karteZiehenMauNichtGerufen() aufgerufen wird.
     *
     * @param spieler  - der Spieler
     * @param spiel - das aktuelle MauMau-Spiel
     */
    public void maumauPruefen(Spieler spieler, MauMauSpiel spiel) {
        if (spieler.hatMauGerufen()) {
            spieler.setHatMauGerufen(false);
            log.info(MAU_GERUFEN_MESSAGE);
        } else {
            log.info(MAU_NICHT_GERUFEN_MESSAGE);
            karteZiehenMauNichtGerufen(spieler, spiel);
        }
    }

    /**
     * Ein {@link Spieler} ruft Mau Mau und die Variable hatMauGerufen wird auf true gesetzt.
     *
     * @param spieler - der Spieler
     */
    public void maumauRufen(Spieler spieler) {
        spieler.setHatMauGerufen(true);
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
        log.info(new StringBuilder(MINUSPUNKTE_MESSAGE + String.valueOf(punktzahl)));
        return punktzahl;
    }


    /**
     * Der Wunschtyp des Spiels wird gesetzt. Nachdem dies getan wurde, wird der Spielzug des wünschenden Spielers beendet.
     *
     * @param wunschtyp - der zu setzende Wunschtyp
     * @param spiel - das aktuelle Spiel
     */
    public void wunschtypFestlegen(Kartentyp wunschtyp, MauMauSpiel spiel) {
        spiel.setAktuellerWunschtyp(wunschtyp);
        spielerverwaltung.spielerWechseln(spiel);
    }
}
