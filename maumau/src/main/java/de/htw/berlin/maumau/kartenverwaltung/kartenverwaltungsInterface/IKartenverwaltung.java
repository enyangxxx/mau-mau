package de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface;

import de.htw.berlin.maumau.errorHandling.technischeExceptions.LeererStapelException;

import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public interface IKartenverwaltung {

    /**
     * Generiert einen Kartenstapel mit 32 Karten, wobei jede Karte einmal vorkommt.
     * @return generierter Kartenstapel
     */
    void kartenstapelGenerieren() throws Exception;

    /**
     * Mischt den Kartenstapel, sodass die Reihenfolge der Karten zufällig ist.
     * //@param kartenstapel - der aktuelle Kartenstapel
     */
    void kartenMischen() throws Exception;


    /**
     * Mischt die Karten des Ablagestapels in den Kartenstapel.
     //* @param ablagestapel - der aktuelle Ablagestapel
     //* @param kartenstapel - der aktuelle Kartenstapel
     */
    void ablagestapelWiederverwenden() throws LeererStapelException, Exception;
}
