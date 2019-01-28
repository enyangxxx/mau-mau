package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface;

import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.KeinSpielerException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.LeererStapelException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public interface ISpielerverwaltung {

    /**
     * Ein Spieler wird generiert.
     * @param name        - Name des Spielers
     * @param id          - ID des Spielers
     * @param istComputer - true wenn Computer-Spieler
     * @return spieler - der generierte Spieler
     */
    void spielerGenerieren(String name, int id, boolean istComputer) throws Exception;

    /**
     * Der Spieler wird der Spielerliste hinzugefügt.
     * @param spieler      - der neue Spieler
     * @param spielerliste - aktuelle Spielerliste
     */
    void addSpielerZurListe(Spieler spieler, List<Spieler> spielerliste);

    /**
     * Der aktuelle Spieler ist nicht mehr dran, der nächste Spieler ist dran.
     * @param spiel - das aktuelle MauMau-Spiel
     */
    void spielerWechseln(MauMauSpiel spiel);

    /**
     * Der Spieler wird durch die eindeutige ID ermittelt.
     * @param id           - ID des Spielers
     * @param spielerliste - aktuelle Spielerliste
     * @return gefundenerSpieler - der gesuchte Spieler mit der ID
     * @throws KeinSpielerException - Wenn kein Spieler mit der ID gefunden wurde
     */
    Spieler getSpielerById(int id, List<Spieler> spielerliste) throws KeinSpielerException;

    /**
     * Alle {@link Spieler} bekommen je 5 {@link Karte} aus dem Kartenstapel und es wird eine Anfangskarte aufgedeckt.
     *
     * //@param spielerliste - die aktuelle Spielerliste
     * //@param kartenstapel - der aktuelle Kartenstapel
     * //@param ablagestapel - der aktuelle Ablagestapel
     */
    void kartenAusteilen() throws LeererStapelException, Exception;

}
