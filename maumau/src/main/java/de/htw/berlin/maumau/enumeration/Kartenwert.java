/**
 * 
 */
package de.htw.berlin.maumau.enumeration;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public enum Kartenwert {
SIEBEN,
ACHT,
NEUN,
ZEHN,
BUBE,
DAME,
KOENIG,
ASS;

    public static Kartenwert getName(int i){
        switch (i){
            case 0: return Kartenwert.SIEBEN;
            case 1: return Kartenwert.ACHT;
            case 2: return Kartenwert.NEUN;
            case 3: return Kartenwert.ZEHN;
            case 4: return Kartenwert.BUBE;
            case 5: return Kartenwert.DAME;
            case 6: return Kartenwert.KOENIG;
            case 7: return Kartenwert.ASS;
            default: return null;
        }
    }
}
