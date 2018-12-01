package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungController;

import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungView.SpielerverwaltungView;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;

public class SpielerverwaltungController {
    private Spieler spieler;
    private SpielerverwaltungView view;

    public SpielerverwaltungController(Spieler spieler, SpielerverwaltungView view) {
        this.spieler = spieler;
        this.view = view;
    }

    public String getSpielerName(){
        return spieler.getName();
    }

    public void setSpielerName(String name){
        spieler.setName(name);
    }

    public void updateView(){
        view.printSpielerGewechselt();
        view.printSpielerGeneriert(spieler.getName());
    }
}
