package de.htwberlin.maumau.spielerverwaltung;

import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartentyp;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartenwert;
import de.htw.berlin.maumau.errorHandling.IdDuplikatException;
import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.KeinSpielerException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.LeererStapelException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.IKartenverwaltung;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.ISpielerverwaltung;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;
import org.junit.Before;
import org.junit.Test;
import de.htw.berlin.maumau.configurator.ConfigServiceImpl;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class SpielerverwaltungsTest {

    private ISpielerverwaltung spielerverwaltung;
    private List<Spieler> spielerliste;

    @Mock
    private IKartenverwaltung kartenverwaltung;

    private Spieler caner = new Spieler( "Caner",9,false);
    private Spieler enyang = new Spieler( "Enyang",2,false);

    @Before
    public void setUp() throws Exception {
        spielerverwaltung = (ISpielerverwaltung) ConfigServiceImpl.context.getBean("spielerverwaltungimpl");
        spielerliste = new ArrayList<Spieler>();
        MockitoAnnotations.initMocks(this);
        when(kartenverwaltung.kartenstapelGenerieren()).thenReturn(new ArrayList<Karte>() {{
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
        }});
    }

    /**
     * Testet, ob ein Spieler mit Namen, Alter und als Computer generiert werden kann, der nicht Null ist.
     * Das erwartete Ergebnis ist not null.
     */
    @Test
    public void testSpielerGenerierenIsNotNull() throws Exception {
        Spieler otto = spielerverwaltung.spielerGenerieren( "Otto",  21,  true);
        assertNotNull("Der generierte Spieler darf nicht Null sein.", otto);
    }


    /**
     * Testet, ob ein Spieler mit einer ID generiert werden kann, die bereits zu einem anderen Spieler gehört.
     * Das erwartete Ergebnis ist eine IdDuplikatException.
     */
    @Test (expected = IdDuplikatException.class)
    public void testAddSpielerZurListeIdBereitsVergeben() throws Exception {
        Spieler otto = spielerverwaltung.spielerGenerieren( "Otto",  4,  true);
        spielerverwaltung.addSpielerZurListe(otto, spielerliste);
        Spieler hans = spielerverwaltung.spielerGenerieren( "Hans",  4,  false);
        spielerverwaltung.addSpielerZurListe(hans, spielerliste);
    }


    /**
     * Testet, ob ein neuer Spieler zur Spielerliste hinzugefügt werden kann.
     * Das erwartete Ergebnis ist alteAnzahlSpielerInListe + 1 == neueAnzahlSpielerInListe
     */
    @Test
    public void testAddSpielerZurListe() throws IdDuplikatException{
        int alteAnzahlSpielerInListe = spielerliste.size();
        spielerverwaltung.addSpielerZurListe(caner, spielerliste);

        int neueAnzahlSpielerInListe = spielerliste.size();
        assertEquals("Zur Spielerliste muss ein Spieler hinzugefügt worden sein.", alteAnzahlSpielerInListe + 1,neueAnzahlSpielerInListe);
    }


    /**
     * Testet, ob gewechselt werden kann, welcher Spieler gerade am Zug ist.
     * Das erwartete Ergebnis ist Enyang ist dran, Caner ist nicht dran
     */
    @Test
    public void testSpielerWechseln(){
        spielerliste.add(caner);
        spielerliste.add(enyang);
        MauMauSpiel spiel = new MauMauSpiel(spielerliste);
        caner.setDran(true);
        assertTrue("Caner muss dran sein.", caner.isDran());

        spielerverwaltung.spielerWechseln(spiel);
        assertTrue("Enyang muss dran sein.", enyang.isDran());
        assertFalse("Caner darf nicht mehr dran sein.", caner.isDran());
    }

    /**
     * Testet, ob der Spieler immer noch am Zug ist, wenn er den Spielzug mit sich selbst wechselt.
     * Das erwartete Ergebnis ist Caner ist immer noch dran
     */
    @Test
    public void testSpielerWechselnGleicherSpieler(){
        MauMauSpiel spiel = new MauMauSpiel(spielerliste);
        caner.setDran(true);
        assertTrue("Caner muss dran sein.", caner.isDran());

        spielerverwaltung.spielerWechseln(spiel);
        assertTrue("Caner muss immer noch dran sein.", caner.isDran());
    }

    /**
     * Testet, ob ein existierender Spieler anhand seiner ID zurückgegeben werden kann.
     * Das erwartete Ergebnis ist der Spieler Caner
     */
    @Test
    public void testGetSpielerById() throws KeinSpielerException, IdDuplikatException {
        spielerverwaltung.addSpielerZurListe(caner, spielerliste);
        assertEquals("Der Spieler Caner mit der ID 9 muss zurückgegeben werden.", caner,  spielerverwaltung.getSpielerById(9, spielerliste));
    }


    /**
     * Testet, ob kein Spieler zu einer ID gefunden werden kann, die nicht vergeben ist.
     * Das erwartete Ergebnis ist KeinSpielerException.
     */
    @Test(expected = KeinSpielerException.class)
    public void testgetSpielerByIdIdNichtVergeben() throws KeinSpielerException, IdDuplikatException {
        spielerverwaltung.addSpielerZurListe(enyang, spielerliste);
        spielerverwaltung.getSpielerById(9, spielerliste);
    }

    /**
     * Testet, ob die Methode KartenAusteilen jedem Spieler aus der Spielerliste 5 Karten austeilt,
     * ob der Kartenstapel um 11 Karten reduziert wurde und ob der Ablagestapel 1 Karte enthält.
     * Das erwartete Ergebnis ist dass jeder Spieler jeweils 5 Karten, der Ablagestapel 1 Karte,
     * der Kartenstapel 21
     */
    @Test
    public void testKartenAusteilen() throws LeererStapelException {
        List<Karte> kartenstapel = kartenverwaltung.kartenstapelGenerieren();
        List<Karte> ablagestapel = new ArrayList<Karte>();
        List<Spieler> spielerList = new ArrayList<Spieler>() {{
            add(new Spieler("theo", 1, false));
            add(new Spieler("ingo", 2, false));
        }};

        spielerverwaltung.kartenAusteilen(spielerList, kartenstapel, ablagestapel);

        assertEquals("Der Spieler 1 muss 5 Karten haben", 5, spielerList.get(0).getHand().size());
        assertEquals("Der Spieler 2 muss 5 Karten haben", 5, spielerList.get(1).getHand().size());
        assertEquals("Der Kartenstapel muss 21 Karten haben", 21, kartenstapel.size());
        assertEquals("Der Ablagestapel muss 1 Karte haben", 1, ablagestapel.size());
    }
}
