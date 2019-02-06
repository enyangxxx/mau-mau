package de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface;

import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoFindException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoUpdateException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.LeererStapelException;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public interface IKartenverwaltung {

    /**
     * Generiert einen Kartenstapel mit 32 Karten, wobei jede Karte einmal vorkommt.
     *
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     * @throws DaoFindException   - beim fehlerhaften Lesen in der Dao-Klasse
     */
    void kartenstapelGenerieren() throws DaoUpdateException, DaoFindException;

    /**
     * Mischt den Kartenstapel, sodass die Reihenfolge der Karten zufaellig ist.
     *
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     * @throws DaoFindException   - beim fehlerhaften Lesen in der Dao-Klasse
     */
    void kartenMischen() throws DaoUpdateException, DaoFindException;


    /**
     * Mischt die Karten des Ablagestapels in den Kartenstapel.
     *
     * @throws LeererStapelException - Der leerer Stapel darf nicht leer sein.
     * @throws DaoFindException      - beim fehlerhaften Lesen in der Dao-Klasse
     * @throws DaoUpdateException    - beim fehlerhaften Updaten in der Dao-Klasse
     */
    void ablagestapelWiederverwenden() throws LeererStapelException, DaoFindException, DaoUpdateException;
}
