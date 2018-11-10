package de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class Karte{
	
	private Kartentyp typ;
	private Kartenwert wert;

	public Karte(){

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
