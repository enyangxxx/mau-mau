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
	 * Ein {@link Spieler} zieht eine Karte von dem Kartenstapel und fügt diese seiner Hand hinzu.
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
	 * Generiert einen Kartenstapel mit 32 Karten, wobei jede Karte einmal vorkommt.
	 * @return generierter Kartenstapel
	 */
	public List<Karte> kartenstapelGenerieren();
	
	/**
	 * Ein {@link Spieler} legt eine Karte. Falls es sich um einen Buben handelt, darf er einen Wunschtyp festlegen.
	 * @param spieler
	 */
	public void karteLegen(Spieler spieler);

	/**
	 * Ein {@link Spieler} beendet seinen Spielzug und der Spielzug des nächsten {@link Spieler} wird eingeleitet.
	 * @param spieler
	 * @param computer
	 */
	public void spielerWechseln(Spieler spieler, Spieler computer);
	
	/**
	 * Ein {@link Spieler} ruft Mau Mau und die Variable hatMauGerufen wird auf true gesetzt.
	 * @param spieler
	 */
	public void maumauRufen(Spieler spieler);
	
	/**
	 * Wenn der {@link Spieler} nur noch eine Karte auf der Hand hat, wird geprüft, ob er Mau gesagt hat.
	 * Wenn ja, muss er keinen Strafzug machen und die Variable hatMauGerufen wird wieder auf false gesetzt.
	 * Wenn nein, muss er zwei Karten ziehen.
	 * @param spieler
	 */
	public void maumauPruefen(Spieler spieler);
	
	/**
	 * Der Wert der Hand des {@link Spieler}, der verloren hat, wird berechnet.
	 * @param spieler
	 * @return Minuswert der Hand
	 */
	public int minuspunkteBerechnen(Spieler spieler);
	
	/**
	 * Beide {@link Spieler} bekommen je 5 {@link Karte} aus dem Kartenstapel und es wird eine Anfangskarte aufgedeckt.
	 * @param spieler
	 * @param computer
	 * @param kartenstapel
	 */
	public void kartenAusteilen(Spieler spieler, Spieler computer, List<Karte> kartenstapel);
	
	/**
	 * Anhand der Farbe und des Werts letzten {@link Karte} wird geprüft, ob die neue Karte gelegt werden kann.
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
	 * Anhand des {@link Kartentyp} Wunschtyps wird geprüft, ob die neue {@link Karte} gelegt werden kann.
	 * @param neueKarte
	 * @param wunschtyp
	 * @return true, wenn legbar.
	 */
	public boolean istLegbar(Karte neueKarte, Kartentyp wunschtyp);
	
	/**
	 * Setzt die Sonderregel Aussetzen um und falls er nicht mit einem Ass kontern kann,
	 * wird der Zug für den {@link Spieler} beendet und der Zug des nächsten {@link Spieler} eingeleitet.
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
	 * Legt den {@link Kartentyp} für die Sonderregel Typ wünschen fest.
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
	
	/**
	 * Der Punktestand der {@link Spieler} wird zurückgesetzt und ein neues Spiel wird eingeleitet.
	 * @param spieler
	 * @param computer
	 */
	public void neuesSpielStarten(Spieler spieler, Spieler computer);
	
	/**
	 * Eine neue Runde wird eingeleitet.
	 * @param spieler
	 * @param computer
	 */
	public void neueRundeStarten(Spieler spieler, Spieler computer);

}
