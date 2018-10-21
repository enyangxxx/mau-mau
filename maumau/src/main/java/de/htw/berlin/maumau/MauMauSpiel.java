/**
 * 
 */
package de.htw.berlin.maumau;

import java.util.List;

import de.htw.berlin.maumau.enumeration.Kartentyp;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 *
 */
public class MauMauSpiel{
	
	private Spieler spieler;
	private Spieler computer;
	private int runde;
	private Karte letzteKarte;
	private List<Karte> kartenstapel;
	private Kartentyp wunschtyp;
	
	public Spieler getSpieler() {
		return spieler;
	}
	public void setSpieler(Spieler spieler) {
		this.spieler = spieler;
	}
	public Spieler getComputer() {
		return computer;
	}
	public void setComputer(Spieler computer) {
		this.computer = computer;
	}
	public int getRunde() {
		return runde;
	}
	public void setRunde(int runde) {
		this.runde = runde;
	}
	public Karte getLetzteKarte() {
		return letzteKarte;
	}
	public void setLetzteKarte(Karte letzteKarte) {
		this.letzteKarte = letzteKarte;
	}



}
