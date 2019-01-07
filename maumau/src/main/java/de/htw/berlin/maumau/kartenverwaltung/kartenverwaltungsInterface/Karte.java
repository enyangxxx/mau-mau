package de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;

import javax.persistence.*;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */

@Entity
public class Karte{

	private Kartentyp typ;
	private Kartenwert wert;
	private int karten_id;

	public Karte(){

	}

	public Karte(Kartentyp typ, Kartenwert wert) {
		this.typ = typ;
		this.wert = wert;
	}


	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	public int getKarten_id() {
		return karten_id;
	}

	public void setKarten_id(int karten_id) {
		this.karten_id = karten_id;
	}

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
