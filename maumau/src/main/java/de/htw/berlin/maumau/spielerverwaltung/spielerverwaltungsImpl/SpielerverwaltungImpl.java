package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl;

import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.errorHandling.IdDuplikatException;
import de.htw.berlin.maumau.errorHandling.KeineSpielerException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.ISpielerverwaltung;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class SpielerverwaltungImpl implements ISpielerverwaltung {

    private Log log = LogFactory.getLog(SpielerverwaltungImpl.class);

    private static final String SPIELER_HAND_GESETZT = "Die Hand wurde gesetzt für Spieler: ";
    private static final String KARTE_ZUM_ABLAGESTAPEL_HINZUGEFUEGT_MESSAGE = "Karte auf Ablagestapel gelegt!";


    public SpielerverwaltungImpl() {
        log.info("SpielerverwaltungsImpl Konstruktor called");
    }

    public Spieler spielerGenerieren(String name, int id, boolean istComputer) {
        Spieler spieler = new Spieler(name, id, istComputer);
        return spieler;
    }

    public void addSpielerZurListe(Spieler spieler, List<Spieler> spielerliste) throws IdDuplikatException {
        for (Spieler spielerx : spielerliste) {
            if (spielerx.getS_id() == spieler.getS_id()) {
                throw new IdDuplikatException("Die ID ist bereits vergeben");
            }
        }
        spielerliste.add(spieler);
    }


    public void spielerWechseln(MauMauSpiel spiel) {
        List<Spieler> spielerliste = spiel.getSpielerListe();
        for (int i = 0; i < spielerliste.size(); i++) {
            if (spielerliste.get(i).istDran()) {
                spielerliste.get(i).setIstDran(false);
                if (spielerliste.size() - 1 == i) {
                    spielerliste.get(0).setIstDran(true);
                } else {
                    spielerliste.get(i + 1).setIstDran(true);
                }
                break;
            }
        }

        if(spiel.isSonderregelAssAktiv()){
            spiel.setSonderregelAssAktiv(false);
            spielerWechseln(spiel);
        }
        /*if(spiel.getAblagestapel().get(spiel.getAblagestapel().size()-1).getWert().equals(Kartenwert.ASS)&&
                (!kannAssKontern(spiel))){
            //spielerWechseln(spiel);
            for (int i = 0; i < spielerliste.size(); i++) {
                if (spielerliste.get(i).istDran()) {
                    spielerliste.get(i).setIstDran(false);
                    if (spielerliste.size() - 1 == i) {
                        spielerliste.get(0).setIstDran(true);
                    } else {
                        spielerliste.get(i + 1).setIstDran(true);
                    }
                    break;
                }
            }
            spiel.setSonderregelAssAktiv(false);
        }*/
    }


    /*private boolean kannAssKontern(MauMauSpiel spiel){
        boolean tmp = false;
        if(spiel.getAblagestapel().get(spiel.getAblagestapel().size()-1).getWert().equals(Kartenwert.ASS)
        &&spiel.isSonderregelAssAktiv()){
            for(Spieler spieler : spiel.getSpielerListe()){
                if (spieler.istDran()) {
                    for(Karte karte : spieler.getHand()){
                        if(karte.getWert().equals(Kartenwert.ASS)){
                            tmp = true;
                        }
                    }
                }
            }
        }
        return tmp;
    }
    */

    public Spieler getSpielerById(int id, List<Spieler> spielerliste) throws KeineSpielerException {
        Spieler gefundenerSpieler = null;
        for (Spieler spieler : spielerliste) {
            if (spieler.getS_id() == id) {
                gefundenerSpieler = spieler;
            }
        }
        if (gefundenerSpieler == null) {
            throw new KeineSpielerException("Keine Spieler Exception");
        }

        return gefundenerSpieler;

    }


    /**
     * Alle {@link Spieler} bekommen je 5 {@link Karte} aus dem Kartenstapel und es wird eine Anfangskarte aufgedeckt.
     *
     * @param spielerliste - die aktuelle Spielerliste
     * @param kartenstapel - der aktuelle Kartenstapel
     * @param ablagestapel - der aktuelle Ablagestapel
     */
    public void kartenAusteilen(List<Spieler> spielerliste, List<Karte> kartenstapel, List<Karte> ablagestapel) {
        for (Spieler spieler : spielerliste) {
            List<Karte> hand = new ArrayList<Karte>();
            for (int i = 0; i < 10; i++) {
                hand.add(kartenstapel.get(i));
                kartenstapel.remove(kartenstapel.get(i));
            }
            log.info(SPIELER_HAND_GESETZT + spieler.getName());
            spieler.setHand(hand);
        }
        log.info(KARTE_ZUM_ABLAGESTAPEL_HINZUGEFUEGT_MESSAGE);
        ablagestapel.add(kartenstapel.get(kartenstapel.size() - 1));
        kartenstapel.remove(kartenstapel.get(kartenstapel.size() - 1));
    }
}
