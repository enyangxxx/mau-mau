package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface;

import de.htw.berlin.maumau.errorHandling.IdDuplikatException;
import de.htw.berlin.maumau.errorHandling.KeineSpielerException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public interface ISpielerverwaltung {

    /**
     * Generiert ein {@link Spieler} mit einem Namen und einer ID.
     *
     * @param name        - Name des Spielers
     * @param id          - ID des Spielers
     * @param istComputer - true wenn Computer-Spieler
     * @return den Spieler - der generierte Spieler
     */
    Spieler spielerGenerieren(String name, int id, boolean istComputer);

    /**
     * Fügt einen {@link Spieler} zur Spielerliste hinzu.
     *
     * @param spieler      - der neue Spieler
     * @param spielerliste - aktuelle Spielerliste
     */
    void addSpielerZurListe(Spieler spieler, List<Spieler> spielerliste) throws IdDuplikatException;

    /**
     * Ein {@link Spieler} beendet seinen Spielzug und der Spielzug des nächsten {@link Spieler} wird eingeleitet.
     *
     * @param aktuellerSpieler - der aktuelle Spieler
     * @param neuerSpieler     - der nächste Spieler
     */
    void spielerWechseln(MauMauSpiel spiel);

    /**
     * Gibt den {@link Spieler} aus der Spielerliste zurück, zu dem die ID gehört.
     *
     * @param id           - ID des Spielers
     * @param spielerliste - aktuelle Spielerliste
     * @return spieler - der Spieler mit der ID
     */
    Spieler getSpielerById(int id, List<Spieler> spielerliste) throws KeineSpielerException;

    /**
     * Alle {@link Spieler} bekommen je 5 {@link Karte} aus dem Kartenstapel und es wird eine Anfangskarte aufgedeckt.
     * @param spielerliste - die aktuelle Spielerliste
     * @param kartenstapel - der aktuelle Kartenstapel
     * @param ablagestapel - der aktuelle Ablagestapel
     */
    void kartenAusteilen(List<Spieler> spielerliste, List<Karte> kartenstapel, List<Karte> ablagestapel);
}
