package de.htw.berlin.maumau.spielregeln.spielregelnView;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;

import java.util.List;

public class SpielregelnView {

    public void printKarteGelegt(Kartentyp typ, Kartenwert wert){
        System.out.println("Die Karte "+typ.toString()+" "+wert.toString()+" wurde gelegt.");
    }

    public void printKarteNichtLegbar(Kartentyp typ, Kartenwert wert){
        System.out.println("Die Karte "+typ.toString()+" "+wert.toString()+" ist nicht legbar.");
    }

    public void printHatKartenGezogen(int anzahl, List<Karte> hand){
        System.out.println("Du hast "+anzahl+" Karten gezogen. Deine Hand enthält nun: ");
        for(Karte karte : hand){
            System.out.println(karte.getTyp().toString()+" "+karte.getWert().toString());
        }
    }
}
