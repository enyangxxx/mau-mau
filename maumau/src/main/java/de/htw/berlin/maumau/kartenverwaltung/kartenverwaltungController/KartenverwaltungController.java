package de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungController;

import de.htw.berlin.maumau.configurator.ConfigServiceImpl;
import de.htw.berlin.maumau.errorHandling.KeineKarteException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungView.KartenverwaltungView;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.IKartenverwaltung;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.ISpielerverwaltung;

import java.util.List;

public class KartenverwaltungController {
    private Karte karte;
    private KartenverwaltungView view;
    private IKartenverwaltung kartenverwaltung = (IKartenverwaltung) ConfigServiceImpl.context.getBean("kartenverwaltungimpl");
    private List<Karte> kartenstapel;

    public KartenverwaltungController(Karte karte, KartenverwaltungView view){
        this.karte = karte;
        this.view = view;
    }

    public void updateView(){
        //view.printKartenAusgeteilt();
    }

    public void kartenVorbereiten() throws KeineKarteException {
        kartenstapel = kartenverwaltung.kartenstapelGenerieren();
        kartenverwaltung.kartenMischen(kartenstapel);
    }

}
