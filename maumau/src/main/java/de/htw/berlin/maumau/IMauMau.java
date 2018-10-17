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
	
	public void kartenMischen(List<Karte> kartenstapel);
	
	public void karteLegen(Spieler spieler);
	
	public void maumauRufen(Spieler spieler);
	
	public int minuspunkteBerechnen(Spieler spieler);
	
	public void kartenAusteilen(Spieler spieler, Spieler computer, List<Karte> kartenstapel);
	
	public boolean regelnPruefen(Spieler spieler, Karte letzteKarte);
	
	public void neuenSpielzugStarten(Spieler spieler, Spieler computer);
	
	

}
