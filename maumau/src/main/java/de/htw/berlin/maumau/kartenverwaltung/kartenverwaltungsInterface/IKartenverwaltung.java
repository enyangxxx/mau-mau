package de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface;

import de.htw.berlin.maumau.errorHandling.KeineKarteException;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;

import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public interface IKartenverwaltung {

    /**
     * Generiert einen Kartenstapel mit 32 Karten, wobei jede Karte einmal vorkommt.
     * @return generierter Kartenstapel
     */
    List<Karte> kartenstapelGenerieren();

    /**
     * Mischt den Kartenstapel, sodass die Reihenfolge der Karten zufällig ist.
     * @param kartenstapel - der aktuelle Kartenstapel
     */
    void kartenMischen(List<Karte> kartenstapel) throws KeineKarteException;


    /**
     * Mischt die Karten des Ablagestapels in den Kartenstapel.
     * @param ablagestapel - der aktuelle Ablagestapel
     * @param kartenstapel - der aktuelle Kartenstapel
     */
    void ablagestapelWiederverwenden(List<Karte> ablagestapel, List<Karte> kartenstapel);
}
