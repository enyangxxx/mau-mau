package spielManager;

public class Spiel{
	
	
	static Spielbeginn beginn = new Spielbeginn();
	static Spielzug spielzug = new Spielzug();
	
	
	public static void main(String[] args) {
		//Testvariablen
		String[] karten = {"Bube", "Dame","König","Ass"};
		String karte1 = "Bube";
		String karte2 = "Dame";
		
		
		beginn.kartenMischen(karten);
		beginn.kartenAusteilen();
		spielzug.checkRegelen(karte1, karte2);
		
		
		
	}



}
