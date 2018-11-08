package de.htwberlin.mau_mau.spielverwaltung;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.errorHandling.KeineKarteException;
import de.htw.berlin.maumau.errorHandling.KeineSpielerException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsImpl.*;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl.*;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl.MauMauSpiel;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
    public void setUp(){
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
        spielverwaltung.neuesSpielStarten(spielerliste);
    }

    @Test
    void testNeuesSpielStartenOhneSpielerliste(){
        exceptionRule.expect(KeineSpielerException.class);
        spielverwaltung.neuesSpielStarten(spielerliste);
    }

    @Test
    void testNeueRundeStarten(){
        spielerliste.add(ingo);
        MauMauSpiel spiel = new MauMauSpiel(spielerliste);
        int alteRunde = spiel.getRunde();
        spielverwaltung.neueRundeStarten(spiel);
        int neueRunde = spiel.getRunde();
        Assert.assertEquals("Neue Runde muss um 1 größer sein als die alte Runde",alteRunde + 1,neueRunde);
    }

    @Test
    void testKarteZiehen(){
        spielerliste.add(ingo);
        Spieler spieler = spielerliste.get(0);

        int alteAnzahlKartenInHand = spieler.getHand().size();
        int alteKartenstapelMenge = stapel.size();

        spielverwaltung.karteZiehen(spieler, stapel);

        int neueAnzahlKartenInHand = spieler.getHand().size();
        int neueKartenstapelMenge = stapel.size();

        assertEquals("Die Hand muss um 1 Karte erweitert sein", neueAnzahlKartenInHand, alteAnzahlKartenInHand + 1);
        assertEquals("Der Kartenstapel muss um 1 Karte verringert sein", neueKartenstapelMenge, alteKartenstapelMenge - 1);
    }

    @Test
    void testKarteZiehenOhneSpieler(){
        exceptionRule.expect(KeineSpielerException.class);
        spielerliste.add(ingo);
        spielverwaltung.karteZiehen(null, stapel);
    }

    @Test
    void testZweiKartenZiehen(){
        spielerliste.add(ingo);
        Spieler spieler = spielerliste.get(0);

        int alteAnzahlKartenInHand = spieler.getHand().size();
        int alteKartenstapelMenge = stapel.size();

        spielverwaltung.karteZiehen(spieler, stapel,2);

        int neueAnzahlKartenInHand = spieler.getHand().size();
        int neueKartenstapelMenge = stapel.size();

        assertEquals("Die Hand muss um 2 Karten erweitert sein", neueAnzahlKartenInHand, alteAnzahlKartenInHand + 2);
        assertEquals("Der Kartenstapel muss um 2 Karten verringert sein", neueKartenstapelMenge, alteKartenstapelMenge - 2);

    }

    @Test
    void testZweiKartenZiehenOhneSpieler(){
        exceptionRule.expect(KeineSpielerException.class);
        spielerliste.add(ingo);
        spielverwaltung.karteZiehen(null, stapel, 2);
    }

    @Test
    void testKartelegenBeiNormalerKarte(){
        spielerliste.add(ingo);

        Spieler spieler = spielerliste.get(0);
        spieler.setHand(hand);
        int alteAnzahlKartenInHand = spieler.getHand().size();
        int alteKartenstapelMenge = stapel.size();

        spielverwaltung.karteLegen(spieler.getHand().get(1), spieler.getHand(), stapel);

        int neueAnzahlKartenInHand = spieler.getHand().size();
        int neueKartenstapelMenge = stapel.size();

        assertEquals("Die Hand muss um 1 Karte verringert sein", neueAnzahlKartenInHand, alteAnzahlKartenInHand - 1);
        assertEquals("Der Kartenstapel muss um 1 Karte erweitert sein", neueKartenstapelMenge, alteKartenstapelMenge + 1);

    }

    @Test
    void testKartelegenBeiBube(){
        spielerliste.add(ingo);

        Spieler spieler = spielerliste.get(0);
        spieler.setHand(hand);
        int alteAnzahlKartenInHand = spieler.getHand().size();
        int alteKartenstapelMenge = stapel.size();

        spielverwaltung.karteLegen(spieler.getHand().get(0), spieler.getHand(), stapel);

        int neueAnzahlKartenInHand = spieler.getHand().size();
        int neueKartenstapelMenge = stapel.size();

        assertNotNull("Der Wunschtyp darf nicht null sein", spielService.getWunschtyp());
        assertEquals("Die Hand muss um 1 Karte verringert sein", neueAnzahlKartenInHand, alteAnzahlKartenInHand - 1);
        assertEquals("Der Kartenstapel muss um 1 Karte erweitert sein", neueKartenstapelMenge, alteKartenstapelMenge + 1);

    }

    @Test
    void testLetzteKarteVomAblagestapelErmitteln(){
        Karte letzteKarte = spielverwaltung.letzteKarteErmitteln(stapel);
        assertEquals("Die letzte Karte soll PIK/BUBE sein", letzteKarte, stapel.get(stapel.size()-1));
    }

    @Test
    void testLetzteKarteInLeererListeErmitteln(){
        exceptionRule.expect(KeineKarteException.class);
        spielverwaltung.letzteKarteErmitteln(new ArrayList<Karte>());
    }

    @Test
    void testMauMauPruefenWennTrue(){
        ingo.setHatMauGerufen(true);
        int alteKartenstapelMenge = stapel.size();
        spielverwaltung.maumauPruefen(ingo,stapel);
        int neueKartenstapelMenge = stapel.size();

        assertEquals("Der Kartenstapel soll sich nicht verrringert haben, wenn MauMau gerufen wurde",alteKartenstapelMenge,neueKartenstapelMenge);
    }

    @Test
    void testMauMauPruefenWennFalse(){
        ingo.setHatMauGerufen(false);

        int alteKartenstapelMenge = stapel.size();
        int alteMengeInHand = ingo.getHand().size();

        spielverwaltung.maumauPruefen(ingo,stapel);

        int neueKartenstapelMenge = stapel.size();
        int neueMengeInHand = ingo.getHand().size();

        assertEquals("Die Hand soll sich um 2 Karten erweitert haben, wenn MauMau nicht gerufen wurde",alteMengeInHand + 2,neueMengeInHand);
        assertEquals("Der Kartenstapel soll sich um 2 Karten verrringert haben, wenn MauMau nicht gerufen wurde",alteKartenstapelMenge - 2,neueKartenstapelMenge);
    }

    @Test
    void testMauMauRufen(){
        ingo.setHatMauGerufen(false);
        spielverwaltung.maumauRufen(ingo);

        assertTrue("MauMauGerufen soll true sein",ingo.hatMauGerufen());
    }

    @Test
    void testMinuspunkteBerechnen(){
        assertEquals("",19, spielverwaltung.minuspunkteBerechnen(hand));
    }


}