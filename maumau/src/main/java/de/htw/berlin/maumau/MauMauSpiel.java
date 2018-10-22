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
	
	private List<Spieler> spielerliste;
	private int runde;
	private List<Karte> ablagestapel;
	private List<Karte> kartenstapel;
	private Kartentyp wunschtyp;
	private int anzahlSonderregelKartenZiehen;
	
	public MauMauSpiel(List<Spieler> spielerliste){
		this.runde = 1;
		this.spielerliste = spielerliste;
	}
	
	public int getRunde() {
		return runde;
	}
	public void setRunde(int runde) {
		this.runde = runde;
	}
	public List<Karte> getAblagestapel() {
		return ablagestapel;
	}
	public void setAblagestapel(List<Karte> ablagestapel) {
		this.ablagestapel = ablagestapel;
	}

	public List<Karte> getKartenstapel() {
		return kartenstapel;
	}

	public void setKartenstapel(List<Karte> kartenstapel) {
		this.kartenstapel = kartenstapel;
	}

	public Kartentyp getWunschtyp() {
		return wunschtyp;
	}

	public void setWunschtyp(Kartentyp wunschtyp) {
		this.wunschtyp = wunschtyp;
	}
	
	public List<Spieler> getSpielerListe() {
		return spielerliste;
	}
	
	public void setSpielerListe(List<Spieler> spielerliste) {
		this.spielerliste = spielerliste;
	}
	

}
