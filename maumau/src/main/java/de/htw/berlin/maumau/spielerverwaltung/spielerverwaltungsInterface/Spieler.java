package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface;

import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */

@Entity
public class Spieler {

    private String name;
    private int s_id;
    private List<Karte> hand = new ArrayList<Karte>();
    private boolean hatMauGerufen;
    private boolean dran;
    private boolean istComputer;
    private Karte neueKarte;// Die nächste Karte, die gelegt werden soll
    private int punktestand;

    public Spieler(){

    }


    public Spieler(String name, int s_id, boolean istComputer) {
        this.name = name;
        this.s_id = s_id;
        this.istComputer = istComputer;
        dran = false;
    }

    @OneToOne
    public Karte getNaechsteKarte() {
        return neueKarte;
    }

    public void setNaechsteKarte(Karte naechsteKarte) {
        this.neueKarte = naechsteKarte;
    }

    @Column(nullable=false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    @OneToMany
    public List<Karte> getHand() {
        return hand;
    }

    public void setHand(List<Karte> hand) {
        this.hand = hand;
    }

    @Column(nullable=false)
    public boolean isDran() {
        return dran;
    }

    @Transient
    public void setDran(boolean dran) {
        this.dran = dran;
    }

    public boolean hatMauGerufen() {
        return hatMauGerufen;
    }

    public void setHatMauGerufen(boolean hatMauGerufen) {
        this.hatMauGerufen = hatMauGerufen;
    }

    @Column(nullable=true)
    public int getPunktestand() {
        return punktestand;
    }

    public void setPunktestand(int punktestand) {
        this.punktestand = punktestand;
    }

    //public boolean isIstComputer() {
        //return istComputer;
    //}

    //public void setIstComputer(boolean istComputer) {
        //this.istComputer = istComputer;
    //}

}
