package de.htw.berlin.maumau.spielregeln.spielregelnImpl;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.errorHandling.KeinWunschtypException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielregeln.spielregelnInterface.ISpielregeln;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class SpielregelnImpl implements ISpielregeln {

    private Log log = LogFactory.getLog(SpielregelnImpl.class);


    public SpielregelnImpl() {
        log.info("SpielregelnImpl Konstruktor called");
    }


    /**
     * Anhand des {@link Kartentyp} Wunschtyps wird geprüft, ob die neue {@link Karte} gelegt werden kann.
     *
     * @param neueKarte - die neue Karte
     * @param wunschtyp - der Wunschtyp
     * @return true, wenn  die neue Karte legbar ist.
     */
    public boolean istLegbar(Karte neueKarte, Kartentyp wunschtyp){
        if (wunschtyp == null) {
            try {
                throw new KeinWunschtypException("Es wurde kein Wunschtyp gesetzt.");
            } catch (KeinWunschtypException e) {
                log.error(e.toString());
            }
        }

        return neueKarte.getTyp().equals(wunschtyp);
    }


    /**
     * Anhand der Farbe und des Werts der letzten {@link Karte} wird geprüft, ob die neue {@link Karte} gelegt werden kann.
     *
     * @param letzteKarte - die letzte Karte
     * @param neueKarte   - die neue Karte
     * @return true, wenn legbar.
     */
    public boolean istLegbar(Karte letzteKarte, Karte neueKarte) {
        return neueKarte.getTyp().equals(letzteKarte.getTyp()) || neueKarte.getWert().equals(letzteKarte.getWert());
    }


    /**
     * Es wird überprüft, ob die neue Karte {@link Karte} eine Sieben ist, wenn die letzte Karte auch eine Sieben ist.
     *
     * @param letzteKarte - die letzte Karte
     * @param neueKarte   - die neue Karte die gelegt werden soll
     * @return true - wenn die Sonderregel Sieben eingehalten wurde
     */
    public boolean sonderregelEingehaltenSieben(Karte neueKarte, Karte letzteKarte) {
        switch (letzteKarte.getWert()) {
            case SIEBEN:
                return neueKarte.getWert().equals(Kartenwert.SIEBEN);
            default:
                return true;
        }
    }


    /**
     * Es wird überprüft, ob die neue Karte {@link Karte} ein Bube ist, wenn die letzte Karte auch ein Bube ist.
     *
     * @param letzteKarte - die letzte Karte
     * @param neueKarte   - die neue Karte die gelegt werden soll
     * @return true - wenn versucht wird einen Buben auf einen Buben zu legen.
     */
    public boolean sonderregelEingehaltenBube(Karte neueKarte, Karte letzteKarte) {
        switch (letzteKarte.getWert()) {
            case BUBE:
                return !(neueKarte.getWert().equals(Kartenwert.BUBE));
            default:
                return true;
        }
    }


}
