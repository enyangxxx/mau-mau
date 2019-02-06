package de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */

@Embeddable
public class Karte {

    @Column(name = "typ")
    private Kartentyp typ;

    @Column(name = "wert")
    private Kartenwert wert;

    public Karte() {

    }

    public Karte(Kartentyp typ, Kartenwert wert) {
        this.typ = typ;
        this.wert = wert;
    }

    public Kartentyp getTyp() {
        return typ;
    }

    public void setTyp(Kartentyp typ) {
        this.typ = typ;
    }

    public Kartenwert getWert() {
        return wert;
    }

    public void setWert(Kartenwert wert) {
        this.wert = wert;
    }

}
