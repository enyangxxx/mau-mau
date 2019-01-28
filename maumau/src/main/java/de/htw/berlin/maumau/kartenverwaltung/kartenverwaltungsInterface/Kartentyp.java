/**
 * 
 */
package de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public enum Kartentyp {
HERZ,
PIK,
KREUZ,
KARO;

    public static Kartentyp getName(int i){
        switch (i){
            case 0: return Kartentyp.HERZ;
            case 1: return Kartentyp.PIK;
            case 2: return Kartentyp.KREUZ;
            case 3: return Kartentyp.KARO;
            default: return null;
        }
    }

}


