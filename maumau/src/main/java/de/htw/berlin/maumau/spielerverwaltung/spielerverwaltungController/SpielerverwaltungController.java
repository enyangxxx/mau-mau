package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungController;

import ch.aplu.util.Console;
import de.htw.berlin.maumau.configurator.ConfigServiceImpl;
import de.htw.berlin.maumau.errorHandling.IdDuplikatException;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungView.SpielerverwaltungView;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.ISpielerverwaltung;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungController.SpielverwaltungController;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.ISpielverwaltung;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

import java.util.ArrayList;
import java.util.List;

public class SpielerverwaltungController {
    private Spieler spieler;
    private MauMauSpiel spiel;
    private List<Spieler> spielerliste = new ArrayList<Spieler>();
    private SpielerverwaltungView view = new SpielerverwaltungView();
    private ISpielerverwaltung spielerverwaltung = (ISpielerverwaltung) ConfigServiceImpl.context.getBean("spielerverwaltungimpl");


    /*public SpielerverwaltungController(Spieler spieler, SpielerverwaltungView view) {
        this.spieler = spieler;
        this.view = view;
    }
    */

    public String getSpielerName(){
        return spieler.getName();
    }

    public void setSpielerName(String name){
        spieler.setName(name);
    }

    public MauMauSpiel getSpiel() {
        return spiel;
    }

    public void setSpiel(MauMauSpiel spiel) {
        this.spiel = spiel;
    }

    public void updateViewNeuerSpielerErstellen() throws IdDuplikatException {
        Console.init();
        while(spielerliste.size() <= 3){
            String userInput = view.userInputNeuerSpielerErstellen();
            if(userInput.equalsIgnoreCase("j")){
                String name = view.userInputNeuerSpielerName();
                int id = view.userInputNeuerSpielerId();
                this.spieler = spielerverwaltung.spielerGenerieren(name, id, false);
                spielerverwaltung.addSpielerZurListe(spieler, spielerliste);
                Console.println(spieler.getName());
                Console.println("spielerliste size: "+spielerliste.size());
            }
            else{
                if(spielerliste.size()>=2){
                    break;
                }
            }
        }

        spiel = new MauMauSpiel(spielerliste);

        //spielerverwaltung.kartenAusteilen(spielerliste, kartenstapel);
        view.printKartenAusgeteilt();

        spiel.getSpielerListe().get(0).setIstDran(true);

        view.printSpielerGewechselt(spiel.getSpielerListe().get(0).getName());

    }
}
