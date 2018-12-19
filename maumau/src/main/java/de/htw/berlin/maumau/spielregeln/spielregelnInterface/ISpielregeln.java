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
     *
     * @param neueKarte - die neue Karte
     * @param wunschtyp - der Wunschtyp
     * @return true, wenn  die neue Karte legbar ist.
     * @throws KeinWunschtypException - falls kein Wunschtyp festgelegt wurde
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
     * Es wird überprüft, ob die neue Karte {@link Karte} eine Sieben ist, wenn die letzte Karte auch eine Sieben ist.
     * @param letzteKarte - die letzte Karte
     * @param  neueKarte - die neue Karte die gelegt werden soll
     * @return true - wenn die Sonderregel Sieben eingehalten wurde
     */
    boolean sonderregelEingehaltenSieben(Karte neueKarte, Karte letzteKarte);

    /**
     * Es wird überprüft, ob die neue Karte {@link Karte} ein Bube ist, wenn die letzte Karte auch ein Bube ist.
     * @param letzteKarte - die letzte Karte
     * @param  neueKarte - die neue Karte die gelegt werden soll
     * @return true - wenn versucht wird einen Buben auf einen Buben zu legen.
     */
    boolean sonderregelEingehaltenBube(Karte neueKarte, Karte letzteKarte);



}
