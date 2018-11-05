package de.htwberlin.mau_mau.spielverwaltung;

import de.htw.berlin.maumau.errorHandling.KeineSpielerException;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.ISpielverwaltung;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SpielverwaltungsTest {

    ISpielverwaltung spielverwaltung;
    private Spieler ingo = new Spieler("Ingo", 1, false);
    private Spieler enyang = new Spieler("Enyang", 2, false);
    List<Spieler> spielerliste;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    void testNeuesSpielStartenMitSpielerliste(){
        spielerliste = new ArrayList<Spieler>();
        spielerliste.add(ingo);
        spielerliste.add(enyang);
        assertEquals("Die erste Runde des neuen Spiels muss 1 sein",1,spielverwaltung.neuesSpielStarten(spielerliste).getRunde());
    }

    @Test
    void testNeuesSpielStartenMitSpielerlisteOhneSpieler(){
        exceptionRule.expect(KeineSpielerException.class);
        spielerliste = new ArrayList<Spieler>();
        spielverwaltung.neuesSpielStarten(spielerliste).getRunde();
    }

    @Test
    void testNeuesSpielStartenOhneSpielerliste(){
        exceptionRule.expect(KeineSpielerException.class);
        spielverwaltung.neuesSpielStarten(spielerliste).getRunde();
    }
}
