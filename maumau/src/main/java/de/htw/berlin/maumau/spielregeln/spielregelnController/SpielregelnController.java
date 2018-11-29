package de.htw.berlin.maumau.spielregeln.spielregelnController;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielregeln.spielregelnView.SpielregelnView;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

import java.util.List;

public class SpielregelnController {
    private SpielregelnView view;
    private Karte karte;
    private Spieler spieler;
    private MauMauSpiel spiel;

    public SpielregelnController(SpielregelnView view, Karte karte, MauMauSpiel spiel) {
        this.view = view;
        this.karte = karte;
        this.spiel = spiel;
    }

    public Kartenwert getKartenwert(){
        return karte.getWert();
    }

    public void setKartenwert(Kartenwert wert){
        karte.setWert(wert);
    }

    public Kartentyp getKartentyp(){
        return karte.getTyp();
    }

    public void setKartentyp(Kartentyp typ){
        karte.setTyp(typ);
    }

    public List<Karte> getHand(){
        return spieler.getHand();
    }

    public void setHand(List<Karte> hand){
        spieler.setHand(hand);
    }

    public int getAnzahl(){
        return spiel.getAnzahlSonderregelKartenZiehen();
    }

    public void setAnzahl(int anzahl){
        spiel.setAnzahlSonderregelKartenZiehen(anzahl);
    }

    public void updateView(){
        view.printKarteGelegt(karte.getTyp(), karte.getWert());
        view.printKarteNichtLegbar(karte.getTyp(), karte.getWert());
        view.printHatKartenGezogen(spiel.getAnzahlSonderregelKartenZiehen(), spieler.getHand());
    }
}
