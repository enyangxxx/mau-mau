package de.htw.berlin.maumau.virtuellerSpielerverwaltung.virtuellerSpielerverwaltungInterface;

import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoFindException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoUpdateException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.KarteNichtGezogenException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.LeererStapelException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

import java.util.List;

public interface IVirtuellerSpielerverwaltung {

    /**
     * Der Computer versucht eine Karte zu legen. Wenn dies nicht möglich ist, zieht er eine Karte. Falls die Spielregeln
     * es angeben, zieht er mehrere Karten.
     *
     * @throws DaoFindException           - beim fehlerhaften Lesen in der Dao-Klasse
     * @throws LeererStapelException      - wenn der Stapel leer ist, aber nicht leer sein sollte
     * @throws KarteNichtGezogenException - wenn der Spieler auf fehlerhafte Weise keine Karte zieht
     * @throws DaoUpdateException         - beim fehlerhaften Updaten in der Dao-Klasse
     */
    void spielzugDurchfuehren() throws DaoFindException, LeererStapelException, KarteNichtGezogenException, DaoUpdateException;

    /**
     * Der Computer versucht fuer jede Karte auf seiner Hand sie zu legen (entsprechend der Spielregeln).
     *
     * @param spiel       - das aktuelle Spiel
     * @param letzteKarte - die oberste Karte vom Ablagestapel des Spiels
     * @param hand        - die Hand des Computers
     * @throws DaoUpdateException         - beim fehlerhaften Updaten in der Dao-Klasse
     * @throws DaoFindException           - beim fehlerhaften Lesen in der Dao-Klasse
     * @throws LeererStapelException      - wenn der Stapel leer ist, aber nicht leer sein sollte
     * @throws KarteNichtGezogenException - wenn der Spieler auf fehlerhafte Weise keine Karte zieht
     */
    boolean karteLegen(MauMauSpiel spiel, Karte letzteKarte, List<Karte> hand) throws DaoUpdateException, DaoFindException, LeererStapelException, KarteNichtGezogenException;

    /**
     * Der Computer legt einen zufaelligen Wunschtyp fuer das Spiel fest, falls er einen Buben gelegt hat
     *
     * @throws DaoFindException   - beim fehlerhaften Lesen in der Dao-Klasse
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     */
    void wunschtypFestlegen() throws DaoFindException, DaoUpdateException;


}
