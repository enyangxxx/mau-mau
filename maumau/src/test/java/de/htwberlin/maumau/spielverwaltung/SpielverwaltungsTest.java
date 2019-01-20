package de.htwberlin.maumau.spielverwaltung;

import de.htw.berlin.maumau.configurator.ConfigServiceImpl;
import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.KeinSpielerException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.KarteNichtGezogenException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.LeererStapelException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.ISpielerverwaltung;
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

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */


public class SpielverwaltungsTest {

    private ISpielverwaltung spielverwaltung;
    private ISpielerverwaltung spielerverwaltung;

    private static final Spieler hans = new Spieler("hans", 1, false);
    private static final Spieler enyang = new Spieler("Enyang", 2, false);

    private ArrayList<Karte> ablagestapel = new ArrayList<Karte>() {{
        add(new Karte(Kartentyp.HERZ, Kartenwert.ACHT));
        add(new Karte(Kartentyp.KREUZ, Kartenwert.NEUN));
        add(new Karte(Kartentyp.PIK, Kartenwert.SIEBEN));
    }};

    private ArrayList<Karte> kartenstapel = new ArrayList<Karte>() {{
        add(new Karte(Kartentyp.HERZ, Kartenwert.ACHT));
        add(new Karte(Kartentyp.KREUZ, Kartenwert.NEUN));
        add(new Karte(Kartentyp.PIK, Kartenwert.SIEBEN));
    }};

    private ArrayList<Karte> hand = new ArrayList<Karte>() {{
        add(new Karte(Kartentyp.HERZ, Kartenwert.BUBE));
        add(new Karte(Kartentyp.KREUZ, Kartenwert.NEUN));
        add(new Karte(Kartentyp.KREUZ, Kartenwert.SIEBEN));
    }};

