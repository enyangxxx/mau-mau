package de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl;

import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.ISpielverwaltung;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class SpielverwaltungImpl implements ISpielverwaltung {
    /**
     * Der Punktestand der {@link Spieler} wird zurückgesetzt und ein neues Spiel wird eingeleitet.
     *
     * @param spielerliste - Die Liste der teilnehmenden Spieler
     * @return das MauMau Spiel
     */
    public MauMauSpiel neuesSpielStarten(List<Spieler> spielerliste) {
        return null;
    }

    /**
     * Eine neue Runde wird eingeleitet.
     *
     * @param spiel - das vorhandene Spiel
     */
    public void neueRundeStarten(MauMauSpiel spiel) {

    }

    /**
     * Ein {@link Spieler} zieht eine Karte von dem Kartenstapel und fügt diese seiner Hand hinzu.
     * Wenn der Kartenstapel leer ist, wird er neu erstellt.
     *
     * @param spieler      - der Spieler
     * @param kartenstapel - der Kartenstapel
     */
    public void karteZiehen(Spieler spieler, List<Karte> kartenstapel) {

    }

    /**
     * Ein {@link Spieler} zieht eine bestimmte Anzahl von {@link Karte} vom Kartenstapel.
     * Wenn der Kartenstapel leer ist, wird er neu erstellt.
     *
     * @param spieler      - der Spieler
     * @param kartenstapel - der Kartenstapel
     * @param anzahl       - Anzahl der zu ziehenden Karten
     */
    public void karteZiehen(Spieler spieler, List<Karte> kartenstapel, int anzahl) {

    }

    /**
     * Ein {@link Spieler} legt eine {@link Karte}. Falls es sich um einen Buben handelt, darf er einen Wunschtyp festlegen.
     *
     * @param gewaehlteKarte - die vom Spieler ausgesuchte Karte
     * @param hand           - die aktuelle Hand des Spielers
     * @param ablagestapel   - der aktuelle Ablagestapel des Spiels
     */
    public void karteLegen(Karte gewaehlteKarte, List<Karte> hand, List<Karte> ablagestapel) {

    }

    /**
     * Ermittelt die letzte {@link Karte} auf dem Ablagestapel und gibt diese zurück.
     *
     * @param ablagestapel - der Ablagestapel
     * @return die letzte Karte - die neuste Karte vom Ablagestapel
     */
    public Karte letzteKarteErmitteln(List<Karte> ablagestapel) {
        return null;
    }

    /**
     * Wenn der {@link Spieler} nur noch eine {@link Karte} auf der Hand hat, wird geprüft, ob er Mau gesagt hat.
     * Wenn ja, muss er keinen Strafzug machen und die Variable hatMauGerufen wird wieder auf false gesetzt.
     * Wenn nein, muss er zwei Karten ziehen.
     *
     * @param spieler      - der Spieler
     * @param kartenstapel - der Kartenstapel
     */
    public boolean maumauPruefen(Spieler spieler, List<Karte> kartenstapel) {
        return false;
    }

    /**
     * Ein {@link Spieler} ruft Mau Mau und die Variable hatMauGerufen wird auf true gesetzt.
     *
     * @param spieler - der Spieler
     */
    public void maumauRufen(Spieler spieler) {

    }

    /**
     * Der Wert der Hand des {@link Spieler}, der verloren hat, wird berechnet.
     *
     * @param hand - die Hand des Spielers
     * @return Minuswert der Hand
     */
    public int minuspunkteBerechnen(List<Karte> hand) {
        return 0;
    }
}
