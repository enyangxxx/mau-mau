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
	 * Wenn der Kartenstapel leer ist, wird er neu erstellt.
	 * @param spieler
	 * @param kartenstapel
	 */
	public void karteZiehen(Spieler spieler, List<Karte> kartenstapel);
	
	/**
	 * Ein {@link Spieler} zieht eine bestimmte Anzahl von {@link Karte} vom Kartenstapel.
	 * Wenn der Kartenstapel leer ist, wird er neu erstellt.
	 * @param spieler
	 * @param kartenstapel
	 * @param anzahl
	 */
	public void karteZiehen(Spieler spieler, List<Karte> kartenstapel, int anzahl);
	
	/**
	 * Mischt den Kartenstapel, sodass die Reihenfolge der Karten zufällig ist.
	 * @param kartenstapel
	 */
	public void kartenMischen(List<Karte> kartenstapel);
	
	/**
	 * Generiert einen Kartenstapel mit 32 Karten, wobei jede Karte einmal vorkommt.
	 * @return generierter Kartenstapel
	 */
	public List<Karte> kartenstapelGenerieren();
	
	/**
	 * Ein {@link Spieler} legt eine {@link Karte}. Falls es sich um einen Buben handelt, darf er einen Wunschtyp festlegen.
	 * @param spieler
	 * @param ablagestapel
	 */
	public void karteLegen(Spieler spieler, List<Karte> ablagestapel);

	/**
	 * Mischt die Karten des Ablagestapels in den Kartenstapel.
	 * @param ablagestapel
	 * @param kartenstapel
	 */
	public void ablagestapelWiederverwenden(List<Karte> ablagestapel, List<Karte> kartenstapel);
	
	/**
	 * Ein {@link Spieler} beendet seinen Spielzug und der Spielzug des nächsten {@link Spieler} wird eingeleitet.
	 * @param aktuellerSpieler
	 * @param neuerSpieler
	 */
	public void spielerWechseln(Spieler aktuellerSpieler, Spieler neuerSpieler);
	
	/**
	 * Ermittelt die letzte {@link Karte} auf dem Ablagestapel und gibt diese zurück.
	 * @param ablagestapel
	 * @return die letzte Karte
	 */
	public Karte letzteKarteErmitteln(List<Karte> ablagestapel);
	
	/**
	 * Fügt einen {@link Spieler} zur Spielerliste hinzu.
	 * @param spieler
	 * @param spielerliste
	 */
	public void addSpielerZurListe(Spieler spieler, List<Spieler> spielerliste);
	
	/**
	 * Gibt den {@link Spieler} aus der Spielerliste zurück, zu dem die ID gehört.
	 * @param id
	 * @param spielerliste
	 * @return spieler
	 */
	public Spieler getSpielerById(int id, List<Spieler> spielerliste);
	
	/**
	 * Ein {@link Spieler} ruft Mau Mau und die Variable hatMauGerufen wird auf true gesetzt.
	 * @param spieler
	 */
	public void maumauRufen(Spieler spieler);
	
	/**
	 * Wenn der {@link Spieler} nur noch eine {@link Karte} auf der Hand hat, wird geprüft, ob er Mau gesagt hat.
	 * Wenn ja, muss er keinen Strafzug machen und die Variable hatMauGerufen wird wieder auf false gesetzt.
	 * Wenn nein, muss er zwei Karten ziehen.
	 * @param spieler
	 * @param kartenstapel
	 */
	public void maumauPruefen(Spieler spieler, List<Karte> kartenstapel);
	
	/**
	 * Der Wert der Hand des {@link Spieler}, der verloren hat, wird berechnet.
	 * @param spieler
	 * @return Minuswert der Hand
	 */
	public int minuspunkteBerechnen(Spieler spieler);
	
	/**
	 * Alle {@link Spieler} bekommen je 5 {@link Karte} aus dem Kartenstapel und es wird eine Anfangskarte aufgedeckt.
	 * @param spielerliste
	 * @param kartenstapel
	 * @param ablagestapel
	 */
	public void kartenAusteilen(List<Spieler> spielerliste, List<Karte> kartenstapel, List<Karte> ablagestapel);
	
	/**
	 * Anhand der Farbe und des Werts der letzten {@link Karte} wird geprüft, ob die neue {@link Karte} gelegt werden kann.
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
	 * Setzt die Sonderregel Aussetzen um und falls der {@link Spieler} nicht mit einem Ass kontern kann,
	 * wird der Zug für den {@link Spieler} beendet und der Zug des nächsten {@link Spieler} eingeleitet.
	 * @param aktuellerSpieler
	 * @param neuerSpieler
	 */
	public void sonderregelAussetzen(Spieler aktuellerSpieler, Spieler neuerSpieler);
	
	/**
	 * Setzt die Sonderregel Karten ziehen um. Entweder werden Karten gezogen, oder es kann gekontert werden.
	 * @param anzahl
	 * @param hand
	 * @param kartenstapel
	 */
	public void sonderregelKartenZiehen(int anzahl, List<Karte> hand, List<Karte> kartenstapel);
	
	/**
	 * Legt den {@link Kartentyp} für die Sonderregel Typ wünschen fest.
	 * @param wunschtyp - der gewünschte Kartentyp.
	 */
	public void sonderregelWunschtypSetzen(Kartentyp wunschtyp);
	
	/**
	 * Der Punktestand der {@link Spieler} wird zurückgesetzt und ein neues Spiel wird eingeleitet.
	 * @param spielerliste
	 */
	public void neuesSpielStarten(List<Spieler> spielerliste);
	
	/**
	 * Eine neue Runde wird eingeleitet.
	 * @param spiel
	 */
	public void neueRundeStarten(MauMauSpiel spiel);

}
