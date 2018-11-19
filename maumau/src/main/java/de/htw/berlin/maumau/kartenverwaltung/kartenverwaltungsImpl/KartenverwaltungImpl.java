package de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsImpl;


import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.errorHandling.KeineKarteException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.IKartenverwaltung;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class KartenverwaltungImpl implements IKartenverwaltung {

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
        return kartenstapel;
    }

    /**
     * Mischt den Kartenstapel, sodass die Reihenfolge der Karten zufällig ist.
     *
     * @param kartenstapel - der aktuelle Kartenstapel
     */
    public void kartenMischen(List<Karte> kartenstapel) {
        Collections.shuffle(kartenstapel);
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
            spieler.setHand(hand);
        }
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
        kartenstapel.addAll(ablagestapel);
    }



}
