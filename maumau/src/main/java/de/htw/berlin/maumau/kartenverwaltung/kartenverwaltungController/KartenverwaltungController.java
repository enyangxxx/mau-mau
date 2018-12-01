package de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungController;

import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungView.KartenverwaltungView;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;

public class KartenverwaltungController {
    private Karte karte;
    private KartenverwaltungView view;

    public KartenverwaltungController(Karte karte, KartenverwaltungView view){
        this.karte = karte;
        this.view = view;
    }

    public void updateView(){
        view.printKartenAusgeteilt();
    }

}
