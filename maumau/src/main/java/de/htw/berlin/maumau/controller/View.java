package de.htw.berlin.maumau.controller;

import ch.aplu.util.Console;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartentyp;
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

    /**
     * Willkommensnachricht wird geprintet
     */
    public void printWillkommen(){
        Console.println("Willkommen bei MauMau!\n");
    }


    /**
     * @param anzahlSpieler - aktuelle Anzahl der Spieler
     * @return input - Ja oder Nein
     */
    public String userInputNeuerSpielerErstellen(int anzahlSpieler){
        if(anzahlSpieler==0){
            Console.println("Es gibt noch keine Spieler.\n");
        }else{
            Console.println("Die Anzahl der Spieler betraegt: "+anzahlSpieler);
            Console.println();
        }

        Console.println("Soll ein neuer Spieler erstellt werden? (Ja/Nein)");
        String input = Console.readLine();
        Console.println();
        return input;
    }

    /**
     * Warnmeldung für Mindestanzahl der Spieler wird geprintet
     */
    public void printMindestanzahlSpielerNennen(){
        Console.println("Es muss mindestens 2 Spieler geben, um MauMau spielen zu können.");
    }

    /**
     * Name des Spielers wird eingelesen
     *
     * @return input - Name des Spielers
     */
    public String userInputNeuerSpielerName(){
        Console.println("Name des Spielers eingeben:");
        String input = Console.readLine();
        Console.println();
        return input;
    }

    /**
     * Message für ein neu gestartes Spiel wird geprintet
     */
    public void printNeuesSpielGestartet(){
        Console.println();
        Console.println("Ein neues Spiel wurde gestartet.");
    }


    /**
     * Message für ausgeteilte Karten wird geprintet
     */
    public void printKartenAusgeteilt(){
        Console.println();
        Console.println("Die Karten wurden ausgeteilt. Jeder Spieler hat nun 5 Karten auf der Hand.");
    }


    /**
     * Ein neuer Spieler ist dran
     *
     * @param name - Name des aktuellen Spielers
     */
    public void printSpielerGewechselt(String name){
        Console.println();
        Console.println(name+" ist jetzt dran.");
    }

    /**
     * Hand des aktuellen Spielers wird geprintet
     *
     * @param hand - Hand des aktuellen Spielers
     */
    public void printHandAnzeigen(List<Karte> hand){
        Console.println();
        Console.println("Folgende Karten sind auf deiner Hand:");
        int i = 0;
        for(Karte karte : hand){
            i++;
            Console.println(i+": "+karte.getTyp()+" "+karte.getWert());
        }
    }

    /**
     * Letzte Karte auf dem Ablagestapel wird geprintet
     *
     * @param letzteKarte - letzte Karte des Ablagestapels
     */
    public void printLetzteKarteAblagestapel(Karte letzteKarte){
        Console.println();
        Console.println("Die letzte Karte auf dem Ablagestapel ist:");
        Console.println(letzteKarte.getTyp()+" "+letzteKarte.getWert());
    }

    /**
     * Der Spieler wird aufgefordert, eine Aktion auszusuchen
     *
     * @return input - ausgesuchte Aktion
     */
    public String userInputAktionWaehlenMitMau(){
        Console.println();
        Console.println("Aktion selektieren (Legen/Ziehen/Mau)");
        String input = Console.readLine();
        return input;
    }

    /**
     * Der Spieler wird aufgefordert, eine Aktion außer Mau auszusuchen
     *
     * @return input - ausgesuchte Aktion
     */
    public String userInputAktionWaehlenOhneMau(){
        Console.println();
        Console.println("Aktion selektieren (Legen/Ziehen)");
        String input = Console.readLine();
        return input;
    }

    /**
     * Spieler wird gefragt, welche Karte er legen will
     *
     * @return
     */
    public int userInputKarteWaehlen(){
        Console.println();
        Console.println("Welche Karte willst du legen?");
        int index = Console.readInt();

        return index-1;
    }

    /**
     * Die gelegte Karte wird bestätigt, indem sie geprintet wird
     *
     * @param legenderSpieler - der legende Spieler
     * @param gewaehlteKarte - die ausgewählte Karte
     */
    public void printKarteGelegt(Spieler legenderSpieler, Karte gewaehlteKarte){
        Console.println();
        Console.println(legenderSpieler.getName() + " legt die Karte " + gewaehlteKarte.getTyp() + " " + gewaehlteKarte.getWert());
    }

    /**
     * Die Karte ist nicht legbar
     *
     * @param gewaehlteKarte - Die ausgesuchte Karte
     */
    public void printKarteNichtLegbar(Karte gewaehlteKarte){
        Console.println();
        Console.println("Die Karte "+gewaehlteKarte.getTyp() + " " + gewaehlteKarte.getWert()+" ist nicht legbar.");
    }

    /**
     * Eine Karte wird vom Spieler gezogen
     *
     * @param spieler - Der Spieler, der die Karte zieht
     */
    public void printEineKarteGezogen(Spieler spieler){
        Console.println();
        Console.println(spieler.getName() + " zieht 1 Karte.");
    }


    /**
     * Karte wird aufgrund einer zutreffenden Sonderregel gezogen
     *
     * @param spieler - der Spieler der die Karte zieht
     * @param spiel - das aktuelle Maumau Spiel
     */
    public void printKartenGezogenSonderregel(Spieler spieler, MauMauSpiel spiel){
        Console.println();
        Console.println(spieler.getName() + " zieht " + spiel.getAnzahlSonderregelKartenZiehen() + " Karten.");
    }

    /**
     * Die Meldung, dass man 2 Karten ziehen muss, weil er nicht Maumau gerufen hat
     *
     * @param spieler - der bestrafte Spieler
     */
    public void printStrafzugKeinMauGerufen(Spieler spieler){
        Console.println();
        Console.println(spieler.getName() + " zieht 2 Strafkarten, weil er nicht Mau gerufen hat.");
    }

    /**
     * Der User-Input für Wunschtyp, den man festlegen darf.
     *
     * @return input - Der gewünschte Typ
     */
    public String userInputWunschtypFestlegen(){
        Console.println();
        Console.println("Wunschtyp festlegen: \n"+Kartentyp.KREUZ+"\n"+Kartentyp.HERZ+"\n"+Kartentyp.KARO+"\n"+Kartentyp.PIK);
        Console.println();
        String input = Console.readLine();

        return input.toUpperCase();
    }

    /**
     * Die berechneten Minuspunkte als Resultat
     *
     * @param spielerliste - die aktuelle Spielerliste
     */
    public void printBerechneteMinuspunkte(List<Spieler> spielerliste){
        Console.println();
        Console.println("|  Spielername  |  ID  |  Punktzahl |");
        for(Spieler spieler : spielerliste){
            Console.println();
            Console.println("|  "+spieler.getName()+ "  |  "+ spieler.getS_id()+"  |  "+ spieler.getPunktestand() +"  |");
        }
    }

    /**
     * User-Abfrage ob er ein neue Runde starten möchte
     *
     * @return input - Die Antwort auf die Frage
     */
    public String userInputNeueRundeStarten(){
        Console.println("Neue Runde starten? (Ja/Nein)");
        String input = Console.readLine();

        return input;
    }

    /**
     * Die Fehlermeldung an entsprechender Stelle
     *
     * @param fehlerausgabe - die Fehlermeldung
     */
    public void fehlermeldungAusgabe(String fehlerausgabe) {
        Console.println(fehlerausgabe);
    }


    /**
     * User-Abfrage ob er ein neue Runde starten möchte
     *
     * @return input - Die Antwort auf die Frage
     */
    public String userInputMitOderOhneComputer(){
        Console.println("Möchtest du virtuelle Spieler integrieren? (Ja/Nein)");
        String input = Console.readLine();

        return input;
    }


    /**
     * User-Abfrage ob er ein neue Runde starten möchte
     *
     * @return input - Die Antwort auf die Frage
     */
    public int userInputWievieleComputer(){
        Console.println();
        Console.println("Wie viele virtuelle Spieler möchtest du integrieren? (Max.3)");
        int input = Console.readInt();
        Console.println();

        return input;
    }


    public void printComputerLegtWunschtypFest(Spieler spieler, String wunschtyp){
        Console.println();
        Console.println("Der Spieler "+spieler.getName()+" hat als Wunschtyp "+wunschtyp+" festgelegt.");
    }
}
