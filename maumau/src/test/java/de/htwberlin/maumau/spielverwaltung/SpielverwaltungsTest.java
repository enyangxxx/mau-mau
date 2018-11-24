package de.htwberlin.maumau.spielverwaltung;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.errorHandling.KeineKarteException;
import de.htw.berlin.maumau.errorHandling.KeineSpielerException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl.SpielverwaltungImpl;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.ISpielverwaltung;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class SpielverwaltungsTest {

    private ISpielverwaltung spielverwaltung;
    private static final Spieler ingo = new Spieler("Ingo", 1, false);
    private static final Spieler enyang = new Spieler("Enyang", 2, false);
    private ArrayList<Karte> stapel = new ArrayList<Karte>() {{
        add(new Karte(Kartentyp.HERZ, Kartenwert.ACHT));
        add(new Karte(Kartentyp.KREUZ, Kartenwert.NEUN));
        add(new Karte(Kartentyp.PIK, Kartenwert.BUBE));
    }};
    private ArrayList<Karte> hand = new ArrayList<Karte>() {{
        add(new Karte(Kartentyp.HERZ, Kartenwert.BUBE));
        add(new Karte(Kartentyp.KREUZ, Kartenwert.NEUN));
    }};
    private List<Spieler> spielerliste;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        spielverwaltung = new SpielverwaltungImpl();
        spielerliste = new ArrayList<Spieler>();
    }

    /**
     * Teste die Funktionalität, ein neues Spiel zu starten, indem eine Spielerliste mit 2 Spielern übergeben wird.
     * Das erwartete Ergebnis ist ein neues Spiel mit der Runde 1
     */
    @Test
    public void testNeuesSpielStartenMitSpielerliste() throws KeineSpielerException{
        spielerliste.add(ingo);
        spielerliste.add(enyang);
        MauMauSpiel neuesSpiel = spielverwaltung.neuesSpielStarten(spielerliste);
        assertEquals("Die erste Runde des neuen Spiels muss 1 sein", 1, neuesSpiel.getRunde());
        assertNotNull("Das neue Spiel muss erstellt worden sein.", neuesSpiel);
    }

    /**
     * Teste die Funktionalität, ein neues Spiel zu starten, indem eine leere Spielerliste übergeben wird.
     * Das erwartete Ergebnis ist KeineSpielerException
     */
    @Test
    public void testNeuesSpielStartenMitSpielerlisteOhneSpieler() throws KeineSpielerException{
        exceptionRule.expect(KeineSpielerException.class);
        spielverwaltung.neuesSpielStarten(spielerliste);
    }

    /**
     * Teste die Funktionalität, eine neue Runde im Spiel zu starten, angefangen mit der 1. Runde.
     * Das erwartete Ergebnis ist alteRunde + 1 == neueRunde
     */
    @Test
    public void testNeueRundeStarten() {
        spielerliste.add(ingo);
        MauMauSpiel spiel = new MauMauSpiel(spielerliste);
        int alteRunde = spiel.getRunde();
        spielverwaltung.neueRundeStarten(spiel);
        int neueRunde = spiel.getRunde();
        assertEquals("Neue Runde muss um 1 größer sein als die alte Runde", alteRunde + 1, neueRunde);
    }

    /**
     * Teste die Funktionalität, eine neue Karte zu ziehen.
     * Das erwartete Ergebnis ist alteAnzahlKartenInHand + 1 && alteKartenstapelMenge - 1
     */
    @Test
    public void testKarteZiehen() {
        spielerliste.add(ingo);
        Spieler spieler = spielerliste.get(0);

        int alteAnzahlKartenInHand = spieler.getHand().size();
        int alteKartenstapelMenge = stapel.size();

        spielverwaltung.karteZiehen(spieler, stapel);

        int neueAnzahlKartenInHand = spieler.getHand().size();
        int neueKartenstapelMenge = stapel.size();

        assertEquals("Die Hand muss um 1 Karte erweitert sein", alteAnzahlKartenInHand + 1, neueAnzahlKartenInHand);
        assertEquals("Der Kartenstapel muss um 1 Karte verringert sein", alteKartenstapelMenge - 1, neueKartenstapelMenge);
    }

    /**
     * Teste die Funktionalität, zwei Karten zu ziehen.
     * Das erwartete Ergebnis ist alteAnzahlKartenInHand + 2 && alteKartenstapelMenge - 2
     */
    @Test
    public void testZweiKartenZiehen() {
        spielerliste.add(ingo);
        Spieler spieler = spielerliste.get(0);

        int alteAnzahlKartenInHand = spieler.getHand().size();
        int alteKartenstapelMenge = stapel.size();

        spielverwaltung.karteZiehen(spieler, stapel, 2);

        int neueAnzahlKartenInHand = spieler.getHand().size();
        int neueKartenstapelMenge = stapel.size();

        assertEquals("Die Hand muss um 2 Karten erweitert sein", alteAnzahlKartenInHand + 2, neueAnzahlKartenInHand);
        assertEquals("Der Kartenstapel muss um 2 Karten verringert sein", alteKartenstapelMenge - 2, neueKartenstapelMenge);

    }

    /**
     * Teste die Funktionalität, zwei Karten zu ziehen.
     * Das erwartete Ergebnis ist alteAnzahlKartenInHand + 2 && alteKartenstapelMenge - 2
     */
    @Test
    public void testDreiKartenZiehen() {
        spielerliste.add(ingo);
        Spieler spieler = spielerliste.get(0);

        int alteAnzahlKartenInHand = spieler.getHand().size();
        int alteKartenstapelMenge = stapel.size();

        spielverwaltung.karteZiehen(spieler, stapel, 3);

        int neueAnzahlKartenInHand = spieler.getHand().size();
        int neueKartenstapelMenge = stapel.size();

        assertEquals("Die Hand muss um 3 Karten erweitert sein", alteAnzahlKartenInHand + 3, neueAnzahlKartenInHand);
        assertEquals("Der Kartenstapel muss um 3 Karten verringert sein", alteKartenstapelMenge - 3, neueKartenstapelMenge);

    }

    /**
     * Teste die Funktionalität, eine Karte zu legen.
     * Das erwartete Ergebnis ist alteAnzahlKartenInHand - 1 && alteAblagestapelMenge + 1
     */
    @Test
    public void testKarteLegen() {
        spielerliste.add(ingo);
        Spieler spieler = spielerliste.get(0);

        spieler.setHand(hand);
        int alteAnzahlKartenInHand = spieler.getHand().size();
        int alteAblagestapelMenge = stapel.size();

        spielverwaltung.karteLegen(spieler.getHand().get(1), spieler.getHand(), stapel);

        int neueAnzahlKartenInHand = spieler.getHand().size();
        int neueAblagestapelMenge = stapel.size();

        assertEquals("Die Hand muss um 1 Karte verringert sein", alteAnzahlKartenInHand - 1,neueAnzahlKartenInHand);
        assertEquals("Der Ablagestapel muss um 1 Karte erweitert sein", alteAblagestapelMenge + 1, neueAblagestapelMenge);

    }

    /**
     * Teste die Funktionalität, eine Karte zu legen, wenn vorher ein Wunschtyp durch Bube festgelegt wurde.
     * Das erwartete Ergebnis ist ein gesetzter Wunschtyp && alteAnzahlKartenInHand - 1 && alteAblagestapelMenge + 1
     */
    @Test
    public void testBubeLegen() {
        spielerliste.add(ingo);
        MauMauSpiel maumauSpiel = new MauMauSpiel(spielerliste);

        Spieler spieler = spielerliste.get(0);
        spieler.setHand(hand);
        int alteAnzahlKartenInHand = spieler.getHand().size();
        int alteAblagestapelMenge = stapel.size();

        spielverwaltung.karteLegen(maumauSpiel, spieler.getHand().get(0), spieler.getHand(),stapel, Kartentyp.HERZ);

        int neueAnzahlKartenInHand = spieler.getHand().size();
        int neueAblagestapelMenge = stapel.size();

        assertNotNull("Der Wunschtyp darf nicht null sein", maumauSpiel.getWunschtyp());
        assertEquals("Der Wunschtyp muss HERZ sein",Kartentyp.HERZ,maumauSpiel.getWunschtyp());
        assertEquals("Die Hand muss um 1 Karte verringert sein", alteAnzahlKartenInHand - 1, neueAnzahlKartenInHand);
        assertEquals("Der Ablagestapel muss um 1 Karte erweitert sein", alteAblagestapelMenge + 1, neueAblagestapelMenge);

    }

    /**
     * Teste die Funktionalität, die letzte Karte des Ablagestapels zu ermitteln.
     * Das erwartete Ergebnis ist die Karte PIK/BUBE
     */
    @Test
    public void testLetzteKarteVomAblagestapelErmitteln() throws KeineKarteException {
        Karte letzteKarte = spielverwaltung.letzteKarteErmitteln(stapel);
        assertEquals("Die letzte Karte soll PIK/BUBE sein", letzteKarte, stapel.get(stapel.size() - 1));
    }

    /**
     * Teste die Funktionalität, die letzte Karte im leeren Ablagestapel zu ermitteln.
     * Das erwartete Ergebnis ist KeineKarteException
     */
    @Test
    public void testLetzteKarteInLeererListeErmitteln() throws KeineKarteException{
        exceptionRule.expect(KeineKarteException.class);
        spielverwaltung.letzteKarteErmitteln(new ArrayList<Karte>());
    }

    /**
     * Teste die Funktionalität zu prüfen, ob ein Spieler MauMau gerufen hat und ob er zur Strafe 2 Karten gezogen hat.
     * Das erwartete Ergebnis ist ein unveränderter Kartenstapel, bedeutet dass Spieler keine Karten zur Strafe gezogen hat
     */
    @Test
    public void testMauMauPruefenWennTrue() {
        ingo.setHatMauGerufen(true);
        int alteKartenstapelMenge = stapel.size();
        spielverwaltung.maumauPruefen(ingo, stapel);
        int neueKartenstapelMenge = stapel.size();

        assertEquals("Der Kartenstapel soll sich nicht verrringert haben, wenn MauMau gerufen wurde", alteKartenstapelMenge, neueKartenstapelMenge);
    }

    /**
     * Teste die Funktionalität zu prüfen, ob ein Spieler MauMau gerufen hat und ob er zur Strafe 2 Karten gezogen hat.
     * Das erwartete Ergebnis ist alteMengeInHand + 2 && alteKartenstapelMenge - 2
     */
    @Test
    public void testMauMauPruefenWennFalse() {
        ingo.setHatMauGerufen(false);

        int alteKartenstapelMenge = stapel.size();
        int alteMengeInHand = ingo.getHand().size();

        spielverwaltung.maumauPruefen(ingo, stapel);

        int neueKartenstapelMenge = stapel.size();
        int neueMengeInHand = ingo.getHand().size();

        assertEquals("Die Hand soll sich um 2 Karten erweitert haben, wenn MauMau nicht gerufen wurde", alteMengeInHand + 2, neueMengeInHand);
        assertEquals("Der Kartenstapel soll sich um 2 Karten verrringert haben, wenn MauMau nicht gerufen wurde", alteKartenstapelMenge - 2, neueKartenstapelMenge);
    }

    /**
     * Teste die Funktionalität, MauMau zu rufen.
     * Das erwartete Ergebnis ist dass Ingo MauMau gerufen hat
     */
    @Test
    public void testMauMauRufen() {
        ingo.setHatMauGerufen(false);
        spielverwaltung.maumauRufen(ingo);

        assertTrue("MauMauGerufen soll true sein", ingo.hatMauGerufen());
    }

    /**
     * Teste die Funktionalität, Minuspunkte anhand der Karten in seiner Hand zu ermitteln.
     * Das erwartete Ergebnis ist 11
     */
    @Test
    public void testMinuspunkteBerechnen() {
        assertEquals("", 11, spielverwaltung.minuspunkteBerechnen(hand));
    }

}
