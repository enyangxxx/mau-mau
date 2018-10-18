/**
 * 
 */
package de.htw.berlin.maumau;

import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 *
 */
public class Regelwerk {
	
	private Karte letzteKarte;
	private Spieler spieler;
	private List<Karte> kartenstapel;
	
	
	
	public Karte getLetzteKarte() {
		return letzteKarte;
	}
	public void setLetzteKarte(Karte letzteKarte) {
		this.letzteKarte = letzteKarte;
	}
	public Spieler getSpieler() {
		return spieler;
	}
	public void setSpieler(Spieler spieler) {
		this.spieler = spieler;
	}
	public List<Karte> getKartenstapel() {
		return kartenstapel;
	}
	public void setKartenstapel(List<Karte> kartenstapel) {
		this.kartenstapel = kartenstapel;
	}
	
	/**
	public void regel1(Karte letzteKarte) {
		
	}
	public void regel2(Karte letzteKarte) {
		
	}
	public void regel3(Karte letzteKarte) {
	
	}
	*/
	

}
