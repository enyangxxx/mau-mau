package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface;

import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.KeinSpielerException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoCreateException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoFindException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoUpdateException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.LeererStapelException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl.SpielerDao;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl.MauMauSpielDao;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public interface ISpielerverwaltung {

    /**
     * Ein Spieler wird generiert.
     *
     * @param name        - Name des Spielers
     * @param id          - ID des Spielers
     * @throws DaoCreateException - beim fehlerhaften Erstellen in der Dao-Klasse
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    void spielerGenerieren(String name, int id, boolean istComputer) throws DaoCreateException, DaoFindException;

    /**
     * Der Spieler wird der Spielerliste hinzugefügt.
     *
     * @param spieler      - der neue Spieler
     * @param spielerliste - aktuelle Spielerliste
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    void addSpielerZurListe(Spieler spieler, List<Spieler> spielerliste) throws DaoFindException;

    /**
     * Der aktuelle Spieler ist nicht mehr dran, der nächste Spieler ist dran.
     *
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     */
    void spielerWechseln() throws DaoFindException, DaoUpdateException;

    /**
     * Der Spieler wird durch die eindeutige ID ermittelt.
     *
     * @param id           - ID des Spielers
     * @param spielerliste - aktuelle Spielerliste
     * @return gefundenerSpieler - der gesuchte Spieler mit der ID
     * @throws KeinSpielerException - Wenn kein Spieler mit der ID gefunden wurde
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    Spieler getSpielerById(int id, List<Spieler> spielerliste) throws KeinSpielerException, DaoFindException;

    /**
     * Alle {@link Spieler} bekommen je 5 {@link Karte} aus dem Kartenstapel und es wird eine Anfangskarte aufgedeckt.
     *
     * @throws LeererStapelException - Wenn ein leerer Stapel nicht leer sein darf
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    void kartenAusteilen() throws LeererStapelException, DaoUpdateException, DaoFindException;

    void setMaumauSpielDao(MauMauSpielDao mauMauSpielDao);

    void setSpielerDao(SpielerDao spielerDao);
}
