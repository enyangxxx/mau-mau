package de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class MauMauSpiel {

    private List<Spieler> spielerliste;
    private int runde;
    private List<Karte> ablagestapel = new ArrayList<Karte>();
    private List<Karte> kartenstapel = new ArrayList<Karte>();
    private Kartentyp aktuellerWunschtyp;
    private boolean sonderregelSiebenAktiv;
    private boolean sonderregelAssAktiv;
    private int anzahlSonderregelKartenZiehen;
    private int spielId;

    public MauMauSpiel(List<Spieler> spielerliste) {
        this.runde = 1;
        this.anzahlSonderregelKartenZiehen = 0;
        this.spielerliste = spielerliste;
        this.sonderregelSiebenAktiv = false;
        this.sonderregelAssAktiv = false;
    }


    public int getSpielId() {
        return spielId;
    }

    public void setSpielId(int spielId) {
        this.spielId = spielId;
    }

    public List<Spieler> getSpielerliste() {
        return spielerliste;
    }

    public void setSpielerliste(List<Spieler> spielerliste) {
        this.spielerliste = spielerliste;
    }

    public int getAnzahlSonderregelKartenZiehen() {
        return anzahlSonderregelKartenZiehen;
    }

    public void setAnzahlSonderregelKartenZiehen(int anzahlSonderregelKartenZiehen) {
        this.anzahlSonderregelKartenZiehen = anzahlSonderregelKartenZiehen;
    }

    public int getRunde() {
        return runde;
    }

    public void setRunde(int runde) {
        this.runde = runde;
    }

    public List<Karte> getAblagestapel() {
        return ablagestapel;
    }

    public void setAblagestapel(List<Karte> ablagestapel) {
        this.ablagestapel = ablagestapel;
    }

    public List<Karte> getKartenstapel() {
        return kartenstapel;
    }

    public void setKartenstapel(List<Karte> kartenstapel) {
        this.kartenstapel = kartenstapel;
    }

    public Kartentyp getAktuellerWunschtyp() {
        return aktuellerWunschtyp;
    }

    public void setAktuellerWunschtyp(Kartentyp aktuellerWunschtyp) {
        this.aktuellerWunschtyp = aktuellerWunschtyp;
    }

    public List<Spieler> getSpielerListe() {
        return spielerliste;
    }

    public void setSpielerListe(List<Spieler> spielerliste) {
        this.spielerliste = spielerliste;
    }

    public boolean isSonderregelSiebenAktiv() {
        return sonderregelSiebenAktiv;
    }

    public void setSonderregelSiebenAktiv(boolean sonderregelSiebenAktiv) {
        this.sonderregelSiebenAktiv = sonderregelSiebenAktiv;
    }

    public boolean isSonderregelAssAktiv() {
        return sonderregelAssAktiv;
    }

    public void setSonderregelAssAktiv(boolean sonderregelAssAktiv) {
        this.sonderregelAssAktiv = sonderregelAssAktiv;
    }
}


