package de.htwberlin.mau_mau.kartenverwaltung;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsImpl.KartenverwaltungImpl;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.IKartenverwaltung;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl.Spieler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class KartenverwaltungsTest {

    private Log log = LogFactory.getLog(KartenverwaltungsTest.class);
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
    public void setUp() throws Exception {
        karte = new KartenverwaltungImpl();
    }

    /**
     * Testet, ob die Methode Kartenstapel Generieren null zurück gibt.
     */
    @Test
    public void testKartenstapelGenerierenisNotNull() {

        List<Karte> kartenstapel = karte.kartenstapelGenerieren();
        assertNotNull(kartenstapel);
    }

    /**
     * Testet, ob die Methode Kartenstapel Generieren einen leeren Kartenstapel generiert.
     */
    @Test
    public void testKartenstapelGenerierenIsNotEmpty() {

        List<Karte> kartenstapel = karte.kartenstapelGenerieren();
        assertFalse("Kartenstapel darf nicht empty sein",kartenstapel.isEmpty());
    }

    /**
     * Testet, ob die Methode Kartenstapel Generieren genau 32 Karten in dem Stapel erzeugt.
     */
    @Test
    public void testKartenstapelGenerierenRichtigeAnzahlVonKarten() {

        List<Karte> kartenstapel = karte.kartenstapelGenerieren();
        assertEquals("Der Kartenstapel muss aus 32 Karten bestehen", 32, kartenstapel.size());
    }

    /**
     * Testet, ob die Methode Kartenstapel Generieren genau jede Karte von jedem Typ und Farbe einmal erzeugt.
     */
    @Test
    public void testKartenstapelGenerierenErzeugtKeineDuplikate() {
        List<Karte> kartenstapel = karte.kartenstapelGenerieren();

        //Vergleiche die Karten des Korrekten Kartenstapels mit dem generierten
        boolean kartenstapelIstGleich = true;
        for (int i = 0; i < 32; i++) {
            if (kartenstapel.get(i).getTyp().equals(testKartenstapel.get(i).getTyp())
                    && kartenstapel.get(i).getWert().equals(testKartenstapel.get(i).getWert())){
            }else {
                kartenstapelIstGleich = false;
            }
        }
        assertEquals("Anzahl der Karten ist nicht gleich", testKartenstapel.size(), kartenstapel.size());
        assertTrue("Kartenstapel nicht gleich", kartenstapelIstGleich);
    }

    /**
     * Testet, ob die Methode Karten Mischen die Reihenfolge des Kartenstapels ändert.
     */
    @Test
    public void testKartenMischen() {
        List<Karte> kartenstapelAlt = karte.kartenstapelGenerieren();
        karte.kartenMischen(kartenstapelAlt);

        assertNotEquals(testKartenstapel.get(1).getTyp(), kartenstapelAlt.get(1).getTyp());
        assertNotEquals(testKartenstapel.get(1).getWert(), kartenstapelAlt.get(1).getWert());
        assertNotEquals(testKartenstapel.get(2).getTyp(), kartenstapelAlt.get(2).getTyp());
        assertNotEquals(testKartenstapel.get(2).getWert(), kartenstapelAlt.get(2).getWert());
    }

    /**
     * Testet, ob die Methode KartenAusteilen jedem Spieler aus der Spielerliste
     * 5 Karten austeilt, ob der Kartenstapel um 11 Karten reduziert wurde und ob
     * der Ablagestapel 1 Karte enthält.
     */
    @Test
    public void testKartenAusteilen() {
        List<Karte> kartenstapel = karte.kartenstapelGenerieren();
        List<Karte> ablagestapel = new ArrayList<Karte>();
        List<Spieler> spielerList = new ArrayList<Spieler>(){{
            add(new Spieler("theo",1,false));
            add(new Spieler("ingo",2,false));
        }};

        karte.kartenAusteilen(spielerList,kartenstapel, ablagestapel);

        assertEquals(5,spielerList.get(0).getHand());
        assertEquals(5,spielerList.get(1).getHand());
        assertEquals(22, kartenstapel.size());
        assertEquals(1,ablagestapel.size());
    }

    /**
     * Testet, ob die Methode Ablagestapel Wiederverwenden den Ablagestapel und den Kartenstapel zu einem
     * neuen Kartenstapel zusammenfügt und mischt.
     */
    @Test
    public void testAblagestapelWiederverwenden() {
        List<Karte> kartenstapel = karte.kartenstapelGenerieren();
        List<Karte> ablagestapel = new ArrayList<Karte>();
        int erwarteteAnzahl = kartenstapel.size() + ablagestapel.size();
        Karte ersteKarteAlterStapel = new Karte(kartenstapel.get(1).getTyp(), kartenstapel.get(1).getWert());
        Karte zweiteKarteAlterStapel = new Karte(kartenstapel.get(2).getTyp(), kartenstapel.get(2).getWert());
        karte.ablagestapelWiederverwenden(ablagestapel, kartenstapel);

        assertEquals(erwarteteAnzahl,kartenstapel.size());
        assertNotEquals(ersteKarteAlterStapel.getTyp(), kartenstapel.get(1).getTyp());
        assertNotEquals(ersteKarteAlterStapel.getWert(), kartenstapel.get(1).getWert());
        assertNotEquals(zweiteKarteAlterStapel.getTyp(), kartenstapel.get(2).getTyp());
        assertNotEquals(zweiteKarteAlterStapel.getWert(), kartenstapel.get(2).getWert());
    }


}
