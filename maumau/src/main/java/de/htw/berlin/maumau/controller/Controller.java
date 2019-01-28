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

        view.printSpielerGewechselt(spielerDao.findBys_id(spielerDao.findAktuellerSpielerId()).getName());

        view.printHandAnzeigen(spielerDao.findHand(spielerDao.findAktuellerSpielerId()));

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
            //return !aktuellerSpieler.isMauGerufen();
            return !spielerDao.findBys_id(alterSpielerIdErmitteln()).isMauGerufen();
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
        int anzahlKartenAlt = spielerDao.findHand(spielerDao.findAktuellerSpielerId()).size();

        int gewaehlteKarteIndex = view.userInputKarteWaehlen();
        int alterSpielerId = spielerDao.findAktuellerSpielerId();
        Karte gewaehlteKarte = spielerDao.findHand(spielerDao.findAktuellerSpielerId()).get(gewaehlteKarteIndex);
        log.info("gewaehlteKarte Typ: "+gewaehlteKarte.getTyp());
        log.info("gewaehlteKarte Wert: "+gewaehlteKarte.getWert());


        log.info("Letze Karte Wert auf Ablagestapel vor Karte legen: "+spielDao.findAblagestapel().get(spielDao.findAblagestapel().size()-1).getWert());
        log.info("Letze Karte Typ auf Ablagestapel vor Karte legen: "+spielDao.findAblagestapel().get(spielDao.findAblagestapel().size()-1).getTyp());
        spielverwaltung.karteLegen(gewaehlteKarte);
        log.info("Letze Karte Wert auf Ablagestapel vor Karte legen: "+spielDao.findAblagestapel().get(spielDao.findAblagestapel().size()-1).getWert());
        log.info("Letze Karte Typ auf Ablagestapel vor Karte legen: "+spielDao.findAblagestapel().get(spielDao.findAblagestapel().size()-1).getTyp());

        //int anzahlKartenNeu = spielerverwaltung.getSpielerById(id, spiel.getSpielerListe()).getHand().size();
        log.info("alterSpielerID: "+alterSpielerId);
        int anzahlKartenNeu = spielerDao.findHand(alterSpielerId).size();
        //log.info("Anzahl Karten Alt: "+spielerDao.findHand(aktuellerSpieler.getS_id()).size());
        log.info("Anzahl karten alt: "+anzahlKartenAlt);
        log.info("Anzahl karten neu: "+anzahlKartenNeu);

        if (anzahlKartenAlt - 1 == anzahlKartenNeu) {
            view.printKarteGelegt(spielerverwaltung.getSpielerById(alterSpielerId, spielDao.findSpielerlist()), gewaehlteKarte);
            if ((gewaehlteKarte.getWert() == Kartenwert.BUBE)) {
                spielverwaltung.wunschtypFestlegen(Kartentyp.valueOf(view.userInputWunschtypFestlegen()));
            }
        } else if (hatKarteGelegtOhneMauZuRufen(anzahlKartenAlt, anzahlKartenNeu)) {
            view.printKarteGelegt(spielerDao.findBys_id(alterSpielerId), gewaehlteKarte);
            view.printStrafzugKeinMauGerufen(spielerDao.findBys_id(alterSpielerId));
        } else {
            view.printKarteNichtLegbar(spielerDao.findHand(alterSpielerId).get(gewaehlteKarteIndex));
        }
    }


    /**
     * Updated den View wenn eine Karte gezogen wurde bzw. wenn wegen einer 7 mehrere Karten gezogen werden mussten.
     *
     */
    public void updateViewAktionKarteZiehen() throws KarteNichtGezogenException, LeererStapelException, Exception {
        MauMauSpiel spiel = spielDao.findById(0);
        if (spiel.isSonderregelSiebenAktiv()) {
            view.printKartenGezogenSonderregel(spielerDao.findBys_id(spielerDao.findAktuellerSpielerId()), spiel);
            spielverwaltung.karteZiehenSonderregel();
        } else {
            spielverwaltung.karteZiehen();
            view.printEineKarteGezogen(spielerDao.findBys_id(alterSpielerIdErmitteln()));
        }
    }


    /**
     * Berechnet die anzahl der Minuspunkte und gibt diese aus.
     */
    public void updateViewMinuspunkte() throws Exception {
        //MauMauSpiel spiel = spielDao.findById(0);
        Spieler spieler;
        /*for (Spieler player : spielDao.findSpielerlist()) {
            spieler = player;
            spieler.setPunktestand(spieler.getPunktestand() + spielverwaltung.minuspunkteBerechnen(spieler.getHand()));
        }*/

        for(int i=1; i<=spielDao.findSpielerlist().size();i++){
            spieler = spielerDao.findBys_id(i);
            spieler.setPunktestand(spieler.getPunktestand() + spielverwaltung.minuspunkteBerechnen(spielerDao.findHand(spieler.getS_id())));
            spielerDao.update(spieler);
        }

        view.printBerechneteMinuspunkte(spielDao.findSpielerlist());
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

    private int alterSpielerIdErmitteln(){
        int aktuellerSpielerID = spielerDao.findAktuellerSpielerId();
        if(spielDao.findSpielerlist().size()==aktuellerSpielerID){
            return 1;
        }
        else{
            return aktuellerSpielerID+1;
        }
    }


}
