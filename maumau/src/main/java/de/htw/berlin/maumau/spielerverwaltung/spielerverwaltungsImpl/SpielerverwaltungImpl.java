package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl;

import de.htw.berlin.maumau.errorHandling.IdDuplikatException;
import de.htw.berlin.maumau.errorHandling.KeineSpielerException;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.ISpielerverwaltung;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class SpielerverwaltungImpl implements ISpielerverwaltung {

    private Log log = LogFactory.getLog(SpielerverwaltungImpl.class);


    public SpielerverwaltungImpl(){
        log.info("SpielerverwaltungsImpl Konstruktor called");
    }

    public Spieler spielerGenerieren(String name, int id, boolean istComputer) {
        Spieler spieler = new Spieler(name, id, istComputer);
        return spieler;
    }

    public void addSpielerZurListe(Spieler spieler, List<Spieler> spielerliste) throws IdDuplikatException {
        for(Spieler spielerx : spielerliste) {
            if(spielerx.getS_id() == spieler.getS_id()) {
                throw new IdDuplikatException("Die ID ist bereits vergeben");
            }
        }
            spielerliste.add(spieler);
    }

    public void spielerWechseln(Spieler aktuellerSpieler, Spieler neuerSpieler) {
        aktuellerSpieler.setIstDran(false);
        neuerSpieler.setIstDran(true);
    }

    public Spieler getSpielerById(int id, List<Spieler> spielerliste) throws KeineSpielerException {
        Spieler gefundenerSpieler = null;
            for(Spieler spieler : spielerliste) {
                if(spieler.getS_id() == id) {
                    gefundenerSpieler = spieler;
                }
            }
            if(gefundenerSpieler == null){
                throw new KeineSpielerException("Keine Spieler Exception");
            }

    return gefundenerSpieler;

    }
}
