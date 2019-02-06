package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl;

import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoCreateException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoFindException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoUpdateException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;

import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public interface SpielerDao {

    /**
     * Erzeugt einen Spieler-Eintrag in der Datenbank.
     *
     * @param spieler - der Spieler, der eingetragen werden soll
     * @throws DaoCreateException - beim fehlerhaften Create in der Dao-Klasse
     */
    void create(Spieler spieler) throws DaoCreateException;

    /**
     * Updatet einen Spieler-Eintrag in der Datenbank.
     *
     * @param spieler - der Spieler, der geupdatet werden soll
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     */
    void update(Spieler spieler) throws DaoUpdateException;

    /**
     * Liefert den Spieler-Eintrag in der Datenbank, zu dem die ID gehoert.
     *
     * @param s_id - die ID des Spielers, der gefunden werden soll
     * @return Spieler - der gefundene Spieler
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    Spieler findBys_id(int s_id) throws DaoFindException;

    /**
     * Liefert die Hand des Spielers aus der Datenbank, zu dem die ID gehoert.
     *
     * @param s_id - die ID des Spielers, dessen Hand gefunden werden soll
     * @return List<Karte> - die Hand des Spielers aus der Datenbank
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    List<Karte> findHand(int s_id) throws DaoFindException;

    /**
     * Liefert die ID desjenigen Spielers aus der Datenbank, der den Status isDran = true hat.
     *
     * @return int - ID des Spielers
     */
    int findAktuellerSpielerId();

    /**
     * Updatet den Status hatMauGerufen des Spielers mit der mitgegebenen ID.
     *
     * @param status - der Status, den hatMauGerufen annehmen soll
     * @param s_id   - die ID des Spielers
     * @throws DaoCreateException - beim fehlerhaften Create in der Dao-Klasse
     */
    void updateHatMauGerufen(boolean status, int s_id) throws DaoUpdateException;

    /**
     * Updatet den Status isDran des Spielers mit der mitgegebenen ID.
     *
     * @param status - der Status, den isDran annehmen soll
     * @param s_id   - die ID des Spielers
     * @throws DaoCreateException - beim fehlerhaften Create in der Dao-Klasse
     */
    void updateDran(boolean status, int s_id) throws DaoUpdateException;

    /**
     * Ermittelt, ob der Spieler mit der ID ein Computer ist oder nicht.
     *
     * @param s_id - die ID des Spielers, der überprüft werden soll
     * @return boolean - der Status von isComputer aus der Datenbank
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    boolean istComputer(int s_id) throws DaoFindException;

}
