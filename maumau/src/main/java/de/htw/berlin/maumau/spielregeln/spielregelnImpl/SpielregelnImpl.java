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
    ISpielverwaltung spielverwaltung;
    ISpielerverwaltung spielerverwaltung;
    private Log log = LogFactory.getLog(SpielregelnImpl.class);


    // private ISpielverwaltung testSpielverwaltung;


    public SpielregelnImpl(final ISpielerverwaltung spielerverwaltungimpl, final ISpielverwaltung spielverwaltungimpl){
        log.info("SpielregelnImpl Konstruktor called");
        this.spielerverwaltung = spielerverwaltungimpl;
        this.spielverwaltung = spielverwaltungimpl;
    }

    public boolean istLegbar(Karte neueKarte, Kartentyp wunschtyp) throws KeinWunschtypException {
        if(wunschtyp == null){
            throw new KeinWunschtypException("Es wurde kein Wunschtyp gesetzt.");
        }

        if(neueKarte.getTyp().equals(wunschtyp)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean istLegbar(Karte letzteKarte, Karte neueKarte) {
        if(neueKarte.getTyp().equals(letzteKarte.getTyp())||neueKarte.getWert().equals(letzteKarte.getWert())){
            return true;
        }
        else{
            return false;
        }
    }

    public SonderregelTyp sonderregelErmitteln(Karte letzteKarte) {
        switch(letzteKarte.getWert()){
            case ASS: return SonderregelTyp.ASS;
            case BUBE: return SonderregelTyp.BUBE;
            case SIEBEN: return SonderregelTyp.SIEBEN;
            default: return SonderregelTyp.KEINE;
        }
    }

    public void sonderregelAussetzen(Spieler aktuellerSpieler, Spieler neuerSpieler, MauMauSpiel spiel) {
        for(Karte karte : aktuellerSpieler.getHand()) {
            if(karte.getWert().equals(Kartenwert.ASS)){
                spielverwaltung.karteLegen(karte, aktuellerSpieler.getHand(), spiel.getAblagestapel());
            }
        }
        spielerverwaltung.spielerWechseln(aktuellerSpieler, neuerSpieler);
    }

    public void sonderregelKartenZiehen(Spieler aktuellerSpieler, Spieler neuerSpieler, MauMauSpiel spiel){
        boolean kannKontern = false;
        for(Karte karte : aktuellerSpieler.getHand()){
            if(karte.getWert().equals(Kartenwert.SIEBEN)){
                kannKontern = true;
                spielverwaltung.karteLegen(karte, aktuellerSpieler.getHand(), spiel.getAblagestapel());
                spiel.setAnzahlSonderregelKartenZiehen(spiel.getAnzahlSonderregelKartenZiehen()+2);
                break;
            }
        }
        if(!kannKontern){
            spielverwaltung.karteZiehen(aktuellerSpieler, spiel.getKartenstapel(), spiel.getAnzahlSonderregelKartenZiehen(), spiel.getAblagestapel());
        }
        spielerverwaltung.spielerWechseln(aktuellerSpieler, neuerSpieler);
    }
}
