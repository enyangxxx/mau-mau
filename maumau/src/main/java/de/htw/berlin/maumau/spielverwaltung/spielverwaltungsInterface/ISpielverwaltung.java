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
     * @throws DaoCreateException - beim fehlerhaften Erstellen in der Dao-Klasse
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    void neuesSpielStarten(List<Spieler> spielerliste) throws DaoCreateException, DaoFindException;

    /**
     * Eine neue Runde wird eingeleitet.
     *
     */
    void neueRundeStarten() throws DaoUpdateException, DaoFindException;

    /**
     * Ein {@link Spieler} zieht eine Karte von dem Kartenstapel und fügt diese seiner Hand hinzu.
     * Wenn der Kartenstapel leer ist, wird die Methode ablagestapelWiederverwenden aufgerufen.
     *
     * @throws KarteNichtGezogenException - Wenn Karte nicht gezogen werden kann
     * @throws LeererStapelException - Wenn ein leerer Stapel nicht leer sein darf
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     */
    void karteZiehen() throws KarteNichtGezogenException, LeererStapelException, DaoFindException, DaoUpdateException;

    /**
     * Ein {@link Spieler} zieht eine bestimmte Anzahl von {@link Karte} vom Kartenstapel.
     * Wenn der Kartenstapel leer ist, wird er neu erstellt. Nach dem Ziehen wird die Anzahl
     * der zu ziehenden Karten wieder auf den Standardwert 2 gesetzt und die Regel auf inaktiv gesetzt.
     *
     * @throws KarteNichtGezogenException - Wenn Karte nicht gezogen werden kann
     * @throws LeererStapelException - Wenn ein leerer Stapel nicht leer sein darf
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     */
    void karteZiehenSonderregel() throws KarteNichtGezogenException, LeererStapelException, DaoFindException, DaoUpdateException;

    /**
     * Diese Merhode wird verwendet, wenn der {@link Spieler} nicht Mau gerufen hat. Er zieht zwei Karten als Strafe
     * von dem Kartenstapel und fügt diese seiner Hand hinzu. Wenn der Kartenstapel leer ist, wird er neu erstellt.
     *
     * @throws KarteNichtGezogenException - Wenn Karte nicht gezogen werden kann
     * @throws LeererStapelException - Wenn ein leerer Stapel nicht leer sein darf
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     */
    void karteZiehenMauNichtGerufen() throws KarteNichtGezogenException, LeererStapelException, DaoFindException, DaoUpdateException;

    /**
     * Es wird überprüft, ob die gewählte Karte legbar ist. Wenn dies der Fall ist, dann wird überprüft, ob für das Spiel
     * ein Wunschtyp gesetzt wurde. Für den jeweiligen Fall werden die entsprechenden Methoden aufgerufen, sofern die Karte
     * gelegt werden kann. Falls nicht, dann passiert gar nichts.
     *
     * @param gewaehlteKarte  - die Karte, die gelegt werden soll
     * @throws KarteNichtGezogenException - Wenn Karte nicht gezogen werden kann
     * @throws LeererStapelException - Wenn ein leerer Stapel nicht leer sein darf
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     */
    void karteLegen(Karte gewaehlteKarte) throws KarteNichtGezogenException, LeererStapelException, DaoFindException, DaoUpdateException;


    /**
     * Ermittelt die letzte {@link Karte} auf dem Ablagestapel und gibt diese zurück.
     *
     * @return die letzte Karte - die neueste Karte vom Ablagestapel
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    Karte letzteKarteErmitteln() throws DaoFindException;

    /**
     * Es wird geprüft, ob der Spieler Mau gesagt hat.
     * Wenn ja, muss er keinen Strafzug machen und die Variable isMauGerufen wird wieder auf false gesetzt.
     * Wenn nein, muss er zwei Karten ziehen, indem die Methode karteZiehenMauNichtGerufen() aufgerufen wird.
     *
     * @throws KarteNichtGezogenException - Wenn Karte nicht gezogen werden kann
     * @throws LeererStapelException - Wenn ein leerer Stapel nicht leer sein darf
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     */
    void maumauPruefen() throws KarteNichtGezogenException, LeererStapelException, DaoFindException, DaoUpdateException;

    /**
     * Ein {@link Spieler} ruft Mau Mau und die Variable isMauGerufen wird auf true gesetzt.
     *
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     */
    void maumauRufen() throws DaoUpdateException, DaoFindException;

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
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     */
    void wunschtypFestlegen(Kartentyp wunschtyp) throws DaoFindException, DaoUpdateException;
}
