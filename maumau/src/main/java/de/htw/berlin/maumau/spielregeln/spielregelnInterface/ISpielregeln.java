package de.htw.berlin.maumau.spielregeln.spielregelnInterface;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.SonderregelTyp;
import de.htw.berlin.maumau.errorHandling.KeinWunschtypException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public interface ISpielregeln {

    /**
     * Anhand des {@link Kartentyp} Wunschtyps wird geprüft, ob die neue {@link Karte} gelegt werden kann.
     * @param neueKarte - die neue Karte
     * @param wunschtyp - der Wunschtyp
     * @return true, wenn legbar.
     */
    boolean istLegbar(Karte neueKarte, Kartentyp wunschtyp) throws KeinWunschtypException;

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
    boolean sonderregelEingehaltenSieben(Karte neueKarte, Karte letzteKarte);


    boolean sonderregelEingehaltenBube(Karte neueKarte, Karte letzteKarte);



}
