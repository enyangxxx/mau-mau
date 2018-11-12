package de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl;

import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.ISpielverwaltung;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class SpielverwaltungImpl implements ISpielverwaltung {
    public MauMauSpiel neuesSpielStarten(List<Spieler> spielerliste) {
        return null;
    }

    public void neueRundeStarten(MauMauSpiel spiel) {

    }

    public void karteZiehen(Spieler spieler, List<Karte> kartenstapel) {

    }

    public void karteZiehen(Spieler spieler, List<Karte> kartenstapel, int anzahl) {

    }

    public void karteLegen(Karte gewaehlteKarte, List<Karte> hand, List<Karte> ablagestapel) {

    }

    public Karte letzteKarteErmitteln(List<Karte> ablagestapel) {
        return null;
    }

    public boolean maumauPruefen(Spieler spieler, List<Karte> kartenstapel) {
        return false;
    }

    public void maumauRufen(Spieler spieler) {

    }

    public int minuspunkteBerechnen(List<Karte> hand) {
        return 0;
    }
}
