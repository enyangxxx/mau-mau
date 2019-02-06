package de.htwberlin.maumau.spielerverwaltung;

import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.KeinSpielerException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoFindException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoUpdateException;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl.SpielerDao;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl.SpielerverwaltungImpl;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.ISpielerverwaltung;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl.MauMauSpielDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
@RunWith(value = MockitoJUnitRunner.class)
public class SpielerverwaltungsTest {

    @Mock
    MauMauSpielDao mauMauSpielDao;

    @Mock
    SpielerDao spielerDao;

    @InjectMocks
    ISpielerverwaltung spielerverwaltung = new SpielerverwaltungImpl(spielerDao, mauMauSpielDao);

    private Spieler caner = new Spieler("Caner", 9, false);
    private Spieler enyang = new Spieler("Enyang", 10, false);
    private List<Spieler> spielerliste;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        spielerliste = new ArrayList<Spieler>();
    }

    /**
     * Testet, ob ein neuer Spieler zur Spielerliste hinzugefügt werden kann.
     * Das erwartete Ergebnis ist alteAnzahlSpielerInListe + 1 == neueAnzahlSpielerInListe
     */
    @Test
    public void testAddSpielerZurListe() throws DaoFindException {
        int alteAnzahlSpielerInListe = spielerliste.size();
        spielerverwaltung.addSpielerZurListe(caner, spielerliste);

        int neueAnzahlSpielerInListe = spielerliste.size();
        assertEquals("Zur Spielerliste muss ein Spieler hinzugefügt worden sein.", alteAnzahlSpielerInListe + 1, neueAnzahlSpielerInListe);
    }

    /**
     * Testet, ob gewechselt werden kann, welcher Spieler gerade am Zug ist.
     * Das erwartete Ergebnis ist Enyang ist dran, Caner ist nicht dran
     */
    @Test
    public void testSpielerWechseln() throws DaoUpdateException, DaoFindException {
        when(mauMauSpielDao.findSpielerlist()).thenReturn(new ArrayList<Spieler>() {{
            add(caner);
            add(enyang);
        }});
        when(spielerDao.findAktuellerSpielerId()).thenReturn(9);
        when(spielerDao.findBys_id(9)).thenReturn(caner);
        when(mauMauSpielDao.findAssAktivStatus()).thenReturn(false);
        when(spielerDao.findBys_id(10)).thenReturn(enyang);

        spielerverwaltung.spielerWechseln();
        assertTrue("Enyang muss dran sein.", enyang.isDran());
        assertFalse("Caner darf nicht mehr dran sein.", caner.isDran());
    }

    /**
     * Testet, ob ein existierender Spieler anhand seiner ID zurückgegeben werden kann.
     * Das erwartete Ergebnis ist der Spieler Caner
     */
    @Test
    public void testGetSpielerById() throws KeinSpielerException, DaoFindException {
        when(spielerDao.findBys_id(anyInt())).thenReturn(caner);
        when(mauMauSpielDao.findSpielerlist()).thenReturn(new ArrayList<Spieler>() {{
            add(caner);
        }});

        spielerverwaltung.addSpielerZurListe(caner, spielerliste);
        assertEquals("Der Spieler Caner mit der ID 9 muss zurückgegeben werden.", caner, spielerverwaltung.getSpielerById(9, spielerliste));

        verify(spielerDao, times(2)).findBys_id(anyInt());
        verify(mauMauSpielDao, times(1)).findSpielerlist();
    }

    /**
     * Testet, ob kein Spieler zu einer ID gefunden werden kann, die nicht vergeben ist.
     * Das erwartete Ergebnis ist KeinSpielerException.
     */
    @Test(expected = KeinSpielerException.class)
    public void testgetSpielerByIdIdNichtVergeben() throws KeinSpielerException, DaoFindException {
        when(spielerDao.findBys_id(anyInt())).thenReturn(enyang);
        when(mauMauSpielDao.findSpielerlist()).thenReturn(new ArrayList<Spieler>() {{
            add(enyang);
        }});

        spielerverwaltung.addSpielerZurListe(enyang, spielerliste);
        spielerverwaltung.getSpielerById(9, spielerliste);

        assertFalse(throwKeinSpielerException());
    }

    private boolean throwKeinSpielerException() throws KeinSpielerException {
        throw new KeinSpielerException("Kein Spieler konnte gefunden werden");
    }


    /**
     * Testet, ob die Methode KartenAusteilen jedem Spieler aus der Spielerliste 5 Karten austeilt,
     * ob der Kartenstapel um 11 Karten reduziert wurde und ob der Ablagestapel 1 Karte enthält.
     * Das erwartete Ergebnis ist dass jeder Spieler jeweils 5 Karten, der Ablagestapel 1 Karte,
     * der Kartenstapel 21
     */
    /*@Test
    public void testKartenAusteilen() throws LeererStapelException, Exception {
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
    */
}
