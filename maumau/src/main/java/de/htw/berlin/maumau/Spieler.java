/**
 * 
 */
package de.htw.berlin.maumau;

import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 *
 */
public class Spieler {
	
	private String name;
	private int s_id;
	private List<Karte> hand;
	private boolean hatMauGerufen;
	private boolean istDran;
	private Karte naechsteKarte;// Die nächste Karte, die gelegt werden soll
	
	public Karte getNaechsteKarte() {
		return naechsteKarte;
	}

	public void setNaechsteKarte(Karte naechsteKarte) {
		this.naechsteKarte = naechsteKarte;
	}

	public Spieler(String name, int s_id) {
		this.name = name;
		this.s_id = s_id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}
	public List<Karte> getHand() {
		return hand;
	}
	public void setHand(List<Karte> hand) {
		this.hand = hand;
	}

	public boolean istDran() {
		return istDran;
	}

	public void setIstDran(boolean istDran) {
		this.istDran = istDran;
	}

	public boolean hatMauGerufen() {
		return hatMauGerufen;
	}

	public void setHatMauGerufen(boolean hatMauGerufen) {
		this.hatMauGerufen = hatMauGerufen;
	}

}
