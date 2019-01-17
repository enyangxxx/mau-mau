package de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import org.hibernate.annotations.Target;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */

@Entity
public class MauMauSpiel {

    @ElementCollection
    private List<Spieler> spielerliste;
    private int runde;
    @ElementCollection
    @Embedded
    private List<Karte> ablagestapel = new ArrayList<Karte>();
    @ElementCollection
    @Embedded
    private List<Karte> kartenstapel = new ArrayList<Karte>();
    private Kartentyp aktuellerWunschtyp;
    private boolean sonderregelSiebenAktiv;
    private boolean sonderregelAssAktiv;
    private int anzahlSonderregelKartenZiehen;
    private int spielId;

    public MauMauSpiel(){

    }

    public MauMauSpiel(List<Spieler> spielerliste) {
        this.runde = 1;
        this.anzahlSonderregelKartenZiehen = 0;
        this.spielerliste = spielerliste;
        this.sonderregelSiebenAktiv = false;
        this.sonderregelAssAktiv = false;
        this.spielId = 0;
    }


    @Id
    public int getSpielId() {
        return spielId;
    }

    public void setSpielId(int spielId) {
        this.spielId = spielId;
    }

    @ElementCollection
    public List<Spieler> getSpielerListe() {
        return spielerliste;
    }

    public void setSpielerListe(List<Spieler> spielerliste) {
        this.spielerliste = spielerliste;
    }

    @Column(nullable=false)
    public int getAnzahlSonderregelKartenZiehen() {
        return anzahlSonderregelKartenZiehen;
    }

    public void setAnzahlSonderregelKartenZiehen(int anzahlSonderregelKartenZiehen) {
        this.anzahlSonderregelKartenZiehen = anzahlSonderregelKartenZiehen;
    }

    @Column(nullable=false)
    public int getRunde() {
        return runde;
    }

    public void setRunde(int runde) {
        this.runde = runde;
    }

    @ElementCollection
    @Embedded
    public List<Karte> getAblagestapel() {
        return ablagestapel;
    }

    public void setAblagestapel(List<Karte> ablagestapel) {
        this.ablagestapel = ablagestapel;
    }

    @ElementCollection
    @Embedded
    public List<Karte> getKartenstapel() {
        return kartenstapel;
    }

    public void setKartenstapel(List<Karte> kartenstapel) {
        this.kartenstapel = kartenstapel;
    }

    @Column(nullable=true)
    public Kartentyp getAktuellerWunschtyp() {
        return aktuellerWunschtyp;
    }

    public void setAktuellerWunschtyp(Kartentyp aktuellerWunschtyp) {
        this.aktuellerWunschtyp = aktuellerWunschtyp;
    }


    @Column(nullable=false)
    public boolean isSonderregelSiebenAktiv() {
        return sonderregelSiebenAktiv;
    }

    public void setSonderregelSiebenAktiv(boolean sonderregelSiebenAktiv) {
        this.sonderregelSiebenAktiv = sonderregelSiebenAktiv;
    }

    @Column(nullable=false)
    public boolean isSonderregelAssAktiv() {
        return sonderregelAssAktiv;
    }

    public void setSonderregelAssAktiv(boolean sonderregelAssAktiv) {
        this.sonderregelAssAktiv = sonderregelAssAktiv;
    }
}


