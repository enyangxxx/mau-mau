package de.htw.berlin.maumau.controller;

import ch.aplu.util.Console;
import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 *
 * Diese Klasse beinhaltet alle Ausgaben sowie alle Benutzereingaben,
 * die für den Controller benötigt werden.
 */


public class View {

    public void printWillkommen(){
        Console.println("Willkommen bei MauMau!\n");
    }

    public String userInputNeuerSpielerErstellen(int anzahlSpieler){
        if(anzahlSpieler==0){
            Console.println("Es gibt noch keine Spieler.\n");
        }else{
            Console.println("Die Anzahl der Spieler betraegt: "+anzahlSpieler);
            Console.println();
        }

        Console.println("Soll ein neuer Spieler erstellt werden? (Ja/Nein)");
        String s = Console.readLine();
        Console.println();
        return s;
    }

    public void printMindestanzahlSpielerNennen(){
        Console.println("Es muss mindestens 2 Spieler geben, um MauMau spielen zu können.");
    }

    public String userInputNeuerSpielerName(){
        Console.println();
        Console.println("Name des Spielers eingeben:");
        String s = Console.readLine();
        Console.println();
        return s;
    }

    public void printNeuesSpielGestartet(){
        Console.println();
        Console.println("Ein neues Spiel wurde gestartet.");
    }


    public void printKartenAusgeteilt(){
        Console.println();
        Console.println("Die Karten wurden ausgeteilt. Jeder Spieler hat nun 5 Karten auf der Hand.");
    }


    public void printSpielerGewechselt(String name){
        Console.println();
        Console.println(name+" ist jetzt dran.");
    }

    public void printHandAnzeigen(List<Karte> hand){
        Console.println();
        Console.println("Folgende Karten sind auf deiner Hand:");
        int i = 0;
        for(Karte karte : hand){
            i++;
            Console.println(i+": "+karte.getTyp()+" "+karte.getWert());
        }
    }


    public void printLetzteKarteAblagestapel(Karte letzteKarte){
        Console.println();
        Console.println("Die letzte Karte auf dem Ablagestapel ist:");
        Console.println(letzteKarte.getTyp()+" "+letzteKarte.getWert());
    }


    public String userInputAktionWaehlenMitMau(){
        Console.println();
        Console.println("Aktion selektieren (Legen/Ziehen/Mau)");
        String s = Console.readLine();
        return s;
    }

    public String userInputAktionWaehlenOhneMau(){
        Console.println();
        Console.println("Aktion selektieren (Legen/Ziehen)");
        String s = Console.readLine();
        return s;
    }

    public void printGebeLegenOderZiehenEin(){
        Console.println();
        Console.println("Schreibe entweder Legen oder Ziehen!");
    }

    public int userInputKarteWaehlen(){
        Console.println();
        Console.println("Welche Karte willst du legen?");
        int index = Console.readInt();
        return index-1;
    }

    public void printKarteGelegt(Spieler legenderSpieler, Karte gewaehlteKarte){
        Console.println();
        Console.println(legenderSpieler.getName() + " legt die Karte " + gewaehlteKarte.getTyp() + " " + gewaehlteKarte.getWert());
    }

    public void printKarteNichtLegbar(Karte gewaehlteKarte){
        Console.println();
        Console.println("Die Karte "+gewaehlteKarte.getTyp() + " " + gewaehlteKarte.getWert()+" ist nicht legbar.");
    }

    public void printEineKarteGezogen(Spieler spieler){
        Console.println();
        Console.println(spieler.getName() + " zieht 1 Karte.");
    }


    public void printKartenGezogenSonderregel(Spieler spieler, MauMauSpiel spiel){
        Console.println();
        Console.println(spieler.getName() + " zieht " + spiel.getAnzahlSonderregelKartenZiehen() + " Karten.");
    }

    public void printStrafzugKeinMauGerufen(Spieler spieler){
        Console.println();
        Console.println(spieler.getName() + " zieht 2 Strafkarten, weil er nicht Mau gerufen hat.");
    }

    public String userInputWunschtypFestlegen(){
        Console.println();
        Console.println("Wunschtyp festlegen: \n"+Kartentyp.KREUZ+"\n"+Kartentyp.HERZ+"\n"+Kartentyp.KARO+"\n"+Kartentyp.PIK);
        Console.println();
        String s = Console.readLine();;

        while(!(s.equalsIgnoreCase("Kreuz")||s.equalsIgnoreCase("Pik")||s.equalsIgnoreCase("Herz")||s.equalsIgnoreCase("Karo"))){
            Console.println("validen Wunschtyp eingeben: \n"+Kartentyp.KREUZ+"\n"+Kartentyp.HERZ+"\n"+Kartentyp.KARO+"\n"+Kartentyp.PIK);
            Console.println();
            s = Console.readLine();
        }
        return s.toUpperCase();
    }

    public void printBerechneteMinuspunkte(MauMauSpiel spiel){
        Console.println();
        Console.println("|  Spielername  |  ID  |  Punktzahl |");
        for(Spieler spieler : spiel.getSpielerListe()){
            Console.println();
            Console.println("|  "+spieler.getName()+ "  |  "+ spieler.getS_id()+"  |  "+ spieler.getPunktestand() +"  |");
        }
    }

    public String userInputNeueRundeStarten(){
        Console.println("Neue Runde starten? (Ja/Nein)");
        String s = Console.readLine();;

        while(!(s.equalsIgnoreCase("ja")||s.equalsIgnoreCase("nein"))){
            Console.println("Gib bitte Ja oder Nein ein.");
            Console.println();
            s = Console.readLine();
        }

        return s;
    }

}
