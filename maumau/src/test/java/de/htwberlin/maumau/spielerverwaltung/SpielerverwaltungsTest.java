package de.htwberlin.maumau.spielerverwaltung;

import de.htw.berlin.maumau.errorHandling.IdDuplikatException;
import de.htw.berlin.maumau.errorHandling.KeineSpielerException;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl.SpielerverwaltungImpl;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.ISpielerverwaltung;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class SpielerverwaltungsTest {

    private ISpielerverwaltung spielerverwaltung;
    private List<Spieler> spielerliste;
    private Spieler caner = new Spieler( "Caner",9,false);
    private Spieler enyang = new Spieler( "Enyang",2,false);

    @Before
    public void setUp(){
        spielerverwaltung = new SpielerverwaltungImpl();
        spielerliste = new ArrayList<Spieler>();
    }

    /**
     * Testet, ob ein Spieler mit Namen, Alter und als Computer generiert werden kann, der nicht Null ist.
     * Das erwartete Ergebnis ist not null.
     */
    @Test
    public void testSpielerGenerierenIsNotNull(){
        Spieler otto = spielerverwaltung.spielerGenerieren( "Otto",  21,  true);
        assertNotNull("Der generierte Spieler darf nicht Null sein.", otto);
    }


    /**
     * Testet, ob ein Spieler mit einer ID generiert werden kann, die bereits zu einem anderen Spieler gehört.
     * Das erwartete Ergebnis ist eine IdDuplikatException.
     */
    @Test (expected = IdDuplikatException.class)
    public void testAddSpielerZurListeIdBereitsVergeben() throws IdDuplikatException{
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
        caner.setIstDran(true);
        assertTrue("Caner muss dran sein.", caner.istDran());

        spielerverwaltung.spielerWechseln(caner,enyang);
        assertTrue("Enyang muss dran sein.", enyang.istDran());
        assertFalse("Caner darf nicht mehr dran sein.", caner.istDran());
    }

    /**
     * Testet, ob der Spieler immer noch am Zug ist, wenn er den Spielzug mit sich selbst wechselt.
     * Das erwartete Ergebnis ist Caner ist immer noch dran
     */
    @Test
    public void testSpielerWechselnGleicherSpieler(){
        caner.setIstDran(true);
        assertTrue("Caner muss dran sein.", caner.istDran());

        spielerverwaltung.spielerWechseln(caner,caner);
        assertTrue("Caner muss immer noch dran sein.", caner.istDran());
    }

    /**
     * Testet, ob ein existierender Spieler anhand seiner ID zurückgegeben werden kann.
     * Das erwartete Ergebnis ist der Spieler Caner
     */
    @Test
    public void testGetSpielerById() throws KeineSpielerException, IdDuplikatException {
        spielerverwaltung.addSpielerZurListe(caner, spielerliste);
        assertEquals("Der Spieler Caner mit der ID 9 muss zurückgegeben werden.", caner,  spielerverwaltung.getSpielerById(9, spielerliste));
    }


    /**
     * Testet, ob kein Spieler zu einer ID gefunden werden kann, die nicht vergeben ist.
     * Das erwartete Ergebnis ist KeineSpielerException.
     */
    @Test(expected = KeineSpielerException.class)
    public void testgetSpielerByIdIdNichtVergeben() throws KeineSpielerException, IdDuplikatException {
        spielerverwaltung.addSpielerZurListe(enyang, spielerliste);
        spielerverwaltung.getSpielerById(9, spielerliste);
    }
}
