package de.htw.berlin.maumau.controller;

import de.htw.berlin.maumau.errorHandling.technischeExceptions.*;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartentyp;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartenwert;
import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.FalscherInputException;
import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.KeinSpielerException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.IKartenverwaltung;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl.SpielerDao;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl.SpielerverwaltungImpl;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.ISpielerverwaltung;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl.MauMauSpielDao;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.ISpielverwaltung;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;
import de.htw.berlin.maumau.virtuellerSpielerverwaltung.virtuellerSpielerverwaltungInterface.IVirtuellerSpielerverwaltung;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class Controller {

    @PersistenceContext
    private EntityManager entityManager;

    private Log log = LogFactory.getLog(SpielerverwaltungImpl.class);

    private IKartenverwaltung kartenverwaltung;
    private ISpielerverwaltung spielerverwaltung;
    private ISpielverwaltung spielverwaltung;
    private MauMauSpielDao spielDao;
    private SpielerDao spielerDao;
    private IVirtuellerSpielerverwaltung virtuellerSpielerverwaltung;
    private View view = new View();
    private List<Spieler> spielerliste = new ArrayList<Spieler>();


    public Controller(final IKartenverwaltung kartenverwaltung, final ISpielerverwaltung spielerverwaltung, final ISpielverwaltung spielverwaltung, final MauMauSpielDao spielDao, final SpielerDao spielerDao, final IVirtuellerSpielerverwaltung virtuellerSpielerverwaltungimpl) {
        this.kartenverwaltung = kartenverwaltung;
        this.spielerverwaltung = spielerverwaltung;
        this.spielverwaltung = spielverwaltung;
        this.spielDao = spielDao;
        this.spielerDao = spielerDao;
        this.virtuellerSpielerverwaltung = virtuellerSpielerverwaltungimpl;
    }


    /**
     * Realisiert das MauMau spiel innerhalb einer Schleife, solange bis ein Spieler gewonnen hat.
     *

     * @throws LeererStapelException - Wenn ein leerer Stapel nicht leer sein darf
     * @throws DaoCreateException - beim fehlerhaften Erstellen in der Dao-Klasse
     * @throws KeinSpielerException - wenn kein Spieler vorhanden ist
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     * @throws KarteNichtGezogenException - Wenn Karte auf fehlerhafter Weise nicht gezogen werden konnte
     */
    public void run() throws LeererStapelException, DaoCreateException, KeinSpielerException, DaoUpdateException, DaoFindException, KarteNichtGezogenException {

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
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    public boolean checkSpielIstFertig() throws DaoFindException {
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
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     * @throws DaoCreateException - beim fehlerhaften Erstellen in der Dao-Klasse
     */
    public void updateViewSpielerlisteBefuellen() throws DaoFindException, DaoCreateException {
        view.printWillkommen();
        String userInputMitOderOhneComputer = view.userInputMitOderOhneComputer();

        int spielerID = 0;

        if(userInputMitOderOhneComputer.equalsIgnoreCase("Ja")){
            int userInputWievieleComputer = view.userInputWievieleComputer();
            for(int i=0;i<userInputWievieleComputer;i++){
                spielerID++;
                spielerverwaltung.spielerGenerieren("Computer"+spielerID, spielerID, true);
                spielerverwaltung.addSpielerZurListe(spielerDao.findBys_id(spielerID), spielerliste);
                log.info("Spielerliste.size: "+spielerliste.size());
            }
        }

        while (spielerliste.size() <= 3) {
            String userInput = view.userInputNeuerSpielerErstellen(spielerliste.size());
            if (userInput.equalsIgnoreCase("Ja")) {
                String name = spielernamenEintragen();

                spielerID++;
                spielerverwaltung.spielerGenerieren(name, spielerID, false);
                spielerverwaltung.addSpielerZurListe(spielerDao.findBys_id(spielerID), spielerliste);
            } else if (userInput.equalsIgnoreCase("Nein")) {
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


    /**
     * Spielername wird validiert und eingetragen
     *
     * @return name - valider Spielername
     */
    public String spielernamenEintragen(){
        String name = "";
        while (name.isEmpty()) {
            name = view.userInputNeuerSpielerName();
            if (name.isEmpty()) {
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
     * @throws LeererStapelException - wenn ein leerer Stapel nicht leer sein darf
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     * @throws DaoCreateException - beim fehlerhaften Erstellen in der Dao-Klasse
     */
    public void updateViewSpielStarten() throws KeinSpielerException, LeererStapelException, DaoUpdateException, DaoFindException, DaoCreateException {

        if (spielDao.findSpiel() == null) {
            spielverwaltung.neuesSpielStarten(spielDao.findSpielerlist());
            view.printNeuesSpielGestartet();
        }
        if (spielDao.findSpiel().getRunde() > 1) {
            kartenResetten();
            spielerDao.updateDran(false,spielerDao.findAktuellerSpielerId());
        }

        spielDao.updateRunde(spielDao.findSpiel().getRunde() + 1);
        kartenverwaltung.kartenstapelGenerieren();
        kartenverwaltung.kartenMischen();
        spielerverwaltung.kartenAusteilen();
        view.printKartenAusgeteilt();
        spielerDao.updateDran(true, 1);
    }

    private void kartenResetten() throws DaoFindException, DaoUpdateException {
        MauMauSpiel spiel = spielDao.findSpiel();
        List<Karte> ablagestapel = new ArrayList<Karte>();
        List<Karte> kartenstapel = new ArrayList<Karte>();
        List<Karte> hand = new ArrayList<Karte>();
        List<Spieler> spielerList = spielDao.findSpielerlist();

        for (Spieler spieler : spielerList) {
            spieler.setHand(hand);
            spielerDao.update(spieler);
        }
        spiel.setAblagestapel(ablagestapel);
        spiel.setKartenstapel(kartenstapel);
        spiel.setSpielerListe(spielerList);
        spielDao.update(spiel);
    }


    /**
     * Ermittelt den aktuellen Spieler und zeigt seine Hand, die letzte Karte auf dem Ablagestapel und den Namen des Spielers an.
     *
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */

    public void updateViewNaechsterSpielzugStarten() throws DaoFindException {
        view.printSpielerGewechselt(spielerDao.findBys_id(spielerDao.findAktuellerSpielerId()).getName());

        if(!spielerDao.findBys_id(spielerDao.findAktuellerSpielerId()).isIstComputer()){
            view.printHandAnzeigen(spielerDao.findHand(spielerDao.findAktuellerSpielerId()));
        }
        //view.printHandAnzeigen(spielerDao.findHand(spielerDao.findAktuellerSpielerId()));

        view.printLetzteKarteAblagestapel(spielverwaltung.letzteKarteErmitteln());
    }


    /**
     * Prüft den Userinput auf Validität und anschließen wird die Update View Aktion für "legen", "ziehen" oder "Mau"
     * eingeleitet.
     * @throws KeinSpielerException  - falls keine Spieler vorhanden sind
     * @throws KarteNichtGezogenException - wenn der Spieler keine Karte ziehen konnte
     * @throws LeererStapelException - wenn ein leerer Stapel nicht leer sein darf
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     */
    public void updateViewSpielzugDurchfuehren() throws KeinSpielerException, KarteNichtGezogenException, LeererStapelException, DaoFindException, DaoUpdateException {
        if(spielerDao.istComputer(spielerDao.findAktuellerSpielerId())){
           spielzugDurchfuehrenComputer();
        }
        else {
            spielzugDurchfuehren();
        }
    }


    /**
     * Checkt, ob der Spiele eine Karte gelegt hat ohne Mau zu rufen
     *
     * @param anzahlKartenAlt - die Anzahl der Karten auf der Hand vor dem Aufruf der Servicemethode "Karte legen"
     * @param anzahlKartenNeu - die Anzahl der Karten auf der Hand nach dem Aufruf der Servicemethode "Karte legen"
     * @return true - wenn er eine Karte gelegt hat ohne Mau zu rufen
     *
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    private boolean hatKarteGelegtOhneMauZuRufen(int anzahlKartenAlt, int anzahlKartenNeu) throws DaoFindException {
        if (anzahlKartenAlt == 2 && anzahlKartenNeu == 3) {
            return !spielerDao.findBys_id(alterSpielerIdErmitteln()).isMauGerufen();
        }
        return false;
    }


    /**
     * Updated den View wenn eine Karte gelegt wurde. Prüft dabei, ob ein Wunschtyp festgelegt werden muss oder die Karte nicht
     * legbar ist
     * @throws KeinSpielerException  - falls keine Spieler vorhanden sind
     * @throws KarteNichtGezogenException - wenn der Spieler keine Karte ziehen konnte
     * @throws LeererStapelException - wenn ein leerer Stapel nicht leer sein darf
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     */

    public void updateViewAktionKarteLegen() throws KeinSpielerException, KarteNichtGezogenException, LeererStapelException, DaoFindException, DaoUpdateException {
        int anzahlKartenAlt = spielerDao.findHand(spielerDao.findAktuellerSpielerId()).size();
        int gewaehlteKarteIndex;
        boolean falscherIndex;
        do{
            try {
                gewaehlteKarteIndex = view.userInputKarteWaehlen();
            }catch(NumberFormatException e){
                view.fehlermeldungAusgabe("Du Trottel!");
                gewaehlteKarteIndex = -1;
            }

            falscherIndex = (gewaehlteKarteIndex >= spielerDao.findHand(spielerDao.findAktuellerSpielerId()).size()) || (gewaehlteKarteIndex<0);
            if(falscherIndex){
                try{
                    throw new FalscherInputException("Karte mit eingegebenem Index existiert nicht.");
                }catch(FalscherInputException e){
                    view.fehlermeldungAusgabe(e.getMessage());
                }
            }
        }while(falscherIndex);


        int alterSpielerId = spielerDao.findAktuellerSpielerId();
        Karte gewaehlteKarte = spielerDao.findHand(spielerDao.findAktuellerSpielerId()).get(gewaehlteKarteIndex);

        spielverwaltung.karteLegen(gewaehlteKarte);

        int anzahlKartenNeu = spielerDao.findHand(alterSpielerId).size();

        if (anzahlKartenAlt - 1 == anzahlKartenNeu) {
            view.printKarteGelegt(spielerverwaltung.getSpielerById(alterSpielerId, spielDao.findSpielerlist()), gewaehlteKarte);
            if ((gewaehlteKarte.getWert() == Kartenwert.BUBE)) {
                boolean wunschtypGesetzt;
                String wunschtyp;
                do {
                    wunschtyp = view.userInputWunschtypFestlegen();
                    if(!(wunschtyp.equalsIgnoreCase("Kreuz")||wunschtyp.equalsIgnoreCase("Pik")||wunschtyp.equalsIgnoreCase("Herz")||wunschtyp.equalsIgnoreCase("Karo"))){
                        try{
                            throw new FalscherInputException("Bitte einen validen Wunschtyp aussuchen!");
                        }catch(FalscherInputException e){
                            view.fehlermeldungAusgabe(e.getMessage());
                            wunschtypGesetzt = true;
                        }
                    }else{
                        wunschtypGesetzt = false;
                    }
                }while(wunschtypGesetzt);
                spielverwaltung.wunschtypFestlegen(Kartentyp.valueOf(wunschtyp));
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
     * @throws KarteNichtGezogenException - wenn der Spieler keine Karte ziehen konnte
     * @throws LeererStapelException - wenn ein leerer Stapel nicht leer sein darf
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     */
    public void updateViewAktionKarteZiehen() throws KarteNichtGezogenException, LeererStapelException, DaoFindException, DaoUpdateException {
        MauMauSpiel spiel = spielDao.findSpiel();

        if (spiel.isSonderregelSiebenAktiv()) {
            view.printKartenGezogenSonderregel(spielerDao.findBys_id(spielerDao.findAktuellerSpielerId()), spielDao.findAnzahlSonderregelKartenZiehen());
            spielverwaltung.karteZiehenSonderregel();
        } else {
            spielverwaltung.karteZiehen();
            view.printEineKarteGezogen(spielerDao.findBys_id(alterSpielerIdErmitteln()));
        }
    }


    /**
     * Berechnet die anzahl der Minuspunkte und gibt diese aus.
     *
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     */

    public void updateViewMinuspunkte() throws DaoFindException, DaoUpdateException {
        Spieler spieler;

        for (int i = 1; i <= spielDao.findSpielerlist().size(); i++) {
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
        String userInput = view.userInputNeueRundeStarten();
        while (!userInput.equalsIgnoreCase("Ja") && !userInput.equalsIgnoreCase("Nein")) {
            try {
                throw new FalscherInputException("Bitte nur Ja oder Nein eingeben");
            } catch (FalscherInputException e) {
                view.fehlermeldungAusgabe(e.getMessage());
                userInput = view.userInputNeueRundeStarten();
            }
        }
        return userInput.equalsIgnoreCase("ja");
    }

    /**
     * ID des Spielers vom vorherigen Spielzug wird ermittelt
     *
     * @return ID des vorherigen Spielers
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    private int alterSpielerIdErmitteln() throws DaoFindException {
        int aktuellerSpielerID = spielerDao.findAktuellerSpielerId();

        if(aktuellerSpielerID==1){
            return spielDao.findSpielerlist().size();
        }
        else{
            return aktuellerSpielerID-1;
        }
    }


    private void spielzugDurchfuehrenComputer() throws DaoFindException, LeererStapelException, KarteNichtGezogenException, DaoUpdateException {
        int anzahlKartenHandAlt = spielerDao.findHand(spielerDao.findAktuellerSpielerId()).size();
        MauMauSpiel spiel = spielDao.findSpiel();
        Kartentyp wunschtypAlt = spiel.getAktuellerWunschtyp();
        int alterSpielerId = spielerDao.findAktuellerSpielerId();

        int alteAnzahlSonderregelKartenZiehen = spielDao.findAnzahlSonderregelKartenZiehen();
        virtuellerSpielerverwaltung.spielzugDurchfuehren();

        int anzahlKartenHandNeu = spielerDao.findHand(alterSpielerId).size();
        spiel = spielDao.findSpiel();
        Kartentyp wunschtypNeu = spiel.getAktuellerWunschtyp();

        if(anzahlKartenHandAlt>anzahlKartenHandNeu){
            view.printKarteGelegt(spielerDao.findBys_id(alterSpielerId),spielverwaltung.letzteKarteErmitteln());

            if(wunschtypAlt!=wunschtypNeu&&wunschtypNeu!=null){
                view.printComputerLegtWunschtypFest(spielerDao.findBys_id(alterSpielerId),spiel.getAktuellerWunschtyp().toString());
            }
        }else if(anzahlKartenHandAlt==anzahlKartenHandNeu-1){
            view.printEineKarteGezogen(spielerDao.findBys_id(alterSpielerId));
        }else if(anzahlKartenHandAlt<anzahlKartenHandNeu){
            view.printKartenGezogenSonderregel(spielerDao.findBys_id(alterSpielerId),alteAnzahlSonderregelKartenZiehen);
        }

        view.printHandAnzeigen(spielerDao.findHand(alterSpielerId));
    }

    private void spielzugDurchfuehren() throws DaoUpdateException, DaoFindException, KeinSpielerException, KarteNichtGezogenException, LeererStapelException {
        String gewaehlteAktion = view.userInputAktionWaehlenMitMau();

        while (!(gewaehlteAktion.equalsIgnoreCase("legen") ||
                gewaehlteAktion.equalsIgnoreCase("ziehen") ||
                gewaehlteAktion.equalsIgnoreCase("Mau"))) {
            try {
                throw new FalscherInputException("Schreibe entweder Legen oder Ziehen oder Mau!");
            } catch (FalscherInputException e) {
                view.fehlermeldungAusgabe(e.getMessage());
                gewaehlteAktion = view.userInputAktionWaehlenMitMau();
            }
        }

        if (gewaehlteAktion.equalsIgnoreCase("Mau")) {
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


}
