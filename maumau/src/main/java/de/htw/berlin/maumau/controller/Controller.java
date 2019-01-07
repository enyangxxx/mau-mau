package de.htw.berlin.maumau.controller;

import de.htw.berlin.maumau.configurator.ConfigServiceImpl;
import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.errorHandling.IdDuplikatException;
import de.htw.berlin.maumau.errorHandling.KeinWunschtypException;
import de.htw.berlin.maumau.errorHandling.KeineKarteException;
import de.htw.berlin.maumau.errorHandling.KeineSpielerException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.IKartenverwaltung;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.ISpielerverwaltung;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.ISpielverwaltung;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */

public class Controller {

    private IKartenverwaltung kartenverwaltung = (IKartenverwaltung) ConfigServiceImpl.context.getBean("kartenverwaltungimpl");
    private ISpielerverwaltung spielerverwaltung = (ISpielerverwaltung) ConfigServiceImpl.context.getBean("spielerverwaltungimpl");
    private ISpielverwaltung spielverwaltung = (ISpielverwaltung) ConfigServiceImpl.context.getBean("spielverwaltungimpl");

    private View view = new View();

    private MauMauSpiel spiel;
    private Spieler aktuellerSpieler;
    private List<Spieler> spielerliste = new ArrayList<Spieler>();


    /**
     * Pruefe, ob ein Spieler keine Karte mehr auf der Hand hat.
     *
     * @return true - wenn ein Spieler keine Karte mehr auf der Hand hat.
     */
    public boolean checkSpielIstFertig() {
        for (Spieler spieler : spiel.getSpielerListe()) {
            if (spieler.getHand().isEmpty()) {
                return true;
            }
        }
        return false;
    }


    /**
     * Registriert Spieler solange, bis mindestens 2 Spieler registriert sind. sodass eine Runde MauMau gespielt werden kann.
     *
     * @throws KeineSpielerException - falls keine Spieler vorhanden sind
     * @throws IdDuplikatException   - Wenn eine ID doppelt vergeben wird
     * @throws KeineKarteException   - Wenn Keine Karte selektiert wurde
     */
    public void updateViewSpielerlisteBefuellen() throws Exception, KeineSpielerException {
        view.printWillkommen();
        int id = 0;
        while (spielerliste.size() <= 3) {
            String userInput = view.userInputNeuerSpielerErstellen(spielerliste.size());
            if (userInput.equalsIgnoreCase("Ja")) {
                String name = view.userInputNeuerSpielerName();
                id++;
                aktuellerSpieler = spielerverwaltung.spielerGenerieren(name, id, false);
                spielerverwaltung.addSpielerZurListe(aktuellerSpieler, spielerliste);
            } else {
                if (spielerliste.size() >= 2) {
                    break;
                } else {
                    view.printMindestanzahlSpielerNennen();
                }
            }
        }
    }


    /**
     * Startet ein neues Spiel, wenn es die erste Runde ist, oder startet eine neue Runde für ein bereits vorhandenes Spiel.
     *
     * @throws KeineSpielerException - falls keine Spieler vorhanden sind
     * @throws KeineKarteException   - Wenn Keine Karte selektiert wurde
     */
    public void updateViewSpielStarten() throws KeineSpielerException, Exception {

        if (spiel == null) {
            spiel = spielverwaltung.neuesSpielStarten(spielerliste);
            view.printNeuesSpielGestartet();
        }
        if (spiel.getRunde() > 1) {
            spiel.getAblagestapel().removeAll(spiel.getAblagestapel());
            spiel.getKartenstapel().removeAll(spiel.getKartenstapel());
            for (Spieler spieler : spiel.getSpielerListe()) {
                spieler.getHand().removeAll(spieler.getHand());
            }
        }

        spiel.setRunde(spiel.getRunde() + 1);
        spiel.setKartenstapel(kartenverwaltung.kartenstapelGenerieren());
        kartenverwaltung.kartenMischen(spiel.getKartenstapel());
        spielerverwaltung.kartenAusteilen(spiel.getSpielerListe(), spiel.getKartenstapel(), spiel.getAblagestapel());
        view.printKartenAusgeteilt();

        spiel.getSpielerListe().get(0).setDran(true);
    }


