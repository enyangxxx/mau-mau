/**
 * 
 */
package de.htw.berlin.maumau;

import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 *
 */
public class MauMauSpiel implements IMauMau{
	
	private Regelwerk regelwerk;
	private Spieler spieler;
	private Spieler computer;
	private int runde;
	private Karte letzteKarte;
	
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
	/* (non-Javadoc)
	 * @see de.htw.berlin.maumau.IMauMau#karteZiehen(de.htw.berlin.maumau.Spieler, java.util.List)
	 */
	public void karteZiehen(Spieler spieler, List<Karte> kartenstapel) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see de.htw.berlin.maumau.IMauMau#kartenMischen(java.util.List)
	 */
	public void kartenMischen(List<Karte> kartenstapel) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see de.htw.berlin.maumau.IMauMau#karteLegen(de.htw.berlin.maumau.Spieler)
	 */
	public void karteLegen(Spieler spieler) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see de.htw.berlin.maumau.IMauMau#maumauRufen(de.htw.berlin.maumau.Spieler)
	 */
	public void maumauRufen(Spieler spieler) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see de.htw.berlin.maumau.IMauMau#minuspunkteBerechnen(de.htw.berlin.maumau.Spieler)
	 */
	public int minuspunkteBerechnen(Spieler spieler) {
		// TODO Auto-generated method stub
		return 0;
	}
	/* (non-Javadoc)
	 * @see de.htw.berlin.maumau.IMauMau#kartenAusteilen(de.htw.berlin.maumau.Spieler, de.htw.berlin.maumau.Spieler, java.util.List)
	 */
	public void kartenAusteilen(Spieler spieler, Spieler computer, List<Karte> kartenstapel) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see de.htw.berlin.maumau.IMauMau#regelnPruefen(de.htw.berlin.maumau.Spieler, de.htw.berlin.maumau.Karte)
	 */
	public boolean regelnPruefen(Spieler spieler, Karte letzteKarte) {
		// TODO Auto-generated method stub
		return false;
	}
	/* (non-Javadoc)
	 * @see de.htw.berlin.maumau.IMauMau#neuenSpielzugStarten(de.htw.berlin.maumau.Spieler, de.htw.berlin.maumau.Spieler)
	 */
	public void neuenSpielzugStarten(Spieler spieler, Spieler computer) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see de.htw.berlin.maumau.IMauMau#regelnPruefen(de.htw.berlin.maumau.Spieler, de.htw.berlin.maumau.Karte, de.htw.berlin.maumau.Regelwerk)
	 */
	public boolean regelnPruefen(Spieler spieler, Karte letzteKarte, Regelwerk regelwerk) {
		return false;
	}

}
