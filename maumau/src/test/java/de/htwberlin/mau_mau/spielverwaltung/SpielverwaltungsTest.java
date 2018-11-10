package de.htwberlin.mau_mau.spielverwaltung;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.errorHandling.KeineKarteException;
import de.htw.berlin.maumau.errorHandling.KeineSpielerException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.ISpielverwaltung;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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
    private MauMauSpiel spielService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
    }

    /**
     * Teste die Funktionalität, ein neues Spiel zu starten, indem eine Spielerliste mit 2 Spielern übergeben wird.
     */
    @Test
    void testNeuesSpielStartenMitSpielerliste() {
        spielerliste = new ArrayList<Spieler>();
        spielerliste.add(ingo);
        spielerliste.add(enyang);
        MauMauSpiel neuesSpiel = spielverwaltung.neuesSpielStarten(spielerliste);
        assertEquals("Die erste Runde des neuen Spiels muss 1 sein", 1, neuesSpiel.getRunde());
        assertNotNull("Das neue Spiel muss erstellt worden sein.", neuesSpiel);
    }

    /**
     * Teste die Funktionalität, ein neues Spiel zu starten, indem eine leere Spielerliste übergeben wird.
     */
    @Test
    void testNeuesSpielStartenMitSpielerlisteOhneSpieler() {
        exceptionRule.expect(KeineSpielerException.class);
        spielerliste = new ArrayList<Spieler>();
        spielverwaltung.neuesSpielStarten(spielerliste);
    }

    /**
     * Teste die Funktionalität, ein neues Spiel zu starten, indem keine Spielerliste übergeben wird.
     */
    @Test
    void testNeuesSpielStartenOhneSpielerliste() {
        exceptionRule.expect(KeineSpielerException.class);
        spielverwaltung.neuesSpielStarten(spielerliste);
    }

    /**
     * Teste die Funktionalität, eine neue Runde im Spiel zu starten, angefangen mit der 1. Runde.
     */
    @Test
    void testNeueRundeStarten() {
        spielerliste.add(ingo);
        MauMauSpiel spiel = new MauMauSpiel(spielerliste);
        int alteRunde = spiel.getRunde();
        spielverwaltung.neueRundeStarten(spiel);
        int neueRunde = spiel.getRunde();
        assertEquals("Neue Runde muss um 1 größer sein als die alte Runde", alteRunde + 1, neueRunde);
    }

    /**
     * Teste die Funktionalität, eine neue Karte zu ziehen.
     */
    @Test
    void testKarteZiehen() {
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
     * Teste die Funktionalität, eine neue Karte ohne Angabe eines Spielers zu ziehen.
     */
    @Test
    void testKarteZiehenOhneSpieler() {
        exceptionRule.expect(KeineSpielerException.class);
        spielerliste.add(ingo);
        spielverwaltung.karteZiehen(null, stapel);
    }

    /**
     * Teste die Funktionalität, zwei Karten zu ziehen.
     */
    @Test
    void testZweiKartenZiehen() {
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
     * Teste die Funktionalität, zwei neuen Karten ohne Angabe eines Spielers zu ziehen.
     */
    @Test
    void testZweiKartenZiehenOhneSpieler() {
        exceptionRule.expect(KeineSpielerException.class);
        spielerliste.add(ingo);
        spielverwaltung.karteZiehen(null, stapel, 2);
    }

    /**
     * Teste die Funktionalität, eine Karte zu legen.
     */
    @Test
    void testKarteLegen() {
        spielerliste.add(ingo);
        Spieler spieler = spielerliste.get(0);

        spieler.setHand(hand);
        int alteAnzahlKartenInHand = spieler.getHand().size();
        int alteAblagestapelMenge = stapel.size();

        spielverwaltung.karteLegen(spieler.getHand().get(1), spieler.getHand(), stapel);

        int neueAnzahlKartenInHand = spieler.getHand().size();
        int neueAblagestapelMenge = stapel.size();

        assertEquals("Die Hand muss um 1 Karte verringert sein", neueAnzahlKartenInHand, alteAnzahlKartenInHand - 1);
        assertEquals("Der Ablagestapel muss um 1 Karte erweitert sein", neueAblagestapelMenge, alteAblagestapelMenge + 1);

    }

    /**
     * Teste die Funktionalität, eine Karte zu legen, wenn vorher ein Wunschtyp durch Bube festgelegt wurde.
     */
    @Test
    void testBubeLegen() {
        spielerliste.add(ingo);

        Spieler spieler = spielerliste.get(0);
        spieler.setHand(hand);
        int alteAnzahlKartenInHand = spieler.getHand().size();
        int alteAblagestapelMenge = stapel.size();

        spielverwaltung.karteLegen(spieler.getHand().get(0), spieler.getHand(), stapel);

        int neueAnzahlKartenInHand = spieler.getHand().size();
        int neueAblagestapelMenge = stapel.size();

        assertNotNull("Der Wunschtyp darf nicht null sein", spielService.getWunschtyp());
        assertEquals("Die Hand muss um 1 Karte verringert sein", neueAnzahlKartenInHand, alteAnzahlKartenInHand - 1);
        assertEquals("Der Kartenstapel muss um 1 Karte erweitert sein", neueAblagestapelMenge, alteAblagestapelMenge + 1);

    }

    /**
     * Teste die Funktionalität, die letzte Karte des Ablagestapels zu ermitteln.
     */
    @Test
    void testLetzteKarteVomAblagestapelErmitteln() {
        Karte letzteKarte = spielverwaltung.letzteKarteErmitteln(stapel);
        assertEquals("Die letzte Karte soll PIK/BUBE sein", letzteKarte, stapel.get(stapel.size() - 1));
    }

    /**
     * Teste die Funktionalität, die letzte Karte im leeren Ablagestapel zu ermitteln.
     */
    @Test
    void testLetzteKarteInLeererListeErmitteln() {
        exceptionRule.expect(KeineKarteException.class);
        spielverwaltung.letzteKarteErmitteln(new ArrayList<Karte>());
    }

    /**
     * Teste die Funktionalität zu prüfen, ob ein Spieler MauMau gerufen hat und ob er zur Strafe 2 Karten gezogen hat.
     */
    @Test
    void testMauMauPruefenWennTrue() {
        ingo.setHatMauGerufen(true);
        int alteKartenstapelMenge = stapel.size();
        spielverwaltung.maumauPruefen(ingo, stapel);
        int neueKartenstapelMenge = stapel.size();

        assertEquals("Der Kartenstapel soll sich nicht verrringert haben, wenn MauMau gerufen wurde", alteKartenstapelMenge, neueKartenstapelMenge);
    }

    /**
     * Teste die Funktionalität zu prüfen, ob ein Spieler MauMau gerufen hat und ob er zur Strafe 2 Karten gezogen hat.
     */
    @Test
    void testMauMauPruefenWennFalse() {
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
     */
    @Test
    void testMauMauRufen() {
        ingo.setHatMauGerufen(false);
        spielverwaltung.maumauRufen(ingo);

        assertTrue("MauMauGerufen soll true sein", ingo.hatMauGerufen());
    }

    /**
     * Teste die Funktionalität, Minuspunkte anhand der Karten in seiner Hand zu ermitteln.
     */
    @Test
    void testMinuspunkteBerechnen() {
        assertEquals("", 19, spielverwaltung.minuspunkteBerechnen(hand));
    }


}
