package de.htw.berlin.maumau.spielregeln.spielregelnInterface;

import de.htw.berlin.maumau.Karte;
import de.htw.berlin.maumau.MauMauSpiel;
import de.htw.berlin.maumau.Spieler;
import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.SonderregelTyp;

import java.util.List;

public interface ISpielregeln {

    /**
     * Anhand des {@link Kartentyp} Wunschtyps wird geprüft, ob die neue {@link Karte} gelegt werden kann.
     * @param neueKarte - die neue Karte
     * @param wunschtyp - der Wunschtyp
     * @return true, wenn legbar.
     */
    boolean istLegbar(Karte neueKarte, Kartentyp wunschtyp);

    /**
     * Anhand der Farbe und des Werts der letzten {@link Karte} wird geprüft, ob die neue {@link Karte} gelegt werden kann.
     * @param letzteKarte - die letzte Karte
     * @param neueKarte - die neue Karte
     * @return true, wenn legbar.
     */
    boolean istLegbar(Karte letzteKarte, Karte neueKarte);


    /**
     * Es wird überprüft, ob die letzte {@link Karte} eine Sonderregel einleitet.
     * @param letzteKarte - die letzte Karte
     * @return die entsprechende Sonderregel oder "Keine", falls keine Sonderregel zutrifft.
     */
    SonderregelTyp sonderregelErmitteln(Karte letzteKarte);

    /**
     * Setzt die Sonderregel Aussetzen um und falls der {@link Spieler} nicht mit einem Ass kontern kann,
     * wird der Zug für den {@link Spieler} beendet und der Zug des nächsten {@link Spieler} eingeleitet.
     * @param aktuellerSpieler - der aktuelle Spieler
     * @param neuerSpieler - der neue Spieler
     */
    void sonderregelAussetzen(Spieler aktuellerSpieler, Spieler neuerSpieler);

    /**
     * Setzt die Sonderregel Karten ziehen um. Entweder werden Karten gezogen, oder es kann gekontert werden.
     * @param anzahl - Anzahl der Karten, die gezogen werden sollen
     * @param hand - die aktuelle Hand
     * @param kartenstapel - der aktuelle Kartenstapel
     */
    void sonderregelKartenZiehen(int anzahl, List<Karte> hand, List<Karte> kartenstapel);

    /**
     * Legt den {@link Kartentyp} für die Sonderregel Typ wünschen fest.
     * @param wunschtyp - der gewünschte Kartentyp.
     * @param spiel - das aktuelle Spiel
     */
    void sonderregelWunschtypSetzen(Kartentyp wunschtyp, MauMauSpiel spiel);

}
