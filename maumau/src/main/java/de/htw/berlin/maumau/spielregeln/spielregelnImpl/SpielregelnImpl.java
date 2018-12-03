package de.htw.berlin.maumau.spielregeln.spielregelnImpl;

import de.htw.berlin.maumau.configurator.ConfigServiceImpl;
import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.enumeration.SonderregelTyp;
import de.htw.berlin.maumau.errorHandling.KeinWunschtypException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.ISpielerverwaltung;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielregeln.spielregelnInterface.ISpielregeln;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.ISpielverwaltung;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class SpielregelnImpl implements ISpielregeln {

    //ISpielverwaltung spielverwaltung = (ISpielverwaltung) ConfigServiceImpl.context.getBean("spielverwaltungimpl");
    //ISpielerverwaltung spielerverwaltung = (ISpielerverwaltung) ConfigServiceImpl.context.getBean("spielerverwaltungimpl");
    private Log log = LogFactory.getLog(SpielregelnImpl.class);


    // private ISpielverwaltung testSpielverwaltung;


    public SpielregelnImpl(){
        log.info("SpielregelnImpl Konstruktor called");
    }


    public boolean istLegbar(Karte neueKarte, Kartentyp wunschtyp) throws KeinWunschtypException {
        if(wunschtyp == null){
            throw new KeinWunschtypException("Es wurde kein Wunschtyp gesetzt.");
        }
        return neueKarte.getTyp().equals(wunschtyp);
    }


    public boolean istLegbar(Karte letzteKarte, Karte neueKarte) {
        return neueKarte.getTyp().equals(letzteKarte.getTyp())||neueKarte.getWert().equals(letzteKarte.getWert());
    }


    public boolean sonderregelEingehalten(Karte neueKarte, Karte letzteKarte){
        switch(letzteKarte.getWert()){
            case ASS: return neueKarte.getWert().equals(Kartenwert.ASS);
            case SIEBEN: return neueKarte.getWert().equals(Kartenwert.SIEBEN);
            default: return true;
        }
    }


}
