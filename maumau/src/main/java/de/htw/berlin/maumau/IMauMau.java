/**
 * 
 */
package de.htw.berlin.maumau;

import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 *
 */
public interface IMauMau {
	
	/**
	 * Ein {@link Spieler} zieht eine Karte von dem Kartenstapel und fügt diese seiner Hand hinzu
	 * @param spieler
	 * @param kartenstapel
	 */
	public void karteZiehen(Spieler spieler, List<Karte> kartenstapel);
	/**
	 * Mischt den Kartenstapel, sodass die Reihenfolge zufällig ist.
	 * @param kartenstapel
	 */
	public void kartenMischen(List<Karte> kartenstapel);
	/**
	 * Ein {@link Spieler} legt eine Karte.
	 * @param spieler
	 */
	public void karteLegen(Spieler spieler);
	/**
	 * Ein {@link Spieler} ruft Mau Mau.
	 * @param spieler
	 */
	public void maumauRufen(Spieler spieler);
	
	/**
	 * Der Wert der Hand des {@link Spieler} der verloren hat wird berechnet.
	 * @param spieler
	 * @return Minuswert der Hand
	 */
	public int minuspunkteBerechnen(Spieler spieler);
	
	/**
	 * Beide {@link Spieler} bekommen je 5 {@link Karte} aus dem Kartenstapel
	 * @param spieler
	 * @param computer
	 * @param kartenstapel
	 */
	public void kartenAusteilen(Spieler spieler, Spieler computer, List<Karte> kartenstapel);
	
	/**
	 * Ein {@link Spieler} kann nur dann eine {@link Karte} legen, wenn sie dem {@link Regelwerk} entspricht.
	 * @param spieler
	 * @param letzteKarte
	 * @param regelwerk
	 * @return true, wenn regelkonform.
	 */
	public boolean regelnPruefen(Spieler spieler, Karte letzteKarte, Regelwerk regelwerk);
	
	public void neuenSpielzugStarten(Spieler spieler, Spieler computer);
	
	

}
