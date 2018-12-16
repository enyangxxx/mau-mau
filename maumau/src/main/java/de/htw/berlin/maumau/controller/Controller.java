package de.htw.berlin.maumau.controller;

import ch.aplu.util.Console;
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
import de.htw.berlin.maumau.spielregeln.spielregelnInterface.ISpielregeln;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.ISpielverwaltung;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private IKartenverwaltung kartenverwaltung = (IKartenverwaltung) ConfigServiceImpl.context.getBean("kartenverwaltungimpl");
    private ISpielerverwaltung spielerverwaltung = (ISpielerverwaltung) ConfigServiceImpl.context.getBean("spielerverwaltungimpl");
    private ISpielverwaltung spielverwaltung = (ISpielverwaltung) ConfigServiceImpl.context.getBean("spielverwaltungimpl");
    private ISpielregeln spielregeln = (ISpielregeln) ConfigServiceImpl.context.getBean("spielregelnimpl");

    private View view = new View();

    private MauMauSpiel spiel;
    private Spieler aktuellerSpieler;
    private Karte karte;
    private List<Spieler> spielerliste = new ArrayList<Spieler>();
    //private List<Karte> ablagestapel = new ArrayList<Karte>();


    public boolean checkSpielIstFertig(){
        for(Spieler spieler : spiel.getSpielerListe()){
            if(spieler.getHand().isEmpty()){
                return true;
            }
        }
        return false;
    }


    public void updateViewSpielerlisteBefuellen() throws IdDuplikatException, KeineKarteException, KeineSpielerException {
        view.printWillkommen();
        int i = 0;
        while (spielerliste.size() <= 3) {
            i++;
            String userInput = view.userInputNeuerSpielerErstellen(spielerliste.size());
            if (userInput.equalsIgnoreCase("Ja")) {
                String name = view.userInputNeuerSpielerName();
                int id = view.userInputNeuerSpielerId();
                aktuellerSpieler = spielerverwaltung.spielerGenerieren(name, id, false);
                spielerverwaltung.addSpielerZurListe(aktuellerSpieler, spielerliste);
                Console.println();
            } else {
                Console.println();
                if (spielerliste.size() >= 2) {
                    break;
                } else {
                    view.printMindestanzahlSpielerNennen();
                }
            }
        }
    }


    public void updateViewSpielStarten() throws KeineSpielerException, KeineKarteException {

        if(spiel == null){
            spiel = spielverwaltung.neuesSpielStarten(spielerliste);
            view.printNeuesSpielGestartet();
        }
        if(spiel.getRunde()>1){
            spiel.getAblagestapel().removeAll(spiel.getAblagestapel());
            spiel.getKartenstapel().removeAll(spiel.getKartenstapel());
            for(Spieler spieler : spiel.getSpielerListe()){
                spieler.getHand().removeAll(spieler.getHand());
            }
        }

        spiel.setRunde(spiel.getRunde()+1);
        spiel.setKartenstapel(kartenverwaltung.kartenstapelGenerieren());
        kartenverwaltung.kartenMischen(spiel.getKartenstapel());
        spielerverwaltung.kartenAusteilen(spiel.getSpielerListe(), spiel.getKartenstapel(), spiel.getAblagestapel());
        view.printKartenAusgeteilt();

        spiel.getSpielerListe().get(0).setIstDran(true);
    }


    public void updateViewNaechsterSpielzugStarten() throws KeineSpielerException, KeineKarteException {
        for (Spieler spieler : spielerliste) {
            if (spieler.istDran()) {
                aktuellerSpieler = spielerverwaltung.getSpielerById(spieler.getS_id(), spiel.getSpielerListe());
            }
        }

        view.printSpielerGewechselt(aktuellerSpieler.getName());
        view.printHandAnzeigen(aktuellerSpieler.getHand());
        view.printLetzteKarteAblagestapel(spielverwaltung.letzteKarteErmitteln(spiel.getAblagestapel()));
    }

    public void updateViewSpielzugDurchfuehren() throws KeinWunschtypException, KeineKarteException, KeineSpielerException {
        String gewaehlteAktion = view.userInputAktionWaehlenMitMau();

        while (!(gewaehlteAktion.equalsIgnoreCase("legen") ||
                gewaehlteAktion.equalsIgnoreCase("ziehen")||
                gewaehlteAktion.equalsIgnoreCase("Mau"))) {

            view.printGebeLegenOderZiehenEin();
            gewaehlteAktion = view.userInputAktionWaehlenMitMau();
        }

        if (gewaehlteAktion.equalsIgnoreCase("Mau")){
            spielverwaltung.maumauRufen(aktuellerSpieler);
            Console.println("hatMauGerufen: "+aktuellerSpieler.hatMauGerufen());
            gewaehlteAktion = view.userInputAktionWaehlenOhneMau();
        }

            if (gewaehlteAktion.equalsIgnoreCase("legen")) {
                updateViewAktionKarteLegen();

            } else if (gewaehlteAktion.equalsIgnoreCase("ziehen")) {
                updateViewAktionKarteZiehen();
            }
    }


    public void updateViewAktionKarteLegen() throws KeinWunschtypException, KeineKarteException, KeineSpielerException {
        int anzahlKartenAlt = aktuellerSpieler.getHand().size();
        int gewaehlteKarteIndex = view.userInputKarteWaehlen();
        int id = aktuellerSpieler.getS_id();
        Karte gewaehlteKarte = aktuellerSpieler.getHand().get(gewaehlteKarteIndex);
        boolean hatMauGerufen = aktuellerSpieler.hatMauGerufen();
        Console.println("Spieler.hatMauGerufen: "+aktuellerSpieler.hatMauGerufen());

        spielverwaltung.karteLegen(aktuellerSpieler.getHand().get(gewaehlteKarteIndex), aktuellerSpieler.getHand(), spiel);

        int anzahlKartenNeu = spielerverwaltung.getSpielerById(id, spiel.getSpielerListe()).getHand().size();

        if((anzahlKartenAlt-1 == anzahlKartenNeu) && (gewaehlteKarte.getWert()== Kartenwert.BUBE)){
            String wunschtyp = view.userInputWunschtypFestlegen();
            spielverwaltung.wunschtypFestlegen(Kartentyp.valueOf(wunschtyp), spiel);
        }

        Console.println("Spieler.hatMauGerufen: "+aktuellerSpieler.hatMauGerufen());


        if(anzahlKartenAlt-1==anzahlKartenNeu){
            view.printKarteGelegt(spielerverwaltung.getSpielerById(id, spiel.getSpielerListe()), gewaehlteKarte);
        }
        else if(anzahlKartenAlt==2 && anzahlKartenNeu==3){
            view.printKarteGelegt(spielerverwaltung.getSpielerById(id, spiel.getSpielerListe()), gewaehlteKarte);
               if(!hatMauGerufen){
                   view.printStrafzugKeinMauGerufen(aktuellerSpieler);
               }

        }
        else{
            view.printKarteNichtLegbar(aktuellerSpieler.getHand().get(gewaehlteKarteIndex));
        }
    }


    public void updateViewAktionKarteZiehen() throws KeineKarteException {
        if (spiel.isSonderregelSiebenAktiv()) {
            view.printKartenGezogenSonderregel(aktuellerSpieler, spiel);
            spielverwaltung.karteZiehenSonderregel(aktuellerSpieler, spiel);

        } else {
            spielverwaltung.karteZiehen(aktuellerSpieler, spiel);
            view.printEineKarteGezogen(aktuellerSpieler);
        }
    }


    public void updateViewMinuspunkte(){
        for(Spieler spieler : spiel.getSpielerListe()){
            spieler.setPunktestand(spieler.getPunktestand() + spielverwaltung.minuspunkteBerechnen(spieler.getHand()));
        }
        view.printBerechneteMinuspunkte(spiel);
    }

    public boolean checkNeueRundeStarten() throws KeineKarteException {
        String userInput = view.userInputNeueRundeStarten();
        if(userInput.equalsIgnoreCase("ja")){
            return true;
        }else{
            return false;
        }
    }



}
