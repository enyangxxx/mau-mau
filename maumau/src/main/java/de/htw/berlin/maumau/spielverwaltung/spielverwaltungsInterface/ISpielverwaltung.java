package de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface;

import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsImpl.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl.MauMauSpiel;

import java.util.List;

public interface ISpielverwaltung {

    /**
     * Der Punktestand der {@link Spieler} wird zurückgesetzt und ein neues Spiel wird eingeleitet.
     *
     * @param spielerliste
     * @return das MauMau Spiel
     */
    MauMauSpiel neuesSpielStarten(List<Spieler> spielerliste);

    /**
     * Eine neue Runde wird eingeleitet.
     *
     * @param spiel
     */
    void neueRundeStarten(MauMauSpiel spiel);

    /**
     * Ein {@link Spieler} zieht eine Karte von dem Kartenstapel und fügt diese seiner Hand hinzu.
     * Wenn der Kartenstapel leer ist, wird er neu erstellt.
     *
     * @param spieler
     * @param kartenstapel
     */
    void karteZiehen(Spieler spieler, List<Karte> kartenstapel);

    /**
     * Ein {@link Spieler} zieht eine bestimmte Anzahl von {@link Karte} vom Kartenstapel.
     * Wenn der Kartenstapel leer ist, wird er neu erstellt.
     *
     * @param spieler
     * @param kartenstapel
     * @param anzahl
     */
    void karteZiehen(Spieler spieler, List<Karte> kartenstapel, int anzahl);

    /**
     * Ein {@link Spieler} legt eine {@link Karte}. Falls es sich um einen Buben handelt, darf er einen Wunschtyp festlegen.
     *
     * @param hand         - die aktuelle Hand des Spielers
     * @param ablagestapel - der aktuelle Ablagestapel des Spiels
     */
    void karteLegen(List<Karte> hand, List<Karte> ablagestapel);

    /**
     * Ermittelt die letzte {@link Karte} auf dem Ablagestapel und gibt diese zurück.
     *
     * @param ablagestapel
     * @return die letzte Karte
     */
    Karte letzteKarteErmitteln(List<Karte> ablagestapel);

    /**
     * Wenn der {@link Spieler} nur noch eine {@link Karte} auf der Hand hat, wird geprüft, ob er Mau gesagt hat.
     * Wenn ja, muss er keinen Strafzug machen und die Variable hatMauGerufen wird wieder auf false gesetzt.
     * Wenn nein, muss er zwei Karten ziehen.
     *
     * @param spieler
     * @param kartenstapel
     */
    void maumauPruefen(Spieler spieler, List<Karte> kartenstapel);

    /**
     * Ein {@link Spieler} ruft Mau Mau und die Variable hatMauGerufen wird auf true gesetzt.
     *
     * @param spieler
     */
    void maumauRufen(Spieler spieler);

    /**
     * Der Wert der Hand des {@link Spieler}, der verloren hat, wird berechnet.
     *
     * @param spieler
     * @return Minuswert der Hand
     */
    int minuspunkteBerechnen(Spieler spieler);
}
