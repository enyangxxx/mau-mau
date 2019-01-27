package de.htw.berlin.maumau.controller;

import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartentyp;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartenwert;
import de.htw.berlin.maumau.errorHandling.*;
import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.FalscherInputException;
import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.KeinSpielerException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.KarteNichtGezogenException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.LeererStapelException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.IKartenverwaltung;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl.SpielerDao;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl.SpielerverwaltungImpl;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.ISpielerverwaltung;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl.MauMauSpielDao;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.ISpielverwaltung;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class Controller {

    /*private IKartenverwaltung kartenverwaltung = (IKartenverwaltung) ConfigServiceImpl.context.getBean("kartenverwaltungimpl");
    private ISpielerverwaltung spielerverwaltung = (ISpielerverwaltung) ConfigServiceImpl.context.getBean("spielerverwaltungimpl");
    private ISpielverwaltung spielverwaltung = (ISpielverwaltung) ConfigServiceImpl.context.getBean("spielverwaltungimpl");
    private MauMauSpielDao spielDao = (MauMauSpielDao) ConfigServiceImpl.context.getBean("maumauspieldaoimpl");
    private SpielerDao spielerDao = (SpielerDao) ConfigServiceImpl.context.getBean("spielerdaoimpl");
*/

    @PersistenceContext
    private EntityManager entityManager;

    private Log log = LogFactory.getLog(SpielerverwaltungImpl.class);

    private IKartenverwaltung kartenverwaltung;
    private ISpielerverwaltung spielerverwaltung;
    private ISpielverwaltung spielverwaltung;
    private MauMauSpielDao spielDao;
    private SpielerDao spielerDao;


    private View view = new View();

    private MauMauSpiel spiel;
    private Spieler aktuellerSpieler;
    private List<Spieler> spielerliste = new ArrayList<Spieler>();
    private List<Karte> ablagestapel = new ArrayList<Karte>();

    public Controller(final IKartenverwaltung kartenverwaltung, final ISpielerverwaltung spielerverwaltung, final ISpielverwaltung spielverwaltung, final MauMauSpielDao spielDao, final SpielerDao spielerDao){
        this.kartenverwaltung = kartenverwaltung;
        this.spielerverwaltung = spielerverwaltung;
        this.spielverwaltung = spielverwaltung;
        this.spielDao = spielDao;
        this.spielerDao = spielerDao;
    }


    /**
     * Realisiert das MauMau spiel innerhalb einer Schleife, solange bis ein Spieler gewonnen hat.
     *
     * @throws KeinSpielerException  - falls keine Spieler vorhanden sind
     * @throws IdDuplikatException    - Wenn eine ID doppelt vergeben wird
     */
    public void run() throws KeinSpielerException, Exception, KarteNichtGezogenException, LeererStapelException {

        while (checkNeueRundeStarten()) {
            updateViewSpielStarten();
            while (!checkSpielIstFertig()) {
                updateViewNaechsterSpielzugStarten();
                updateViewSpielzugDurchfuehren();
            }
            updateViewMinuspunkte();
        }
        System.exit(0);
    }


    /**
     * Pruefe, ob ein Spieler keine Karte mehr auf der Hand hat.
     *
     * @return true - wenn ein Spieler keine Karte mehr auf der Hand hat.
     */
    public boolean checkSpielIstFertig() {
        for (Spieler spieler : spielDao.findSpielerlist()) {
            if (spielerDao.findHand(spieler.getS_id()).isEmpty()) {
                return true;
            }
        }
        return false;
    }


    /**
     * Registriert Spieler solange, bis mindestens 2 Spieler registriert sind. sodass eine Runde MauMau gespielt werden kann.
     *
     * @throws KeinSpielerException - falls keine Spieler vorhanden sind
     * @throws IdDuplikatException   - Wenn eine ID doppelt vergeben wird
     */
    public void updateViewSpielerlisteBefuellen() throws Exception {
        view.printWillkommen();

        int id = 0;
        while (spielerliste.size() <= 3) {
            String userInput = view.userInputNeuerSpielerErstellen(spielerliste.size());

            if (userInput.equalsIgnoreCase("Ja")) {
                String name = spielernamenEintragen();

                id++;
                spielerverwaltung.spielerGenerieren(name, id, false);
                spielerverwaltung.addSpielerZurListe(spielerDao.findBys_id(id), spielerliste);
            } else if(userInput.equalsIgnoreCase("Nein")) {
                if (spielerliste.size() >= 2) {
                    break;
                } else {
                    log.warn("Spieleranzahl nicht ausreichend, um das Spiel zu starten!");
                    view.printMindestanzahlSpielerNennen();
                }
            } else {
                try {
                    throw new FalscherInputException("Bitte nur Ja oder Nein eingeben");
                } catch (FalscherInputException e) {
                    view.fehlermeldungAusgabe(e.getMessage());
                }
            }
        }
    }

    public String spielernamenEintragen(){
        String name = "";
        while(name.isEmpty()){
            name = view.userInputNeuerSpielerName();
            if(name.isEmpty()){
                try {
                    throw new FalscherInputException("Spielername darf nicht blank sein!");
                } catch (FalscherInputException e) {
                    view.fehlermeldungAusgabe(e.getMessage());
                }
            }
        }
        return name;
    }


    /**
     * Startet ein neues Spiel, wenn es die erste Runde ist, oder startet eine neue Runde für ein bereits vorhandenes Spiel.
     *
     * @throws KeinSpielerException - falls keine Spieler vorhanden sind
     */
    public void updateViewSpielStarten() throws KeinSpielerException, Exception, LeererStapelException {

        if (spielDao.findById(0) == null) {
            spielverwaltung.neuesSpielStarten(spielerliste);
            view.printNeuesSpielGestartet();
        }
        if (spielDao.findById(0).getRunde() > 1) {
            /*spiel.getAblagestapel().removeAll(spiel.getAblagestapel());
            spiel.getKartenstapel().removeAll(spiel.getKartenstapel());
            for (Spieler spieler : spiel.getSpielerListe()) {
                spieler.getHand().removeAll(spieler.getHand());
            }*/
        }
        //spiel.setRunde(spiel.getRunde() + 1);
        spielDao.updateRunde(spielDao.findById(0).getRunde()+1);

        log.info("SpielDao Runde muss 2 sein, Runde ist: "+spielDao.findById(0).getRunde());

        kartenverwaltung.kartenstapelGenerieren();
        log.info("SpielDao Kartenstapel Size: "+spielDao.findKartenstapel().size());

        //kartenverwaltung.kartenMischen(spiel.getKartenstapel());
        kartenverwaltung.kartenMischen();

        log.info("erste Karte aus SpielDao: "+spielDao.findKartenstapel().get(0).getTyp()+" "+spielDao.findKartenstapel().get(0).getWert());
        log.info("zweite Karte aus SpielDao: "+spielDao.findKartenstapel().get(1).getTyp()+" "+spielDao.findKartenstapel().get(1).getWert());


        spielerverwaltung.kartenAusteilen();
        log.info("karten erfolgreich ausgeteilt");
        //spielDao.update(spiel);
        log.info("Nach dem Austeilen: SpielDao Kartenstapel Size: "+spielDao.findKartenstapel().size());

        /*for (int i=1; i<=spiel.getSpielerListe().size();i++){
            Spieler spieler = spiel.getSpielerListe().get(i-1);
            log.info("Spielerobjekt Hand Size: "+ spieler.getName()+" hat "+spieler.getHand().size() +" Karten");
            // über spielerDao machen
            log.info("SpielDao Hand Size: "+ spielDao.findSpielerlist().get(i-1).getName()+" hat "+ spielDao.findSpielerlist().get(i-1).getHand().size()+" Karten");
        }*/

        view.printKartenAusgeteilt();

        //spiel.getSpielerListe().get(0).setDran(true);

        Spieler spieler = spielerDao.findBys_id(1);
        spieler.setDran(true);
        spielerDao.update(spieler);

        log.info("Spieler 0.istDran = "+spielerDao.findBys_id(1).isDran());

    }


    /**
     * Ermittelt den aktuellen Spieler und zeigt seine Hand, die letzte Karte auf dem Ablagestapel und den Namen des Spielers an.
     *
     * @throws KeinSpielerException - falls keine Spieler vorhanden sind
     */
    public void updateViewNaechsterSpielzugStarten() throws KeinSpielerException {
        for (Spieler spieler : spielDao.findSpielerlist()) {
            if (spielerDao.findBys_id(spieler.getS_id()).isDran()) {
                //aktuellerSpieler = spielerverwaltung.getSpielerById(spieler.getS_id(), spiel.getSpielerListe());
                aktuellerSpieler = spielerDao.findBys_id(spieler.getS_id());
            }
        }

        //aktuellerSpieler = spielerDao.findAktuellerSpieler();
        log.info("aktuellerSpieler wurde gefunden: "+aktuellerSpieler.getName());

        view.printSpielerGewechselt(aktuellerSpieler.getName());
        //view.printHandAnzeigen(aktuellerSpieler.getHand());
        view.printHandAnzeigen(spielerDao.findHand(aktuellerSpieler.getS_id()));

        view.printLetzteKarteAblagestapel(spielverwaltung.letzteKarteErmitteln());
    }


    /**
     * Prüft den Userinput auf Validität und anschließen wird die Update View Aktion für "legen", "ziehen" oder "Mau"
     * eingeleitet.
     *
     * @throws KeinSpielerException  - falls keine Spieler vorhanden sind
     */
    public void updateViewSpielzugDurchfuehren() throws KeinSpielerException, KarteNichtGezogenException, LeererStapelException, Exception {
        String gewaehlteAktion = view.userInputAktionWaehlenMitMau();

        while (!(gewaehlteAktion.equalsIgnoreCase("legen") ||
                gewaehlteAktion.equalsIgnoreCase("ziehen") ||
                gewaehlteAktion.equalsIgnoreCase("Mau"))) {

            view.printGebeLegenOderZiehenEin();
            gewaehlteAktion = view.userInputAktionWaehlenMitMau();
        }

        if (gewaehlteAktion.equalsIgnoreCase("Mau")) {
            //spielverwaltung.maumauRufen(aktuellerSpieler);
            spielverwaltung.maumauRufen();
            while (!(gewaehlteAktion.equalsIgnoreCase("legen") ||
                    gewaehlteAktion.equalsIgnoreCase("ziehen"))) {
                gewaehlteAktion = view.userInputAktionWaehlenOhneMau();
            }
        }

        if (gewaehlteAktion.equalsIgnoreCase("legen")) {
            updateViewAktionKarteLegen();

        } else if (gewaehlteAktion.equalsIgnoreCase("ziehen")) {
            updateViewAktionKarteZiehen();
        }
    }


    /**
     * Checkt, ob der Spiele eine Karte gelegt hat ohne Mau zu rufen
     *
     * @param anzahlKartenAlt - die Anzahl der Karten auf der Hand vor dem Aufruf der Servicemethode "Karte legen"
     * @param anzahlKartenNeu - die Anzahl der Karten auf der Hand nach dem Aufruf der Servicemethode "Karte legen"
     * @return true - wenn er eine Karte gelegt hat ohne Mau zu rufen
     */
    private boolean hatKarteGelegtOhneMauZuRufen(int anzahlKartenAlt, int anzahlKartenNeu) {
        if (anzahlKartenAlt == 2 && anzahlKartenNeu == 3) {
            return !aktuellerSpieler.isMauGerufen();
        }
        return false;
    }


    /**
     * Updated den View wenn eine Karte gelegt wurde. Prüft dabei, ob ein Wunschtyp festgelegt werden muss oder die Karte nicht
     * legbar ist.
     *
     * @throws KeinSpielerException  - falls keine Spieler vorhanden sind
     */
    public void updateViewAktionKarteLegen() throws KeinSpielerException, KarteNichtGezogenException, LeererStapelException, Exception {
        int anzahlKartenAlt = spielerDao.findHand(aktuellerSpieler.getS_id()).size();
        int gewaehlteKarteIndex = view.userInputKarteWaehlen();
        int id = aktuellerSpieler.getS_id();
        Karte gewaehlteKarte = spielerDao.findHand(aktuellerSpieler.getS_id()).get(gewaehlteKarteIndex);

        log.info("Letze Karte Wert auf Ablagestapel vor Karte legen: "+spielDao.findAblagestapel().get(spielDao.findAblagestapel().size()-1).getWert());
        log.info("Letze Karte Typ auf Ablagestapel vor Karte legen: "+spielDao.findAblagestapel().get(spielDao.findAblagestapel().size()-1).getTyp());
        spielverwaltung.karteLegen(aktuellerSpieler, gewaehlteKarte);
        log.info("Letze Karte Wert auf Ablagestapel vor Karte legen: "+spielDao.findAblagestapel().get(spielDao.findAblagestapel().size()-1).getWert());
        log.info("Letze Karte Typ auf Ablagestapel vor Karte legen: "+spielDao.findAblagestapel().get(spielDao.findAblagestapel().size()-1).getTyp());

        //int anzahlKartenNeu = spielerverwaltung.getSpielerById(id, spiel.getSpielerListe()).getHand().size();
        int anzahlKartenNeu = spielerDao.findHand(aktuellerSpieler.getS_id()).size();

        if (anzahlKartenAlt - 1 == anzahlKartenNeu) {
            view.printKarteGelegt(spielerverwaltung.getSpielerById(id, spiel.getSpielerListe()), gewaehlteKarte);
            if ((gewaehlteKarte.getWert() == Kartenwert.BUBE)) {
                spielverwaltung.wunschtypFestlegen(Kartentyp.valueOf(view.userInputWunschtypFestlegen()), spiel);
            }
        } else if (hatKarteGelegtOhneMauZuRufen(anzahlKartenAlt, anzahlKartenNeu)) {
            view.printKarteGelegt(spielerverwaltung.getSpielerById(id, spiel.getSpielerListe()), gewaehlteKarte);
            view.printStrafzugKeinMauGerufen(aktuellerSpieler);
        } else {
            view.printKarteNichtLegbar(aktuellerSpieler.getHand().get(gewaehlteKarteIndex));
        }
    }


    /**
     * Updated den View wenn eine Karte gezogen wurde bzw. wenn wegen einer 7 mehrere Karten gezogen werden mussten.
     *
     */
    public void updateViewAktionKarteZiehen() throws KarteNichtGezogenException, LeererStapelException {
        if (spiel.isSonderregelSiebenAktiv()) {
            view.printKartenGezogenSonderregel(aktuellerSpieler, spiel);
            spielverwaltung.karteZiehenSonderregel(aktuellerSpieler, spiel);
        } else {
            spielverwaltung.karteZiehen(aktuellerSpieler, spiel);
            view.printEineKarteGezogen(aktuellerSpieler);
        }
    }


    /**
     * Berechnet die anzahl der Minuspunkte und gibt diese aus.
     */
    public void updateViewMinuspunkte() {
        for (Spieler spieler : spiel.getSpielerListe()) {
            spieler.setPunktestand(spieler.getPunktestand() + spielverwaltung.minuspunkteBerechnen(spieler.getHand()));
        }
        view.printBerechneteMinuspunkte(spiel);
    }

    /**
     * Leitet das startet einer neuen Runde ein
     *
     * @return true - wenn eine neue Runde gestartet werden soll
     */
    public boolean checkNeueRundeStarten() {
        String userInput = "";
        while(!userInput.equalsIgnoreCase("Ja")&&!userInput.equalsIgnoreCase("Nein")){
            userInput = view.userInputNeueRundeStarten();
            if(!userInput.equalsIgnoreCase("Ja")&&!userInput.equalsIgnoreCase("Nein")){
                try {
                    throw new FalscherInputException("Bitte nur Ja oder Nein eingeben");
                } catch (FalscherInputException e) {
                    view.fehlermeldungAusgabe(e.getMessage());
                }
            }
        }
        return userInput.equalsIgnoreCase("ja");
    }


}