    private List<Spieler> spielerliste;


    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        spielverwaltung = (ISpielverwaltung) ConfigServiceImpl.context.getBean("spielverwaltungimpl");
        spielerverwaltung = (ISpielerverwaltung) ConfigServiceImpl.context.getBean("spielerverwaltungimpl");
        spielerliste = new ArrayList<Spieler>();

    }

    /**
     * Testet die Funktionalität, ein neues Spiel zu starten, indem eine Spielerliste mit 2 Spielern übergeben wird.
     * Das erwartete Ergebnis ist ein neues Spiel mit der Runde 1.
     */
    @Test
    public void testNeuesSpielStartenMitSpielerliste() throws KeinSpielerException, Exception {
        spielerliste.add(hans);
        spielerliste.add(enyang);
        MauMauSpiel neuesSpiel = spielverwaltung.neuesSpielStarten(spielerliste);
        assertEquals("Die erste Runde des neuen Spiels muss 1 sein", 1, neuesSpiel.getRunde());
        assertNotNull("Das neue Spiel muss erstellt worden sein.", neuesSpiel);
    }

    /**
     * Testet die Funktionalität, ein neues Spiel zu starten, indem eine leere Spielerliste übergeben wird.
     * Das erwartete Ergebnis ist KeinSpielerException
     */
    @Test
    public void testNeuesSpielStartenMitSpielerlisteOhneSpieler() throws KeinSpielerException, Exception {
        exceptionRule.expect(KeinSpielerException.class);
        spielverwaltung.neuesSpielStarten(spielerliste);
    }

    /**
     * Teste die Funktionalität, eine neue Runde im Spiel zu starten, angefangen mit der 1. Runde.
     * Das erwartete Ergebnis ist alteRunde + 1 == neueRunde
     */
    @Test
    public void testNeueRundeStarten() {
        spielerliste.add(hans);
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
    public void testKarteZiehen() throws KarteNichtGezogenException, LeererStapelException {
        spielerliste.add(hans);
        Spieler spieler = spielerliste.get(0);
        MauMauSpiel spiel = new MauMauSpiel(spielerliste);
        spiel.setAblagestapel(ablagestapel);
        spiel.setKartenstapel(kartenstapel);


        int alteAnzahlKartenInHand = spieler.getHand().size();
        int alteKartenstapelMenge = spiel.getKartenstapel().size();

        spielverwaltung.karteZiehen(spieler, spiel);

        int neueAnzahlKartenInHand = spieler.getHand().size();
        int neueKartenstapelMenge = spiel.getKartenstapel().size();

        assertEquals("Die Hand muss um 1 Karte erweitert sein", alteAnzahlKartenInHand + 1, neueAnzahlKartenInHand);
        assertEquals("Der Kartenstapel muss um 1 Karte verringert sein", alteKartenstapelMenge - 1, neueKartenstapelMenge);
    }

    /**
     * Teste die Funktionalität, zwei Karten zu ziehen.
     * Das erwartete Ergebnis ist alteAnzahlKartenInHand + 2 && alteKartenstapelMenge - 2
     */
    @Test
    public void testZweiKartenZiehen() throws KarteNichtGezogenException, LeererStapelException {
        spielerliste.add(hans);
        Spieler spieler = spielerliste.get(0);
        MauMauSpiel spiel = new MauMauSpiel(spielerliste);
        spiel.setAblagestapel(ablagestapel);
        spiel.setKartenstapel(kartenstapel);

        spiel.setAnzahlSonderregelKartenZiehen(2);

        int alteAnzahlKartenInHand = spieler.getHand().size();
        int alteKartenstapelMenge = spiel.getKartenstapel().size();

        spielverwaltung.karteZiehenSonderregel(spieler, spiel);

        int neueAnzahlKartenInHand = spieler.getHand().size();
        int neueKartenstapelMenge = spiel.getKartenstapel().size();

        assertEquals("Die Hand muss um 2 Karten erweitert sein", alteAnzahlKartenInHand + 2, neueAnzahlKartenInHand);
        assertEquals("Der Kartenstapel muss um 2 Karten verringert sein", alteKartenstapelMenge - 2, neueKartenstapelMenge);

    }

    /**
     * Teste die Funktionalität, zwei Karten zu ziehen.
     * Das erwartete Ergebnis ist alteAnzahlKartenInHand + 2 && alteKartenstapelMenge - 2
     */
    @Test
    public void testDreiKartenZiehen() throws KarteNichtGezogenException, LeererStapelException {
        spielerliste.add(hans);
        MauMauSpiel spiel = new MauMauSpiel(spielerliste);
        spiel.setAblagestapel(ablagestapel);
        spiel.setKartenstapel(kartenstapel);
        spiel.setAnzahlSonderregelKartenZiehen(3);

        Spieler spieler = spielerliste.get(0);

        int alteAnzahlKartenInHand = spieler.getHand().size();
        int alteKartenstapelMenge = spiel.getKartenstapel().size();

        spielverwaltung.karteZiehenSonderregel(spieler, spiel);

        int neueAnzahlKartenInHand = spieler.getHand().size();
        int neueKartenstapelMenge = spiel.getKartenstapel().size();

        assertEquals("Die Hand muss um 3 Karten erweitert sein", alteAnzahlKartenInHand + 3, neueAnzahlKartenInHand);
        assertEquals("Der Kartenstapel muss um 3 Karten verringert sein", alteKartenstapelMenge - 3, neueKartenstapelMenge);

    }

    /**
     * Teste die Funktionalität, eine Karte zu ziehen, wenn der Kartenstapel leer ist.
     * Das erwartete Ergebnis ist, dass der Kartenstapel die Karten des Ablagestapels-1 erhält
     * und somit nicht mehr leer ist. Nach dem Ziehen der Karte darf der Kartenstapel nur noch eine
     * Karte enthalten. Erwartet wird alteAnzahlKartenInHand + 1 und dass der ablagestapel nicht leer ist.
     */
    @Test
    public void testKarteZiehenLeererStapel() throws KarteNichtGezogenException, LeererStapelException {
        List<Karte> kartenstapel = new ArrayList<Karte>();
        spielerliste.add(hans);
        MauMauSpiel spiel = new MauMauSpiel(spielerliste);
        spiel.setAblagestapel(ablagestapel);
        spiel.setKartenstapel(kartenstapel);
        Spieler spieler = spielerliste.get(0);

        int alteAnzahlKartenInHand = spieler.getHand().size();

        spielverwaltung.karteZiehen(spieler, spiel);

        assertFalse("Der Kartenstapel darf nicht leer sein.", kartenstapel.isEmpty());
        assertEquals("Der Kartenstapel muss eine Karte enthalten.", ablagestapel.size(), kartenstapel.size());
        assertEquals("Die Hand muss um 1 Karte erweitert sein.", alteAnzahlKartenInHand + 1, spieler.getHand().size());
        assertTrue("Der Ablagestapel darf nicht leer sein.", !ablagestapel.isEmpty());
    }

    /**
     * Teste die Funktionalität, eine Karte nicht zu legen, weil diese nicht legbar ist.
     * Das erwartete Ergebnis ist, dass die Hand und der Ablagestapel sich nicht verändern.
     */
    @Test
    public void testKarteNichtLegen() throws KeinSpielerException, KarteNichtGezogenException, LeererStapelException {
        MauMauSpiel maumauSpiel = new MauMauSpiel(spielerliste);
        maumauSpiel.setAblagestapel(ablagestapel);

        spielerliste.add(hans);
        Spieler spieler = spielerliste.get(0);

        spieler.setHand(hand);
        int alteAnzahlKartenInHand = spieler.getHand().size();
        int alteAblagestapelMenge = maumauSpiel.getAblagestapel().size();

        spielverwaltung.karteLegen(spieler.getHand().get(1), spieler.getHand(), maumauSpiel);

        int neueAnzahlKartenInHand = spieler.getHand().size();
        int neueAblagestapelMenge = maumauSpiel.getAblagestapel().size();

        assertEquals("Die Hand soll sich nicht verringern.", alteAnzahlKartenInHand, neueAnzahlKartenInHand);
        assertEquals("Der Ablagestapel darf nicht verändert werden.", alteAblagestapelMenge, neueAblagestapelMenge);

    }

    /**
     * Teste die Funktionalität, eine Karte zu legen.
     * Das erwartete Ergebnis ist alteAnzahlKartenInHand - 1 && alteAblagestapelMenge + 1
     */
    @Test
    public void testKarteLegen() throws KeinSpielerException, KarteNichtGezogenException, LeererStapelException {
        MauMauSpiel maumauSpiel = new MauMauSpiel(spielerliste);
        maumauSpiel.setAblagestapel(ablagestapel);

        spielerliste.add(hans);
        Spieler spieler = spielerliste.get(0);

        spieler.setHand(hand);
        int alteAnzahlKartenInHand = spieler.getHand().size();
        int alteAblagestapelMenge = maumauSpiel.getAblagestapel().size();

        spielverwaltung.karteLegen(spieler.getHand().get(2), spieler.getHand(), maumauSpiel);

        int neueAnzahlKartenInHand = spieler.getHand().size();
        int neueAblagestapelMenge = maumauSpiel.getAblagestapel().size();

        assertEquals("Die Hand soll um 1 verringert werden.", alteAnzahlKartenInHand - 1, neueAnzahlKartenInHand);
        assertEquals("Der Ablagestapel muss um 1 erweitert werden.", alteAblagestapelMenge + 1, neueAblagestapelMenge);
    }


    /**
     * Teste die Funktionalität, die letzte Karte des Ablagestapels zu ermitteln.
     * Das erwartete Ergebnis ist die Karte PIK/BUBE
     */
    @Test
    public void testLetzteKarteVomAblagestapelErmitteln() {
        Karte letzteKarte = spielverwaltung.letzteKarteErmitteln(ablagestapel);
        assertEquals("Die letzte Karte soll PIK/BUBE sein", letzteKarte, ablagestapel.get(ablagestapel.size() - 1));
    }

    /**
     * Teste die Funktionalität zu prüfen, ob ein Spieler MauMau gerufen hat und ob er zur Strafe 2 Karten gezogen hat.
     * Das erwartete Ergebnis ist ein unveränderter Kartenstapel, bedeutet dass Spieler keine Karten zur Strafe gezogen hat
     */
    @Test
    public void testMauMauPruefenWennTrue() throws KarteNichtGezogenException, LeererStapelException {
        MauMauSpiel spiel = new MauMauSpiel(spielerliste);
        hans.setHatMauGerufen(true);
        int alteKartenstapelMenge = ablagestapel.size();
        spielverwaltung.maumauPruefen(hans, spiel);
        int neueKartenstapelMenge = ablagestapel.size();

        assertEquals("Der Kartenstapel soll sich nicht verrringert haben, wenn MauMau gerufen wurde", alteKartenstapelMenge, neueKartenstapelMenge);
    }

    /**
     * Teste die Funktionalität zu prüfen, ob ein Spieler MauMau gerufen hat und ob er zur Strafe 2 Karten gezogen hat.
     * Das erwartete Ergebnis ist alteMengeInHand + 2 && alteKartenstapelMenge - 2
     */
    @Test
    public void testMauMauPruefenWennFalse() throws KarteNichtGezogenException, LeererStapelException {
        MauMauSpiel spiel = new MauMauSpiel(spielerliste);
        spiel.setAblagestapel(ablagestapel);
        spiel.setKartenstapel(kartenstapel);

        hans.setHatMauGerufen(false);

        int alteKartenstapelMenge = kartenstapel.size();
        int alteMengeInHand = hans.getHand().size();

        spielverwaltung.maumauPruefen(hans, spiel);

        int neueKartenstapelMenge = kartenstapel.size();
        int neueMengeInHand = hans.getHand().size();

        assertEquals("Die Hand soll sich um 2 Karten erweitert haben, wenn MauMau nicht gerufen wurde", alteMengeInHand + 2, neueMengeInHand);
        assertEquals("Der Kartenstapel soll sich um 2 Karten verrringert haben, wenn MauMau nicht gerufen wurde", alteKartenstapelMenge - 2, neueKartenstapelMenge);
    }

    /**
     * Teste die Funktionalität, MauMau zu rufen.
     * Das erwartete Ergebnis ist, dass Hans MauMau gerufen hat.
     */
    @Test
    public void testMauMauRufen() {
        hans.setHatMauGerufen(false);
        spielverwaltung.maumauRufen(hans);

        assertTrue("MauMauGerufen soll true sein", hans.hatMauGerufen());
    }

    /**
     * Teste die Funktionalität, Minuspunkte anhand der Karten in seiner Hand zu ermitteln.
     * Das erwartete Ergebnis ist 18
     */
    @Test
    public void testMinuspunkteBerechnen() {
        assertEquals("", 18, spielverwaltung.minuspunkteBerechnen(hand));
    }


    /**
     * Teste die Funktionalität, ob ein Spieler aussetzen muss, wenn der vorherige Spieler ein Ass gelegt hat.
     * Das erwartete Ergebnis ist, dass Hans wieder dran ist.
     */
    @Test
    public void testAssAussetzen() throws KeinSpielerException, Exception, KarteNichtGezogenException, LeererStapelException {
        hans.setHand(hand);
        hans.getHand().add(new Karte(Kartentyp.PIK,Kartenwert.ASS));
        enyang.setHand(hand);
        hans.setDran(true);

        spielerliste.add(hans);
        spielerliste.add(enyang);

        MauMauSpiel spiel = spielverwaltung.neuesSpielStarten(spielerliste);
        spiel.setAblagestapel(ablagestapel);
        spiel.setKartenstapel(kartenstapel);

        spielverwaltung.karteLegen(hans.getHand().get(hans.getHand().size()-1), hans.getHand(),spiel);

        assertTrue("Hans muss wieder dran sein.", hans.isDran());
    }
}
