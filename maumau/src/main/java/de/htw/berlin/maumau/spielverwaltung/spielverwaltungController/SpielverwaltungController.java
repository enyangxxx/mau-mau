package de.htw.berlin.maumau.spielverwaltung.spielverwaltungController;

import de.htw.berlin.maumau.configurator.ConfigServiceImpl;
import de.htw.berlin.maumau.errorHandling.KeineSpielerException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungView.SpielverwaltungView;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.ISpielverwaltung;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

public class SpielverwaltungController {

    private SpielverwaltungView view;
    private ISpielverwaltung spielverwaltung = (ISpielverwaltung) ConfigServiceImpl.context.getBean("spielverwaltungimpl");

    private MauMauSpiel spiel;
    private Spieler spieler;
    private Karte karte;

    public SpielverwaltungController(MauMauSpiel spiel, Spieler spieler, Karte karte, SpielverwaltungView view) {
        this.spiel = spiel;
        this.spieler = spieler;
        this.karte = karte;
        this.view = view;
    }


    public void updateViewNeuesSpielGestartet(){
        //this.spiel = spielverwaltung.neuesSpielStarten();
        view.printNeuesSpielGestartet();
    }

}
