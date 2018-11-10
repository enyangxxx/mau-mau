package de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface;

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
    void kartenMischen(List<Karte> kartenstapel);

    /**
     * Alle {@link Spieler} bekommen je 5 {@link Karte} aus dem Kartenstapel und es wird eine Anfangskarte aufgedeckt.
     * @param spielerliste - die aktuelle Spielerliste
     * @param kartenstapel - der aktuelle Kartenstapel
     * @param ablagestapel - der aktuelle Ablagestapel
     */
    void kartenAusteilen(List<Spieler> spielerliste, List<Karte> kartenstapel, List<Karte> ablagestapel);

    /**
     * Mischt die Karten des Ablagestapels in den Kartenstapel.
     * @param ablagestapel - der aktuelle Ablagestapel
     * @param kartenstapel - der aktuelle Kartenstapel
     */
    void ablagestapelWiederverwenden(List<Karte> ablagestapel, List<Karte> kartenstapel);
}
