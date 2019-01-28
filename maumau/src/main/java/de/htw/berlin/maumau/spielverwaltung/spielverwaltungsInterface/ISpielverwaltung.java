package de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface;

import de.htw.berlin.maumau.errorHandling.technischeExceptions.*;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartentyp;
import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.KeinSpielerException;
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
    void neuesSpielStarten(List<Spieler> spielerliste) throws KeinSpielerException, Exception, DaoCreateException, DaoFindException;

    /**
     * Eine neue Runde wird eingeleitet.
     *
     * @param spiel - das vorhandene Spiel
     */
    void neueRundeStarten(MauMauSpiel spiel);

    /**
     * Ein {@link Spieler} zieht eine Karte von dem Kartenstapel und fügt diese seiner Hand hinzu.
     * Wenn der Kartenstapel leer ist, wird die Methode ablagestapelWiederverwenden aufgerufen.
     *
     * @param spieler - der Spieler
     * @param spiel - das aktuelle MauMau-Spiel
     */
    void karteZiehen(Spieler spieler, MauMauSpiel spiel) throws KarteNichtGezogenException, LeererStapelException;

    /**
     * Ein {@link Spieler} zieht eine bestimmte Anzahl von {@link Karte} vom Kartenstapel.
     * Wenn der Kartenstapel leer ist, wird er neu erstellt. Nach dem Ziehen wird die Anzahl
     * der zu ziehenden Karten wieder auf den Standardwert 2 gesetzt und die Regel auf inaktiv gesetzt.
     *
     * @param spieler - der Spieler
     * @param spiel - das aktuelle MauMau-Spiel
     */
    void karteZiehenSonderregel(Spieler spieler, MauMauSpiel spiel) throws KarteNichtGezogenException, LeererStapelException;

    /**
     * Diese Merhode wird verwendet, wenn der {@link Spieler} nicht Mau gerufen hat. Er zieht zwei Karten als Strafe
     * von dem Kartenstapel und fügt diese seiner Hand hinzu. Wenn der Kartenstapel leer ist, wird er neu erstellt.
     *
     * @param spieler  - der Spieler
     * @param spiel - das aktuelle MauMau-Spiel
     */
    void karteZiehenMauNichtGerufen(Spieler spieler, MauMauSpiel spiel) throws KarteNichtGezogenException, LeererStapelException;

    /**
     * Es wird überprüft, ob die gewählte Karte legbar ist. Wenn dies der Fall ist, dann wird überprüft, ob für das Spiel
     * ein Wunschtyp gesetzt wurde. Für den jeweiligen Fall werden die entsprechenden Methoden aufgerufen, sofern die Karte
     * gelegt werden kann. Falls nicht, dann passiert gar nichts.
     *
     * @param gewaehlteKarte  - die Karte, die gelegt werden soll
     */
    void karteLegen(Spieler aktuellerSpieler, Karte gewaehlteKarte) throws KeinSpielerException, KarteNichtGezogenException, LeererStapelException, Exception, DaoFindException, DaoUpdateException;


    /**
     * Ermittelt die letzte {@link Karte} auf dem Ablagestapel und gibt diese zurück.
     *
     //* @param ablagestapel - der Ablagestapel
     * @return die letzte Karte - die neueste Karte vom Ablagestapel
     */
    Karte letzteKarteErmitteln() throws DaoFindException;

    /**
     * Es wird geprüft, ob der Spieler Mau gesagt hat.
     * Wenn ja, muss er keinen Strafzug machen und die Variable isMauGerufen wird wieder auf false gesetzt.
     * Wenn nein, muss er zwei Karten ziehen, indem die Methode karteZiehenMauNichtGerufen() aufgerufen wird.
     *
     * @param spieler  - der Spieler
     * @param spiel - das aktuelle MauMau-Spiel
     */
    void maumauPruefen(Spieler spieler, MauMauSpiel spiel) throws KarteNichtGezogenException, LeererStapelException;

    /**
     * Ein {@link Spieler} ruft Mau Mau und die Variable isMauGerufen wird auf true gesetzt.
     *
     //* @param spieler - der Spieler
     */
    void maumauRufen() throws Exception, DaoUpdateException, DaoFindException;

    /**
     * Der Wert der Hand des {@link Spieler}, der verloren hat, wird berechnet.
     *
     * @param hand - die Hand des Spielers
     * @return Minuswert der Hand
     */
    int minuspunkteBerechnen(List<Karte> hand);


    /**
     * Der Wunschtyp des Spiels wird gesetzt. Nachdem dies getan wurde, wird der Spielzug des wünschenden Spielers beendet.
     *
     * @param wunschtyp - der zu setzende Wunschtyp
     * @param spiel - das aktuelle Spiel
     */
    void wunschtypFestlegen(Kartentyp wunschtyp, MauMauSpiel spiel);
}
