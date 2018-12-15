package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungView;

import ch.aplu.util.Console;

public class SpielerverwaltungView {

    public String userInputNeuerSpielerErstellen(){
        Console.println("Soll ein neuer Spieler erstellt werden? (j/n)");
        String s = Console.readLine();
        return s;
    }

    public String userInputNeuerSpielerName(){
        Console.println("Wie soll der neue Spieler heißen?");
        String s = Console.readLine();
        return s;
    }

    public int userInputNeuerSpielerId(){
        Console.println("Welche ID soll der neue Spieler haben?");
        int i = Console.readInt();
        return i;
    }

    public void printKartenAusgeteilt(){
        Console.println("Die Karten wurden ausgeteilt. Jeder Spieler hat nun 5 Karten auf der Hand.");
    }

    public void printSpielerGewechselt(String name){
        Console.println(name+" ist jetzt dran.");
    }

    public void printHandAnzeigen(){

    }


}
