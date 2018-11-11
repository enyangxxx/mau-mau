package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface;

import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class Spieler {

    private String name;
    private int s_id;
    private List<Karte> hand = new ArrayList<Karte>();
    private boolean hatMauGerufen;
    private boolean istDran;
    private boolean istComputer;
    private Karte neueKarte;// Die nächste Karte, die gelegt werden soll
    private int punktestand;

    public Spieler(String name, int s_id, boolean istComputer) {
        this.name = name;
        this.s_id = s_id;
        this.istComputer = istComputer;
    }

    public Karte getNaechsteKarte() {
        return neueKarte;
    }

    public void setNaechsteKarte(Karte naechsteKarte) {
        this.neueKarte = naechsteKarte;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public List<Karte> getHand() {
        return hand;
    }

    public void setHand(List<Karte> hand) {
        this.hand = hand;
    }

    public boolean istDran() {
        return istDran;
    }

    public void setIstDran(boolean istDran) {
        this.istDran = istDran;
    }

    public boolean hatMauGerufen() {
        return hatMauGerufen;
    }

    public void setHatMauGerufen(boolean hatMauGerufen) {
        this.hatMauGerufen = hatMauGerufen;
    }

    public int getPunktestand() {
        return punktestand;
    }

    public void setPunktestand(int punktestand) {
        this.punktestand = punktestand;
    }

    public boolean isIstComputer() {
        return istComputer;
    }

    public void setIstComputer(boolean istComputer) {
        this.istComputer = istComputer;
    }

}
