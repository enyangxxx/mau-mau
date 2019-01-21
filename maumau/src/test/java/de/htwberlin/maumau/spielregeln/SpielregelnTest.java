package de.htwberlin.maumau.spielregeln;

import de.htw.berlin.maumau.configurator.ConfigServiceImpl;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartentyp;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartenwert;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielregeln.spielregelnInterface.ISpielregeln;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class SpielregelnTest {

    private ISpielregeln spielregeln;

    private Spieler theo = new Spieler("Theo", 1, false);
    private Spieler enyang = new Spieler("Enyang", 2, false);

    List<Spieler> spielerliste = new ArrayList<Spieler>() {{
        add(enyang);
        add(theo);
    }};

    private MauMauSpiel spiel;
    private List<Karte> testkartenstapel = new ArrayList<Karte>() {{
        add(new Karte(Kartentyp.HERZ, Kartenwert.ACHT));
        add(new Karte(Kartentyp.KREUZ, Kartenwert.NEUN));
        add(new Karte(Kartentyp.PIK, Kartenwert.BUBE));
        add(new Karte(Kartentyp.HERZ, Kartenwert.NEUN));
        add(new Karte(Kartentyp.KREUZ, Kartenwert.ZEHN));
        add(new Karte(Kartentyp.PIK, Kartenwert.DAME));
    }};

    @Before
    public void setUp() {
        spielregeln = (ISpielregeln) ConfigServiceImpl.context.getBean("spielregelnimpl");
        spiel = new MauMauSpiel(spielerliste);
    }

    /**
     * Testes, ob Herz Neun auf Pik Acht gelegt werden kann.
     * Das erwartete Ergebnis ist false.
     */
    @Test
    public void testIstLegbarHerzNeunAufPikAcht() {

        Karte alteKarte = new Karte(Kartentyp.PIK, Kartenwert.ACHT);
        Karte neueKarte = new Karte(Kartentyp.HERZ, Kartenwert.NEUN);

        assertFalse("Herz neun soll nicht auf Pik Acht legbar sein", spielregeln.istLegbar(alteKarte, neueKarte));

    }

    /**
     * Testes, ob Herz Neun auf Pik Neun gelegt werden kann.
     * Das erwartete Ergebnis ist true.
     */
    @Test
    public void testIstLegbarHerzNeunAufPikNeun() {

        Karte alteKarte = new Karte(Kartentyp.PIK, Kartenwert.NEUN);
        Karte neueKarte = new Karte(Kartentyp.HERZ, Kartenwert.NEUN);

        assertTrue("Herz Neun soll auf Pik Neun legbar sein", spielregeln.istLegbar(alteKarte, neueKarte));

    }

    /**
     * Testes, ob Herz Neun auf Herz Acht gelegt werden kann.
     * Das erwartete Ergebnis ist true.
     */
    @Test
    public void testIstLegbarHerzNeunAufHerzAcht() {

        Karte alteKarte = new Karte(Kartentyp.HERZ, Kartenwert.ACHT);
        Karte neueKarte = new Karte(Kartentyp.HERZ, Kartenwert.NEUN);

        assertTrue("Herz Neun soll auf Herz Acht legbar sein", spielregeln.istLegbar(alteKarte, neueKarte));

    }

    /**
     * Testet, ob Kreuz Zehn gelegt werden kann, wenn der Wunschtyp Karo ist.
     * Das erwartete Ergebnis ist false.
     */
    @Test
    public void testIstLegbarKreuzZehnBeiWunschtypKaro(){

        Karte neueKarte = new Karte(Kartentyp.KREUZ, Kartenwert.ZEHN);
        Kartentyp wunschtyp = Kartentyp.KARO;

        assertFalse("Kreuz Zehn soll nicht legbar sein, wenn der Wunschtyp Karo ist.", spielregeln.istLegbar(neueKarte, wunschtyp));

    }

    /**
     * Testet, ob Kreuz Zehn gelegt werden kann, wenn der Wunschtyp Kreuz ist.
     * Das erwartete Ergebnis ist true.
     */
    @Test
    public void testIstLegbarKreuzZehnBeiWunschtypKreuz(){

        Karte neueKarte = new Karte(Kartentyp.KREUZ, Kartenwert.ZEHN);
        Kartentyp wunschtyp = Kartentyp.KREUZ;

        assertTrue("Kreuz Zehn soll legbar sein, wenn der Wunschtyp Kreuz ist.", spielregeln.istLegbar(neueKarte, wunschtyp));

    }


    /**
     * Testet, ob die Sonderregel Sieben eingehalten wird, wenn der Kartenwert der neuen Karte Zehn ist.
     * Das erwartete Ergebnis ist False
     */
    @Test
    public void testSonderregelnNichtEingehaltenSieben() {
        Karte letzteKarte = new Karte(Kartentyp.KARO, Kartenwert.SIEBEN);
        Karte neueKarte = new Karte(Kartentyp.KREUZ, Kartenwert.ZEHN);

        assertFalse("Sonderregel nicht eingehalten.", spielregeln.sonderregelEingehaltenSieben(neueKarte, letzteKarte));
    }

    /**
     * Testet, ob die Sonderregel Sieben eingehalten wird, wenn der Kartenwert der neuen Karte Sieben ist.
     * Das erwartete Ergebnis ist True
     */
    @Test
    public void testSonderregelnEingehaltenSieben() {
        Karte letzteKarte = new Karte(Kartentyp.KARO, Kartenwert.SIEBEN);
        Karte neueKarte = new Karte(Kartentyp.KREUZ, Kartenwert.SIEBEN);

        assertTrue("Sonderregel eingehalten.", spielregeln.sonderregelEingehaltenSieben(neueKarte, letzteKarte));
    }

}
