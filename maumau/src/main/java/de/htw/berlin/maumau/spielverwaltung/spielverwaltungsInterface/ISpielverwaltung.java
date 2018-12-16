package de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.errorHandling.KeinWunschtypException;
import de.htw.berlin.maumau.errorHandling.KeineKarteException;
import de.htw.berlin.maumau.errorHandling.KeineSpielerException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;

import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public interface ISpielverwaltung {

    /**
     * Der Punktestand der {@link Spieler} wird zurückgesetzt und ein neues Spiel wird eingeleitet.
     *
     * @param spielerliste - Die Liste der teilnehmenden Spieler
     * @return das MauMau Spiel
     */
    MauMauSpiel neuesSpielStarten(List<Spieler> spielerliste) throws KeineSpielerException;

    /**
     * Eine neue Runde wird eingeleitet.
     *
     * @param spiel - das vorhandene Spiel
     */
    void neueRundeStarten(MauMauSpiel spiel);

    /**
     * Ein {@link Spieler} zieht eine Karte von dem Kartenstapel und fügt diese seiner Hand hinzu.
     * Wenn der Kartenstapel leer ist, wird er neu erstellt.
     *
     * @param spieler      - der Spieler
     */
    void karteZiehen(Spieler spieler, MauMauSpiel spiel);

    /**
     * Ein {@link Spieler} zieht eine bestimmte Anzahl von {@link Karte} vom Kartenstapel.
     * Wenn der Kartenstapel leer ist, wird er neu erstellt.
     *
     * @param spieler      - der Spieler
     */
    void karteZiehenSonderregel(Spieler spieler, MauMauSpiel spiel);

    /**
     * @param spieler
     * @param spiel
     */
    void karteZiehenMauNichtGerufen(Spieler spieler, MauMauSpiel spiel);

    /**
     * Ein {@link Spieler} legt eine {@link Karte}. Falls es sich um einen Buben handelt, darf er einen Wunschtyp festlegen.
     *
     * @param gewaehlteKarte - die vom Spieler ausgesuchte Karte
     * @param hand           - die aktuelle Hand des Spielers
     */
    void karteLegen(Karte gewaehlteKarte, List<Karte> hand, MauMauSpiel spiel) throws KeinWunschtypException, KeineSpielerException;


    /**
     * Ermittelt die letzte {@link Karte} auf dem Ablagestapel und gibt diese zurück.
     *
     * @param ablagestapel - der Ablagestapel
     * @return die letzte Karte - die neuste Karte vom Ablagestapel
     */
    Karte letzteKarteErmitteln(List<Karte> ablagestapel) throws KeineKarteException;

    /**
     * Wenn der {@link Spieler} nur noch eine {@link Karte} auf der Hand hat, wird geprüft, ob er Mau gesagt hat.
     * Wenn ja, muss er keinen Strafzug machen und die Variable hatMauGerufen wird wieder auf false gesetzt.
     * Wenn nein, muss er zwei Karten ziehen.
     *
     * @param spieler      - der Spieler
     */
    void maumauPruefen(Spieler spieler, MauMauSpiel spiel);

    /**
     * Ein {@link Spieler} ruft Mau Mau und die Variable hatMauGerufen wird auf true gesetzt.
     *
     * @param spieler - der Spieler
     */
    void maumauRufen(Spieler spieler);

    /**
     * Der Wert der Hand des {@link Spieler}, der verloren hat, wird berechnet.
     *
     * @param hand - die Hand des Spielers
     * @return Minuswert der Hand
     */
    int minuspunkteBerechnen(List<Karte> hand);

    void wunschtypFestlegen(Kartentyp wunschtyp, MauMauSpiel spiel);
}
