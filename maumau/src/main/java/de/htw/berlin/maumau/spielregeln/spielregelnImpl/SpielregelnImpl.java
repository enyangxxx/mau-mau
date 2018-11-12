package de.htw.berlin.maumau.spielregeln.spielregelnImpl;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.SonderregelTyp;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielregeln.spielregelnInterface.ISpielregeln;

import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class SpielregelnImpl implements ISpielregeln {
    public boolean istLegbar(Karte neueKarte, Kartentyp wunschtyp) {
        return false;
    }

    public boolean istLegbar(Karte letzteKarte, Karte neueKarte) {
        return false;
    }

    public SonderregelTyp sonderregelErmitteln(Karte letzteKarte) {
        return null;
    }

    public void sonderregelAussetzen(Spieler aktuellerSpieler, Spieler neuerSpieler) {

    }

    public void sonderregelKartenZiehen(int anzahl, List<Karte> hand, List<Karte> kartenstapel) {

    }
}
