package de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

import javax.persistence.*;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */

@Entity
@Table(name="KARTE")
public class Karte{

	private Kartentyp typ;
	private Kartenwert wert;
	private int karten_id;
	private MauMauSpiel maumauspiel;

	public Karte(){

	}

	public Karte(Kartentyp typ, Kartenwert wert) {
		this.typ = typ;
		this.wert = wert;
	}

	@ManyToOne
	public MauMauSpiel getMaumauspiel() {
		return maumauspiel;
	}

	public void setMaumauspiel(MauMauSpiel maumauspiel) {
		this.maumauspiel = maumauspiel;
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
