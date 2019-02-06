package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface;


import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;

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
    @ElementCollection
    @Embedded
    private List<Karte> hand = new ArrayList<Karte>();
    private boolean mauGerufen;
    private boolean dran;
    private Karte neueKarte;// Die nächste Karte, die gelegt werden soll
    private int punktestand;
    private boolean istComputer;

    public Spieler() {

    }


    public Spieler(String name, int s_id, boolean istComputer) {
        this.name = name;
        this.s_id = s_id;
        dran = false;
        mauGerufen = false;
        this.istComputer = istComputer;
    }


    @Column(nullable = false)
    public Karte getNeueKarte() {
        return neueKarte;
    }

    public void setNeueKarte(Karte neueKarte) {
        this.neueKarte = neueKarte;
    }

    @Column(nullable = false)
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

    @ElementCollection
    @Embedded
    public List<Karte> getHand() {
        return hand;
    }

    public void setHand(List<Karte> hand) {
        this.hand = hand;
    }

    @Column(nullable = false)
    public boolean isDran() {
        return dran;
    }

    public void setDran(boolean dran) {
        this.dran = dran;
    }

    @Column(name = "mauGerufen")
    public boolean isMauGerufen() {
        return mauGerufen;
    }

    public void setMauGerufen(boolean mauGerufen) {
        this.mauGerufen = mauGerufen;
    }

    @Column(nullable = true)
    public int getPunktestand() {
        return punktestand;
    }

    public void setPunktestand(int punktestand) {
        this.punktestand = punktestand;
    }

    @Column(nullable = true)
    public boolean isIstComputer() {
        return istComputer;
    }

    public void setIstComputer(boolean istComputer) {
        this.istComputer = istComputer;
    }
}