    /**
     * Ermittelt den aktuellen Spieler und zeigt seine Hand, die letzte Karte auf dem Ablagestapel und den Namen des Spielers an.
     *
     * @throws KeineSpielerException - falls keine Spieler vorhanden sind
     * @throws KeineKarteException   - Wenn Keine Karte selektiert wurde
     */
    public void updateViewNaechsterSpielzugStarten() throws KeineSpielerException, KeineKarteException {
        for (Spieler spieler : spielerliste) {
            if (spieler.isDran()) {
                aktuellerSpieler = spielerverwaltung.getSpielerById(spieler.getS_id(), spiel.getSpielerListe());
            }
        }

        view.printSpielerGewechselt(aktuellerSpieler.getName());
        view.printHandAnzeigen(aktuellerSpieler.getHand());
        view.printLetzteKarteAblagestapel(spielverwaltung.letzteKarteErmitteln(spiel.getAblagestapel()));
    }


    /**
     * Prüft den Userinput auf Validität und anschließen wird die Update View Aktion für "legen", "ziehen" oder "Mau"
     * eingeleitet.
     *
     * @throws KeineSpielerException  - falls keine Spieler vorhanden sind
     * @throws KeineKarteException    - Wenn Keine Karte selektiert wurde
     * @throws KeinWunschtypException - Wenn kein Wunschtyp gesetzt wurde
     */
    public void updateViewSpielzugDurchfuehren() throws KeinWunschtypException, KeineKarteException, KeineSpielerException {
        String gewaehlteAktion = view.userInputAktionWaehlenMitMau();

        while (!(gewaehlteAktion.equalsIgnoreCase("legen") ||
                gewaehlteAktion.equalsIgnoreCase("ziehen") ||
                gewaehlteAktion.equalsIgnoreCase("Mau"))) {

            view.printGebeLegenOderZiehenEin();
            gewaehlteAktion = view.userInputAktionWaehlenMitMau();
        }

        if (gewaehlteAktion.equalsIgnoreCase("Mau")) {
            spielverwaltung.maumauRufen(aktuellerSpieler);
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
            return !aktuellerSpieler.hatMauGerufen();
        }
        return false;
    }


    /**
     * Updated den View wenn eine Karte gelegt wurde. Prüft dabei, ob ein Wunschtyp festgelegt werden muss oder die Karte nicht
     * legbar ist.
     *
     * @throws KeineSpielerException  - falls keine Spieler vorhanden sind
     * @throws KeineKarteException    - Wenn Keine Karte selektiert wurde
     * @throws KeinWunschtypException - Wenn kein Wunschtyp gesetzt wurde
     */
    public void updateViewAktionKarteLegen() throws KeinWunschtypException, KeineKarteException, KeineSpielerException {
        int anzahlKartenAlt = aktuellerSpieler.getHand().size();
        int gewaehlteKarteIndex = view.userInputKarteWaehlen();
        int id = aktuellerSpieler.getS_id();
        Karte gewaehlteKarte = aktuellerSpieler.getHand().get(gewaehlteKarteIndex);

        spielverwaltung.karteLegen(aktuellerSpieler.getHand().get(gewaehlteKarteIndex), aktuellerSpieler.getHand(), spiel);

        int anzahlKartenNeu = spielerverwaltung.getSpielerById(id, spiel.getSpielerListe()).getHand().size();

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
     * @throws KeineKarteException - Wenn Keine Karte selektiert wurde
     */
    public void updateViewAktionKarteZiehen() throws KeineKarteException {
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
     * @throws KeineKarteException - Wenn Keine Karte selektiert wurde
     */
    public boolean checkNeueRundeStarten() throws KeineKarteException {
        String userInput = view.userInputNeueRundeStarten();
        return userInput.equalsIgnoreCase("ja");
    }


}
