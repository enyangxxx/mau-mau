package de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

import javax.persistence.*;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */

@Embeddable
public class Karte{

	private Kartentyp typ;
	private Kartenwert wert;
	//private MauMauSpiel maumauspiel;

	public Karte(){

	}

	public Karte(Kartentyp typ, Kartenwert wert) {
		this.typ = typ;
		this.wert = wert;
	}

	/*
	@ManyToOne
	public MauMauSpiel getMaumauspiel() {
		return maumauspiel;
	}

	public void setMaumauspiel(MauMauSpiel maumauspiel) {
		this.maumauspiel = maumauspiel;
	}
	*/

	@Column(nullable=false)
	public Kartentyp getTyp() {
		return typ;
	}

	public void setTyp(Kartentyp typ) {
		this.typ = typ;
	}

	@Column(nullable=false)
	public Kartenwert getWert() {
		return wert;
	}

	public void setWert(Kartenwert wert) {
		this.wert = wert;
	}

}
