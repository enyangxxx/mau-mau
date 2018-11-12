package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl;

import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.ISpielerverwaltung;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;

import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class SpielerverwaltungImpl implements ISpielerverwaltung {
    /**
     * Generiert ein {@link Spieler} mit einem Namen und einer ID.
     *
     * @param name        - Name des Spielers
     * @param id          - ID des Spielers
     * @param istComputer - true wenn Computer-Spieler
     * @return den Spieler - der generierte Spieler
     */
    public Spieler spielerGenerieren(String name, int id, boolean istComputer) {
        return null;
    }

    /**
     * Fügt einen {@link Spieler} zur Spielerliste hinzu.
     *
     * @param spieler      - der neue Spieler
     * @param spielerliste - aktuelle Spielerliste
     */
    public void addSpielerZurListe(Spieler spieler, List<Spieler> spielerliste) {

    }

    /**
     * Ein {@link Spieler} beendet seinen Spielzug und der Spielzug des nächsten {@link Spieler} wird eingeleitet.
     *
     * @param aktuellerSpieler - der aktuelle Spieler
     * @param neuerSpieler     - der nächste Spieler
     */
    public void spielerWechseln(Spieler aktuellerSpieler, Spieler neuerSpieler) {

    }

    /**
     * Gibt den {@link Spieler} aus der Spielerliste zurück, zu dem die ID gehört.
     *
     * @param id           - ID des Spielers
     * @param spielerliste - aktuelle Spielerliste
     * @return spieler - der Spieler mit der ID
     */
    public Spieler getSpielerById(int id, List<Spieler> spielerliste) {
        return null;
    }
}
