package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface;

import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl.Spieler;

import java.util.List;


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
    void addSpielerZurListe(Spieler spieler, List<Spieler> spielerliste);

    /**
     * Ein {@link Spieler} beendet seinen Spielzug und der Spielzug des nächsten {@link Spieler} wird eingeleitet.
     *
     * @param aktuellerSpieler - der aktuelle Spieler
     * @param neuerSpieler     - der nächste Spieler
     */
    void spielerWechseln(Spieler aktuellerSpieler, Spieler neuerSpieler);

    /**
     * Gibt den {@link Spieler} aus der Spielerliste zurück, zu dem die ID gehört.
     *
     * @param id           - ID des Spielers
     * @param spielerliste - aktuelle Spielerliste
     * @return spieler - der Spieler mit der ID
     */
    Spieler getSpielerById(int id, List<Spieler> spielerliste);

}
