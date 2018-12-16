package de.htw.berlin.maumau.spielverwaltung.spielverwaltungView;

import ch.aplu.util.Console;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

import java.util.Scanner;

public class SpielverwaltungView {

    //private Scanner in = new Scanner(System.in);

    public void printNeuesSpielGestartet(){
        Console.println("Ein neues Spiel wurde gestartet.");
    }

    public String userInputNeuesSpielStarten(){
        //System.out.println("Soll ein neues Spiel gestartet werden? (j/n)");
        Console.println("Soll ein neues Spiel gestartet werden? (j/n)");
        String s = Console.readLine();
        return s;
    }

    /*public void printNeuesSpielStartenResult(String userInput){
        if(userInput.equalsIgnoreCase("j")){
            System.out.println("Neues Spiel gestartet.");
        }
    }
    */

    public void printNeueRundeGestartet(){
        Console.print("Soll ein neues Spiel gestartet werden? (j/n)");
    }

    public void printKartenGezogen(Spieler spieler){
        System.out.println(spieler.getName() + " zieht 1 Karte.");
    }

    public void printKartenGezogenSonderregel(Spieler spieler, MauMauSpiel spiel){
        System.out.println(spieler.getName() + " muss " + spiel.getAnzahlSonderregelKartenZiehen() + " Karten ziehen.");
    }

    public void printKartenGezogenMauNichtGerufen(Spieler spieler){
        System.out.println(spieler.getName() + " hat nicht Mau gerufen und muss nun 1 Karte zur Strafe ziehen.");
    }

    public void printKarteGelegt(Karte gewaehlteKarte, MauMauSpiel spiel){
        Spieler aktuellerSpieler = null;

        for(Spieler spieler : spiel.getSpielerListe()){
            if(spieler.istDran()==true){
                aktuellerSpieler = spieler;
            }
        }

        System.out.println(aktuellerSpieler.getName() + " legt die Karte " + gewaehlteKarte.getTyp() + " " + gewaehlteKarte.getWert());
    }


    public void printMauMauGerufen(Spieler spieler){
        System.out.println(spieler.getName() + " hat Mau gerufen.");
    }

    /*public void printMinuspunkteBerechnet(){

    }
    */
}
