package de.htwberlin.mau_mau.kartenverwaltung;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsImpl.KartenverwaltungImpl;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.IKartenverwaltung;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class KartenverwaltungsTest {

    //private static final Log log = LogFactory.getLog(KartenverwaltungsTest.class);
    private IKartenverwaltung karte;
    private List<Karte> testKartenstapel = new ArrayList<Karte>() {{
        add(new Karte(Kartentyp.HERZ, Kartenwert.SIEBEN));
        add(new Karte(Kartentyp.HERZ, Kartenwert.ACHT));
        add(new Karte(Kartentyp.HERZ, Kartenwert.NEUN));
        add(new Karte(Kartentyp.HERZ, Kartenwert.ZEHN));
        add(new Karte(Kartentyp.HERZ, Kartenwert.BUBE));
        add(new Karte(Kartentyp.HERZ, Kartenwert.DAME));
        add(new Karte(Kartentyp.HERZ, Kartenwert.KOENIG));
        add(new Karte(Kartentyp.HERZ, Kartenwert.ASS));

        add(new Karte(Kartentyp.PIK, Kartenwert.SIEBEN));
        add(new Karte(Kartentyp.PIK, Kartenwert.ACHT));
        add(new Karte(Kartentyp.PIK, Kartenwert.NEUN));
        add(new Karte(Kartentyp.PIK, Kartenwert.ZEHN));
        add(new Karte(Kartentyp.PIK, Kartenwert.BUBE));
        add(new Karte(Kartentyp.PIK, Kartenwert.DAME));
        add(new Karte(Kartentyp.PIK, Kartenwert.KOENIG));
        add(new Karte(Kartentyp.PIK, Kartenwert.ASS));

        add(new Karte(Kartentyp.KREUZ, Kartenwert.SIEBEN));
        add(new Karte(Kartentyp.KREUZ, Kartenwert.ACHT));
        add(new Karte(Kartentyp.KREUZ, Kartenwert.NEUN));
        add(new Karte(Kartentyp.KREUZ, Kartenwert.ZEHN));
        add(new Karte(Kartentyp.KREUZ, Kartenwert.BUBE));
        add(new Karte(Kartentyp.KREUZ, Kartenwert.DAME));
        add(new Karte(Kartentyp.KREUZ, Kartenwert.KOENIG));
        add(new Karte(Kartentyp.KREUZ, Kartenwert.ASS));

        add(new Karte(Kartentyp.KARO, Kartenwert.SIEBEN));
        add(new Karte(Kartentyp.KARO, Kartenwert.ACHT));
        add(new Karte(Kartentyp.KARO, Kartenwert.NEUN));
        add(new Karte(Kartentyp.KARO, Kartenwert.ZEHN));
        add(new Karte(Kartentyp.KARO, Kartenwert.BUBE));
        add(new Karte(Kartentyp.KARO, Kartenwert.DAME));
        add(new Karte(Kartentyp.KARO, Kartenwert.KOENIG));
        add(new Karte(Kartentyp.KARO, Kartenwert.ASS));
    }};


    @Before
    public void setUp() {
        karte = new KartenverwaltungImpl();
    }

    /**
     * Testet, ob die Methode Kartenstapel Generieren null zurück gibt.
     * Das erwartete Ergebnis ist ein not null Kartenstapel
     */
    @Test
    public void testKartenstapelGenerierenisNotNull() {

        List<Karte> kartenstapel = karte.kartenstapelGenerieren();
        assertNotNull("Kartenstapel darf nicht null sein", kartenstapel);
    }

    /**
     * Testet, ob die Methode Kartenstapel Generieren einen leeren Kartenstapel generiert.
     * Das erwartete Ergebnis ist ein not empty Kartenstapel
     */
    @Test
    public void testKartenstapelGenerierenIsNotEmpty() {

        List<Karte> kartenstapel = karte.kartenstapelGenerieren();
        assertFalse("Kartenstapel darf nicht empty sein", kartenstapel.isEmpty());
    }

    /**
     * Testet, ob die Methode Kartenstapel Generieren genau 32 Karten in dem Stapel erzeugt.
     * Das erwartete Ergebnis ist ein Kartenstapel mit 32 Karten
     */
    @Test
    public void testKartenstapelGenerierenRichtigeAnzahlVonKarten() {

        List<Karte> kartenstapel = karte.kartenstapelGenerieren();
        assertEquals("Der Kartenstapel muss aus 32 Karten bestehen", 32, kartenstapel.size());
    }

    /**
     * Testet, ob die Methode Kartenstapel Generieren genau jede Karte von jedem Typ und Farbe einmal erzeugt.
     * Das erwartete Ergebnis ist ein Kartenstapel ohne Duplikat mit gleicher Anzahl von Karten
     */
    @Test
    public void testKartenstapelGenerierenErzeugtKeineDuplikate() {
        List<Karte> kartenstapel = karte.kartenstapelGenerieren();

        //Vergleiche die Karten des Korrekten Kartenstapels mit dem generierten
        boolean kartenstapelIstGleich = true;
        for (int i = 0; i < 32; i++) {
            if (!(kartenstapel.get(i).getTyp().equals(testKartenstapel.get(i).getTyp())
                    && kartenstapel.get(i).getWert().equals(testKartenstapel.get(i).getWert()))) {
                kartenstapelIstGleich = false;
            }
        }
        assertEquals("Anzahl der Karten ist nicht gleich", testKartenstapel.size(), kartenstapel.size());
        assertTrue("Der Kartenstapel muss gleich sein", kartenstapelIstGleich);
    }

    /**
     * Testet, ob die Methode Karten Mischen die Reihenfolge des Kartenstapels ändert.
     * Das erwartete Ergebnis ist ein gemischter Kartenstapel
     */
    @Test
    public void testKartenMischen() {
        List<Karte> kartenstapel = karte.kartenstapelGenerieren();
        karte.kartenMischen(kartenstapel);
        boolean istKartenstapelGemischt = false;
        int i = 0;

        while (i < kartenstapel.size()) {
            if(istKartenstapelGemischt){
                break;
            }
            if (testKartenstapel.get(i).getTyp() != kartenstapel.get(i).getTyp()
                    || testKartenstapel.get(i).getWert() != kartenstapel.get(i).getWert()) {
                istKartenstapelGemischt = true;
            }
            i++;
        }

        assertTrue("Der Kartenstapel scheint nicht durchmischt worden zu sein.", istKartenstapelGemischt);
    }

    /**
     * Testet, ob die Methode KartenAusteilen jedem Spieler aus der Spielerliste 5 Karten austeilt,
     * ob der Kartenstapel um 11 Karten reduziert wurde und ob der Ablagestapel 1 Karte enthält.
     * Das erwartete Ergebnis ist dass jeder Spieler jeweils 5 Karten, der Ablagestapel 1 Karte,
     * der Kartenstapel 21
     */
    @Test
    public void testKartenAusteilen() {
        List<Karte> kartenstapel = karte.kartenstapelGenerieren();
        List<Karte> ablagestapel = new ArrayList<Karte>();
        List<Spieler> spielerList = new ArrayList<Spieler>() {{
            add(new Spieler("theo", 1, false));
            add(new Spieler("ingo", 2, false));
        }};

        karte.kartenAusteilen(spielerList, kartenstapel, ablagestapel);

        assertEquals("Der Spieler 1 muss 5 Karten haben",5, spielerList.get(0).getHand().size());
        assertEquals("Der Spieler 2 muss 5 Karten haben",5, spielerList.get(1).getHand().size());
        assertEquals("Der Kartenstapel muss 21 Karten haben",21, kartenstapel.size());
        assertEquals("Der Ablagestapel muss 1 Karte haben",1, ablagestapel.size());
    }

    /**
     * Testet, ob die Methode Ablagestapel Wiederverwenden den Ablagestapel und den Kartenstapel zu einem
     * neuen Kartenstapel zusammenfügt.
     * Das erwartete Ergebnis ist dass der neue Kartenstapel so viele Karten hat wie die Kartenmenge
     * des Ablagenstapels und des alten Kartenstapels
     */
    @Test
    public void testAblagestapelWiederverwenden() {
        List<Karte> kartenstapel = karte.kartenstapelGenerieren();
        List<Karte> ablagestapel = new ArrayList<Karte>();
        int erwarteteAnzahl = kartenstapel.size() + ablagestapel.size();
        karte.ablagestapelWiederverwenden(ablagestapel, kartenstapel);

        assertEquals("Der neue Kartenstapel muss " + erwarteteAnzahl + " Karten haben",erwarteteAnzahl, kartenstapel.size());
    }

}
