package de.htwberlin.maumau.spielregeln;

import de.htw.berlin.maumau.configurator.ConfigServiceImpl;
import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.enumeration.SonderregelTyp;
import de.htw.berlin.maumau.errorHandling.KeinWunschtypException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielregeln.spielregelnImpl.SpielregelnImpl;
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
        //spielregeln = new SpielregelnImpl();
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
    public void testIstLegbarKreuzZehnBeiWunschtypKaro() throws KeinWunschtypException {

        Karte neueKarte = new Karte(Kartentyp.KREUZ, Kartenwert.ZEHN);
        Kartentyp wunschtyp = Kartentyp.KARO;

        assertFalse("Kreuz Zehn soll nicht legbar sein, wenn der Wunschtyp Karo ist.", spielregeln.istLegbar(neueKarte, wunschtyp));

    }

    /**
     * Testet, ob Kreuz Zehn gelegt werden kann, wenn der Wunschtyp Kreuz ist.
     * Das erwartete Ergebnis ist true.
     */
    @Test
    public void testIstLegbarKreuzZehnBeiWunschtypKreuz() throws KeinWunschtypException {

        Karte neueKarte = new Karte(Kartentyp.KREUZ, Kartenwert.ZEHN);
        Kartentyp wunschtyp = Kartentyp.KREUZ;

        assertTrue("Kreuz Zehn soll legbar sein, wenn der Wunschtyp Kreuz ist.", spielregeln.istLegbar(neueKarte, wunschtyp));

    }

    /**
     * Testet die Funktionalität von istLegbar, wenn kein Wunschtyp gesetzt wurde.
     * Das erwartete Ergebnis ist eine KeinWunschtypException
     */
    @Test(expected = KeinWunschtypException.class)
    public void testIstLegbarHerzAchtBeiWunschtypNull() throws KeinWunschtypException {
        Karte neueKarte = new Karte(Kartentyp.HERZ, Kartenwert.ACHT);
        Kartentyp wunschtyp = null;
        spielregeln.istLegbar(neueKarte, wunschtyp);
    }


    /**
     * Testet, ob der Sonderregeltyp Ass ermittelt wird, wenn der Kartentyp der letzten Karte Ass ist.
     * Das erwartete Ergebnis ist der Sonderregeltyp ASS
     */
    @Test
    public void testSonderregelnNichtEingehaltenAss() {
        Karte letzteKarte = new Karte(Kartentyp.KARO, Kartenwert.ASS);
        Karte neueKarte = new Karte(Kartentyp.KREUZ, Kartenwert.ZEHN);

        assertFalse("Sonderregel nicht eingehalten.", spielregeln.sonderregelEingehalten(neueKarte, letzteKarte));
    }

    /**
     * Testet, ob der Sonderregeltyp Ass ermittelt wird, wenn der Kartentyp der letzten Karte Ass ist.
     * Das erwartete Ergebnis ist der Sonderregeltyp ASS
     */
    @Test
    public void testSonderregelnEingehaltenAss() {
        Karte letzteKarte = new Karte(Kartentyp.KARO, Kartenwert.ASS);
        Karte neueKarte = new Karte(Kartentyp.KREUZ, Kartenwert.ASS);

        assertTrue("Sonderregel eingehalten.", spielregeln.sonderregelEingehalten(neueKarte, letzteKarte));
    }

    /**
     * Testet, ob der Sonderregeltyp Ass ermittelt wird, wenn der Kartentyp der letzten Karte Ass ist.
     * Das erwartete Ergebnis ist der Sonderregeltyp ASS
     */
    @Test
    public void testSonderregelnNichtEingehaltenSieben() {
        Karte letzteKarte = new Karte(Kartentyp.KARO, Kartenwert.SIEBEN);
        Karte neueKarte = new Karte(Kartentyp.KREUZ, Kartenwert.ZEHN);

        assertFalse("Sonderregel nicht eingehalten.", spielregeln.sonderregelEingehalten(neueKarte, letzteKarte));
    }

    /**
     * Testet, ob der Sonderregeltyp Ass ermittelt wird, wenn der Kartentyp der letzten Karte Ass ist.
     * Das erwartete Ergebnis ist der Sonderregeltyp ASS
     */
    @Test
    public void testSonderregelnEingehaltenSieben() {
        Karte letzteKarte = new Karte(Kartentyp.KARO, Kartenwert.SIEBEN);
        Karte neueKarte = new Karte(Kartentyp.KREUZ, Kartenwert.SIEBEN);

        assertTrue("Sonderregel nicht eingehalten.", spielregeln.sonderregelEingehalten(neueKarte, letzteKarte));
    }

    /**
     * Testet, ob der Sonderregeltyp Ass ermittelt wird, wenn der Kartentyp der letzten Karte Ass ist.
     * Das erwartete Ergebnis ist der Sonderregeltyp ASS
     */
    @Test
    public void testSonderregelnEingehaltenWederAssNochSieben() {
        Karte letzteKarte = new Karte(Kartentyp.KARO, Kartenwert.ACHT);
        Karte neueKarte = new Karte(Kartentyp.KREUZ, Kartenwert.NEUN);

        assertTrue("Sonderregel eingehalten.", spielregeln.sonderregelEingehalten(neueKarte, letzteKarte));
    }

}
