package de.htwberlin.maumau.spielregeln;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.enumeration.SonderregelTyp;
import de.htw.berlin.maumau.errorHandling.KeinWunschtypException;
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

    private MauMauSpiel spiel = new MauMauSpiel(spielerliste);


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
    }

    /**
     * Testes, ob Herz Neun auf Pik Acht gelegt werden kann.
     * Das erwartete Ergebnis ist false.
     */
    @Test
    void testIstLegbarHerzNeunAufPikAcht() {

        Karte alteKarte = new Karte(Kartentyp.PIK, Kartenwert.ACHT);
        Karte neueKarte = new Karte(Kartentyp.HERZ, Kartenwert.NEUN);

        assertFalse("Herz neun soll nicht auf Pik Acht legbar sein", spielregeln.istLegbar(alteKarte, neueKarte));

    }

    /**
     * Testes, ob Herz Neun auf Pik Neun gelegt werden kann.
     * Das erwartete Ergebnis ist true.
     */
    @Test
    void testIstLegbarHerzNeunAufPikNeun() {

        Karte alteKarte = new Karte(Kartentyp.PIK, Kartenwert.NEUN);
        Karte neueKarte = new Karte(Kartentyp.HERZ, Kartenwert.NEUN);

        assertTrue("Herz Neun soll auf Pik Neun legbar sein", spielregeln.istLegbar(alteKarte, neueKarte));

    }

    /**
     * Testes, ob Herz Neun auf Herz Acht gelegt werden kann.
     * Das erwartete Ergebnis ist true.
     */
    @Test
    void testIstLegbarHerzNeunAufHerzAcht() {

        Karte alteKarte = new Karte(Kartentyp.HERZ, Kartenwert.ACHT);
        Karte neueKarte = new Karte(Kartentyp.HERZ, Kartenwert.NEUN);

        assertTrue("Herz Neun soll auf Herz Acht legbar sein", spielregeln.istLegbar(alteKarte, neueKarte));

    }

    /**
     * Testet, ob Kreuz Zehn gelegt werden kann, wenn der Wunschtyp Karo ist.
     * Das erwartete Ergebnis ist false.
     */
    @Test
    void testIstLegbarKreuzZehnBeiWunschtypKaro() {

        Karte neueKarte = new Karte(Kartentyp.KREUZ, Kartenwert.ZEHN);
        Kartentyp wunschtyp = Kartentyp.KARO;

        assertFalse("Kreuz Zehn soll nicht legbar sein, wenn der Wunschtyp Karo ist.", spielregeln.istLegbar(neueKarte, wunschtyp));

    }

    /**
     * Testet, ob Kreuz Zehn gelegt werden kann, wenn der Wunschtyp Kreuz ist.
     * Das erwartete Ergebnis ist true.
     */
    @Test
    void testIstLegbarKreuzZehnBeiWunschtypKreuz() {

        Karte neueKarte = new Karte(Kartentyp.KREUZ, Kartenwert.ZEHN);
        Kartentyp wunschtyp = Kartentyp.KREUZ;

        assertTrue("Kreuz Zehn soll legbar sein, wenn der Wunschtyp Kreuz ist.", spielregeln.istLegbar(neueKarte, wunschtyp));

    }

    /**
     * Testet die Funktionalität von istLegbar, wenn kein Wunschtyp gesetzt wurde.
     * Das erwartete Ergebnis ist eine KeinWunschtypException
     */
    @Test (expected = KeinWunschtypException.class)
    void testIstLegbarHerzAchtBeiWunschtypNull() {
        Karte neueKarte = new Karte(Kartentyp.HERZ, Kartenwert.ACHT);
        Kartentyp wunschtyp = null;
        spielregeln.istLegbar(neueKarte, wunschtyp);
    }

    /**
     * Testet, ob kein Sonderregeltyp ermittelt wird, wenn der Kartentyp der letzten Karte nicht
     * Bube, Ass oder Sieben ist.
     * Das erwartete Ergebnis ist, dass keine Sonderregeln auftreten (SonderregelTyp.KEINE)
     */
    @Test
    void testSonderregelErmittelnKeineSonderregel() {
        Karte letzteKarte = new Karte(Kartentyp.KREUZ, Kartenwert.ZEHN);

        assertEquals("Für Karo Ass soll der Sonderregeltyp Keine ermittelt werden.", SonderregelTyp.KEINE, spielregeln.sonderregelErmitteln(letzteKarte));
    }

    /**
     * Testet, ob der Sonderregeltyp Ass ermittelt wird, wenn der Kartentyp der letzten Karte Ass ist.
     * Das erwartete Ergebnis ist der Sonderregeltyp ASS
     */
    @Test
    void testSonderregelErmittelnAss() {
        Karte letzteKarte = new Karte(Kartentyp.KARO, Kartenwert.ASS);

        assertEquals("Für Karo Ass soll der Sonderregeltyp Ass ermittelt werden.", SonderregelTyp.ASS, spielregeln.sonderregelErmitteln(letzteKarte));
    }

    /**
     * Testet, ob der Sonderregeltyp Bube ermittelt wird, wenn der Kartentyp der letzten Karte Bube ist.
     * Das erwartete Ergebnis ist Sonderregeltyp BUBE
     */
    @Test
    void testSonderregelErmittelnBube() {
        Karte letzteKarte = new Karte(Kartentyp.HERZ, Kartenwert.BUBE);

        assertEquals("Für Herz Bube soll der Sonderregeltyp Bube ermittelt werden.", SonderregelTyp.BUBE, spielregeln.sonderregelErmitteln(letzteKarte));
    }

    /**
     * Testet, ob der Sonderregeltyp Sieben ermittelt wird, wenn der Kartentyp der letzten Karte Sieben ist.
     * Das erwartete Ergebnis ist Sonderregeltyp SIEBEN
     */
    @Test
    void testSonderregelErmittelnSieben() {
        Karte letzteKarte = new Karte(Kartentyp.PIK, Kartenwert.SIEBEN);

        assertEquals("Für Pik Sieben soll der Sonderregeltyp Sieben ermittelt werden.", SonderregelTyp.SIEBEN, spielregeln.sonderregelErmitteln(letzteKarte));
    }

    /**
     * Testet, ob die Sonderregel Aussetzen durchgeführt wird, wenn der Computer nicht mit einem Ass kontern kann.
     * Das erwartete Ergebnis ist, dass Enyang durchs Aussetzen dieselbe Menge der Handkarten hat und Theo wieder dran ist.
     */
    @Test
    void testSonderregelAussetzen() {
        List<Karte> hand = new ArrayList<Karte>() {{
            add(new Karte(Kartentyp.HERZ, Kartenwert.ACHT));
            add(new Karte(Kartentyp.KREUZ, Kartenwert.NEUN));
            add(new Karte(Kartentyp.PIK, Kartenwert.BUBE));
        }};

        enyang.setHand(hand);
        enyang.setIstDran(true);
        theo.setIstDran(false);

        spielregeln.sonderregelAussetzen(enyang, theo);

        assertEquals("Enyangs Hand muss gleich bleiben, da er nicht mit einem Ass kontern kann.", 3, enyang.getHand().size());
        assertTrue("Theo muss jetzt dran sein.", theo.istDran());
    }


    /**
     * Testet, ob der Computer die Sonderregel Aussetzen kontern kann, wenn er selber ein Ass legen kann.
     * Das erwartete Ergebnis ist, dass Enyang nicht Aussetzen muss, sondern ein Ass legen kann.
     */
    @Test
    void testSonderregelAussetzenKontern() {
        List<Karte> hand = new ArrayList<Karte>() {{
            add(new Karte(Kartentyp.HERZ, Kartenwert.ACHT));
            add(new Karte(Kartentyp.KREUZ, Kartenwert.ASS));
            add(new Karte(Kartentyp.PIK, Kartenwert.BUBE));
        }};

        enyang.setHand(hand);
        enyang.setIstDran(true);
        theo.setIstDran(false);

        spielregeln.sonderregelAussetzen(enyang, theo);

        assertEquals("Enyangs Hand muss um eine Karte reduziert sein, da er mit einem Ass kontern kann.", 2, enyang.getHand().size());
        assertTrue("Theo muss jetzt dran sein.", theo.istDran());
    }

    /**
     * Testet, ob die Sonderregel Karten ziehen durchgeführt wird, wenn der Computer nicht mit einer Sieben kontern kann.
     * Das erwartete Ergebnis ist, dass Enyang zwei Karten vom Kartenstapel zu seiner Hand hinzufügen muss.
     */
    @Test
    void testSonderregelZweiKartenZiehen() {
        List<Karte> hand = new ArrayList<Karte>() {{
            add(new Karte(Kartentyp.HERZ, Kartenwert.ACHT));
            add(new Karte(Kartentyp.KREUZ, Kartenwert.NEUN));
            add(new Karte(Kartentyp.PIK, Kartenwert.BUBE));
        }};

        enyang.setHand(hand);

        int alteAnzahlHand = enyang.getHand().size();
        int alteAnzahlKartenstapel = testkartenstapel.size();

        spielregeln.sonderregelKartenZiehen(2, enyang.getHand(), testkartenstapel);

        assertEquals("Enyangs Hand muss zwei Karten mehr enthalten.", alteAnzahlHand + 2, enyang.getHand().size());
        assertEquals("Der Kartenstapel soll zwei Karten weniger enthalten.", alteAnzahlKartenstapel - 2, testkartenstapel.size());
    }

    /**
     * Testet, ob der Computer die Sonderregel Karten ziehen kontern kann, wenn er selber eine Sieben legen kann.
     * Das erwartete Ergebnis ist, dass Enyang nicht ziehen muss, sondern eine Sieben legen kann und dass die Anzahl
     * der zu ziehenden Karten um zwei erhöht wird.
     */
    @Test
    void testSonderregelZweiKartenZiehenKontern() {
        List<Karte> hand = new ArrayList<Karte>() {{
            add(new Karte(Kartentyp.HERZ, Kartenwert.SIEBEN));
            add(new Karte(Kartentyp.KREUZ, Kartenwert.NEUN));
            add(new Karte(Kartentyp.PIK, Kartenwert.BUBE));
        }};

        enyang.setHand(hand);
        int alteAnzahlHand = enyang.getHand().size();

        spielregeln.sonderregelKartenZiehen(spiel.getAnzahlSonderregelKartenZiehen(), enyang.getHand(), testkartenstapel);

        assertEquals("Die Anzahl der zu ziehenden Karten muss um 2 erhöht sein.", 4, spiel.getAnzahlSonderregelKartenZiehen());
        assertFalse("Enyang darf nun nicht mehr am Zug sein.", enyang.istDran());
        assertEquals("Enyang muss eine Karte weniger auf der Hand haben.", alteAnzahlHand - 1, enyang.getHand().size());
    }

}
