package de.htw.berlin.maumau.spielregeln.spielregelnInterface;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.SonderregelTyp;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;

import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public interface ISpielregeln {

    /**
     * Anhand des {@link Kartentyp} Wunschtyps wird geprüft, ob die neue {@link Karte} gelegt werden kann.
     *
     * @param neueKarte - die neue Karte
     * @param wunschtyp - der Wunschtyp
     * @return true, wenn legbar.
     */
    boolean istLegbar(Karte neueKarte, Kartentyp wunschtyp);

    /**
     * Anhand der Farbe und des Werts der letzten {@link Karte} wird geprüft, ob die neue {@link Karte} gelegt werden kann.
     *
     * @param letzteKarte - die letzte Karte
     * @param neueKarte   - die neue Karte
     * @return true, wenn legbar.
     */
    boolean istLegbar(Karte letzteKarte, Karte neueKarte);


    /**
     * Es wird überprüft, ob die letzte {@link Karte} eine Sonderregel einleitet.
     *
     * @param letzteKarte - die letzte Karte
     * @return die entsprechende Sonderregel oder "Keine", falls keine Sonderregel zutrifft.
     */
    SonderregelTyp sonderregelErmitteln(Karte letzteKarte);

    /**
     * Setzt die Sonderregel Aussetzen um und falls der {@link Spieler} nicht mit einem Ass kontern kann,
     * wird der Zug für den {@link Spieler} beendet und der Zug des nächsten {@link Spieler} eingeleitet.
     *
     * @param aktuellerSpieler - der aktuelle Spieler
     * @param neuerSpieler     - der neue Spieler
     */
    void sonderregelAussetzen(Spieler aktuellerSpieler, Spieler neuerSpieler);

    /**
     * Setzt die Sonderregel Karten ziehen um. Entweder werden Karten gezogen, oder es kann gekontert werden.
     * Wenn die Anzahl der zu ziehenden Karten gezogen wurde, wird der Default-Wert von anzahlSonderregelKartenZiehen
     * auf 2 gesetzt.
     *
     * @param anzahl       - Anzahl der Karten, die gezogen werden sollen
     * @param hand         - die aktuelle Hand
     * @param kartenstapel - der aktuelle Kartenstapel
     */
    void sonderregelKartenZiehen(int anzahl, List<Karte> hand, List<Karte> kartenstapel);

}
