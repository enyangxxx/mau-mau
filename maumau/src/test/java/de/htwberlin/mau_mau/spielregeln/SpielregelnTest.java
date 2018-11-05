package de.htwberlin.mau_mau.spielregeln;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsImpl.Karte;
import de.htw.berlin.maumau.spielregeln.spielregelnInterface.ISpielregeln;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SpielregelnTest {

    ISpielregeln spielregeln;

    @Before
    public void setUp() throws Exception {
    }


    /**
     * Testes, ob Herz Neun auf Pik Acht gelegt werden kann.
     * Das erwartete Ergebnis ist false.
     */
    @Test
    void testIstLegbarHerzNeunAufPikAcht(){

        Karte alteKarte = new Karte(Kartentyp.PIK, Kartenwert.ACHT);
        Karte neueKarte = new Karte(Kartentyp.HERZ, Kartenwert.NEUN);

        assertEquals("Herz neun soll nicht auf Pik Acht legbar sein", false, spielregeln.istLegbar(alteKarte, neueKarte));

    }
}
