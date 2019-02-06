package de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl;

import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoCreateException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoFindException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoUpdateException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartentyp;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

import java.util.List;

public interface MauMauSpielDao {

    /**
     * Erzeugt einen Spiel-Eintrag in der Datenbank.
     *
     * @param spiel - das Spiel, das eingetragen werden soll
     * @throws DaoCreateException - beim fehlerhaften Create in der Dao-Klasse
     */
    void create(MauMauSpiel spiel) throws DaoCreateException;

    /**
     * Updatet einen Spiel-Eintrag in der Datenbank.
     *
     * @param spiel - das Spiel, das geupdatet werden soll
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     */
    void update(MauMauSpiel spiel) throws DaoUpdateException;

    /**
     * Liefert die Spielerliste des Spiels aus der Datenbank.
     *
     * @return List<Spielerliste> - die Liste von Spielern des Spiels aus der Datenbank
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    List<Spieler> findSpielerlist() throws DaoFindException;

    /**
     * Liefert den Kartenstapel des Spiels aus der Datenbank.
     *
     * @return List<Karte> - der Kartenstapel aus der Datenbank
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    List<Karte> findKartenstapel() throws DaoFindException;

    /**
     * Liefert den Ablagestapel des Spiels aus der Datenbank.
     *
     * @return List<Karte> - der Ablagestapels aus der Datenbank
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    List<Karte> findAblagestapel() throws DaoFindException;

    /**
     * Updatet den Eintrag fuer die Rundennummer des Spiels.
     *
     * @param runde - die Runde, die das Spiel annehmen soll
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     */
    void updateRunde(int runde) throws DaoUpdateException;

    /**
     * Liefert das Spiel aus der Datenbank.
     *
     * @return MauMauSpiel - das Spiel aus der Datenbank
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    MauMauSpiel findSpiel() throws DaoFindException;

    /**
     * Updatet den Status sonderregelAssAktiv des Spiels aus der Datenbank.
     *
     * @param status - der Status, den sonderregelAssAktiv annehmen soll
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     */
    void updateAssAktiv(boolean status) throws DaoUpdateException;

    /**
     * Liefert den Status von sonderregelAssAktiv des Spiels aus der Datenbank.
     *
     * @return boolean - der Status von sonderregelAssAktiv
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    boolean findAssAktivStatus() throws DaoFindException;

    /**
     * Updatet den Status sonderregelSiebenAktiv des Spiels aus der Datenbank.
     *
     * @param status - der Status, den sonderregelSiebenAktiv annehmen soll
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     */
    void updateSiebenAktiv(boolean status) throws DaoUpdateException;

    /**
     * Liefert den Status von sonderregelSiebenAktiv des Spiels aus der Datenbank.
     *
     * @return boolean - der Status von sonderregelSiebenAktiv
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    boolean findSiebenAktivStatus() throws DaoFindException;

    /**
     * Updatet den Wert von anzahlSonderregelKartenZiehen des Spiels aus der Datenbank.
     *
     * @param anzahl - der Wert, den anzahlSonderregelKartenZiehen annehmen soll
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     */
    void updateanzahlSonderregelKartenZiehen(int anzahl) throws DaoUpdateException;

    /**
     * Liefert den Wert von anzahlSonderregelKartenZiehen des Spiels aus der Datenbank.
     *
     * @return int - der Wert von anzahlSonderregelKartenZiehen
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    int findAnzahlSonderregelKartenZiehen() throws DaoFindException;

    /**
     * Liefert den Kartentyp von aktuellerWunschtyp des Spiels aus der Datenbank.
     *
     * @return Kartentyp - der aktuelle Wunschtyp des Spiels aus der Datenbank
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    Kartentyp findAktuellerWunschtyp() throws DaoFindException;

    /**
     * Updatet den Kartentyp von aktuellerWunschtyp des Spiels aus der Datenbank.
     *
     * @param wunschtyp - der Kartentyp, den aktuellerWunschtyp annehmen soll
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     */
    void updateAktuellerWunschtyp(Kartentyp wunschtyp) throws DaoUpdateException;

}
