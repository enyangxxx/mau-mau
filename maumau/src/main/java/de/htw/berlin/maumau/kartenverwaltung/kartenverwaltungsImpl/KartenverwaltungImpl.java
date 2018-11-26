package de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsImpl;


import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.errorHandling.KeineKarteException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.IKartenverwaltung;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class KartenverwaltungImpl implements IKartenverwaltung {

    private Log log = LogFactory.getLog(KartenverwaltungImpl.class);
    
    private static final String KARTENSTAPEL_GENERIERT_MESSAGE = "Kartenstapel wurde generiert!";
    private static final String KARTEN_GEMISCHT_MESSAGE = "Kartenstapel wurde gemischt!";
    private static final String SPIELER_HAND_GESETZT = "Die Hand wurde gesetzt für Spieler: ";
    private static final String KARTE_ZUM_ABLAGESTAPEL_HINZUGEFUEGT_MESSAGE = "Karte auf Ablagestapel gelegt!";
    private static final String ABLAGESTAPEL_WIEDERVERWENDET_MESSAGE = "Ablagestapel wurde in Kartenstapel gemischt!";

    public KartenverwaltungImpl(){
        log.info("KartenverwaltungImpl Konstruktor called");
    }
  
    /**
     * Generiert einen Kartenstapel mit 32 Karten, wobei jede Karte einmal vorkommt.
     *
     * @return generierter Kartenstapel
     */
    public List<Karte> kartenstapelGenerieren() {
		ArrayList<Karte> kartenstapel = new ArrayList<Karte>();
        for(int i = 0; i < Kartentyp.values().length; i++){
            for(int a = 0; a < Kartenwert.values().length; a++){
                kartenstapel.add(new Karte(Kartentyp.values()[i], Kartenwert.values()[a]));
            }
        }
        log.info(KARTENSTAPEL_GENERIERT_MESSAGE);
        return kartenstapel;
    }

    /**
     * Mischt den Kartenstapel, sodass die Reihenfolge der Karten zufällig ist.
     *
     * @param kartenstapel - der aktuelle Kartenstapel
     */
    public void kartenMischen(List<Karte> kartenstapel) throws KeineKarteException{
        try{
            log.info(KARTEN_GEMISCHT_MESSAGE);
            Collections.shuffle(kartenstapel);
        }catch (Exception e){
            throw new KeineKarteException("Keine Karte Exception");
        }
    }

    /**
     * Alle {@link Spieler} bekommen je 5 {@link Karte} aus dem Kartenstapel und es wird eine Anfangskarte aufgedeckt.
     *
     * @param spielerliste - die aktuelle Spielerliste
     * @param kartenstapel - der aktuelle Kartenstapel
     * @param ablagestapel - der aktuelle Ablagestapel
     */
    public void kartenAusteilen(List<Spieler> spielerliste, List<Karte> kartenstapel, List<Karte> ablagestapel) {
        for (Spieler spieler: spielerliste){
            List<Karte> hand = new ArrayList<Karte>();
            for(int i = 0;i < 5; i++){
                hand.add(kartenstapel.get(i));
                kartenstapel.remove(kartenstapel.get(i));
            }
            log.info(SPIELER_HAND_GESETZT + spieler.getName());
            spieler.setHand(hand);
        }
        log.info(KARTE_ZUM_ABLAGESTAPEL_HINZUGEFUEGT_MESSAGE);
        ablagestapel.add(kartenstapel.get(kartenstapel.size()-1));
        kartenstapel.remove(kartenstapel.get(kartenstapel.size()-1));
    }

    /**
     * Mischt die Karten des Ablagestapels in den Kartenstapel.
     *
     * @param ablagestapel - der aktuelle Ablagestapel
     * @param kartenstapel - der aktuelle Kartenstapel
     */
    public void ablagestapelWiederverwenden(List<Karte> ablagestapel, List<Karte> kartenstapel) {
        log.info(ABLAGESTAPEL_WIEDERVERWENDET_MESSAGE);
        kartenstapel.addAll(ablagestapel);
        ablagestapel.removeAll(ablagestapel);
    }



}
