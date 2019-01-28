package de.htwberlin.maumau.kartenverwaltung;

import de.htw.berlin.maumau.configurator.ConfigServiceImpl;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartentyp;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartenwert;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.LeererStapelException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.IKartenverwaltung;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class KartenverwaltungsTest {

    private IKartenverwaltung kartenverwaltung;
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
        kartenverwaltung = (IKartenverwaltung) ConfigServiceImpl.context.getBean("kartenverwaltungimpl");
    }

    /**
     * Testet, ob die Methode Kartenstapel Generieren null zurück gibt.
     * Das erwartete Ergebnis ist ein not null Kartenstapel
     */
    /*@Test
    public void testKartenstapelGenerierenisNotNull() throws Exception {

        List<Karte> kartenstapel = kartenverwaltung.kartenstapelGenerieren();
        assertNotNull("Kartenstapel darf nicht null sein", kartenstapel);
    }
    */

    /**
     * Testet, ob die Methode Kartenstapel Generieren einen leeren Kartenstapel generiert.
     * Das erwartete Ergebnis ist ein not empty Kartenstapel
     */
    /*@Test
    public void testKartenstapelGenerierenIsNotEmpty() throws Exception {

        List<Karte> kartenstapel = kartenverwaltung.kartenstapelGenerieren();
        assertFalse("Kartenstapel darf nicht empty sein", kartenstapel.isEmpty());
    }
    */

    /**
     * Testet, ob die Methode Kartenstapel Generieren genau 32 Karten in dem Stapel erzeugt.
     * Das erwartete Ergebnis ist ein Kartenstapel mit 32 Karten
     */
    /*@Test
    public void testKartenstapelGenerierenRichtigeAnzahlVonKarten() throws Exception {

        List<Karte> kartenstapel = kartenverwaltung.kartenstapelGenerieren();
        assertEquals("Der Kartenstapel muss aus 32 Karten bestehen", 32, kartenstapel.size());
    }
    */

    /**
     * Testet, ob die Methode Kartenstapel Generieren genau jede Karte von jedem Typ und Farbe einmal erzeugt.
     * Das erwartete Ergebnis ist ein Kartenstapel ohne Duplikat mit gleicher Anzahl von Karten.
     */
    /*@Test
    public void testKartenstapelGenerierenErzeugtKeineDuplikate() throws Exception {
        List<Karte> kartenstapel = kartenverwaltung.kartenstapelGenerieren();

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
    */

    /**
     * Testet, ob die Methode Karten Mischen die Reihenfolge des Kartenstapels ändert.
     * Das erwartete Ergebnis ist ein gemischter Kartenstapel
     */
    /*@Test
    public void testKartenMischen() throws Exception {
        List<Karte> kartenstapel = kartenverwaltung.kartenstapelGenerieren();
        kartenverwaltung.kartenMischen(kartenstapel);
        boolean istKartenstapelGemischt = false;
        int i = 0;

        while (i < kartenstapel.size()) {
            if (istKartenstapelGemischt) {
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
    */


    /**
     * Testet, ob die Methode ablagestapelWiederverwenden() den Ablagestapel und den Kartenstapel zu einem
     * neuen Kartenstapel zusammenfügt, mit Ausnahme der obersten Karte des Ablagestapels.
     * Das erwartete Ergebnis ist, dass der neue Kartenstapel so viele Karten hat wie die Kartenmenge
     * des Ablagestapels-1 und des alten Kartenstapels zusammen.
     */
    /*@Test
    public void testAblagestapelWiederverwenden() throws Exception, LeererStapelException {
        List<Karte> ablagestapel = kartenverwaltung.kartenstapelGenerieren();
        List<Karte> kartenstapel = new ArrayList<Karte>();
        int erwarteteAnzahl = ablagestapel.size()-1;
        kartenverwaltung.ablagestapelWiederverwenden(ablagestapel, kartenstapel);

        assertEquals("Der neue Kartenstapel muss " + erwarteteAnzahl + " Karten haben.", erwarteteAnzahl, kartenstapel.size());
    }
    */

}
