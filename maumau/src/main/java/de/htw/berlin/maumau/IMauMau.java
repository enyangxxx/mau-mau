/**
 * 
 */
package de.htw.berlin.maumau;

import java.util.List;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.SonderregelTyp;

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
	 * Anhand der letzten {@link Karte} wird geprüft, ob die neue Karte gelegt werden kann.
	 * @param letzteKarte
	 * @param neueKarte
	 * @return true, wenn legbar.
	 */
	public boolean istLegbar(Karte letzteKarte, Karte neueKarte);
	
	/**
	 * Es wird überprüft, ob die letzte {@link Karte} eine Sonderregel einleitet.
	 * @param letzteKarte
	 * @return die entsprechende Sonderregel oder "Keine", falls keine Sonderregel zutrifft.
	 */
	public SonderregelTyp sonderregelErmitteln(Karte letzteKarte);
	
	/**
	 * Anhand des Typs der letzten {@link Karte} wird geprüft, ob die neue Karte gelegt werden kann.
	 * @param neueKarte
	 * @param wunschtyp
	 * @return true, wenn legbar.
	 */
	public boolean istLegbar(Karte neueKarte, Kartentyp wunschtyp);
	
	/**
	 * Setzt die Sonderregel Aussetzen um und beendet den Zug für den Spieler und leitet den Zug für den neuen Spieler ein.
	 * @param spieler
	 * @param computer
	 */
	public void sonderregelAussetzen(Spieler spieler, Spieler computer);
	
	/**
	 * Setzt die Sonderregel Karten ziehen um. Entweder werden Karten gezogen, oder es kann gekontert werden.
	 * @param neueKarte
	 */
	public void sonderregelKartenZiehen();
	
	/**
	 * Legt den Kartentyp für die Sonderregel Typ wünschen fest.
	 * @return den gewünschten Kartentyp.
	 */
	public Kartentyp sonderregelTypWuenschen();
	
	/**
	 * Ein {@link Spieler} Zieht eine bestimmte Anzahl von {@link Karte} vom Kartenstapel.
	 * @param spieler
	 * @param kartenstapel
	 * @param anzahl
	 */
	public void karteZiehen(Spieler spieler, List<Karte> kartenstapel, int anzahl);	

}
