package de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.errorHandling.KeineKarteException;
import de.htw.berlin.maumau.errorHandling.KeineSpielerException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.ISpielverwaltung;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class SpielverwaltungImpl implements ISpielverwaltung {

    private Log log = LogFactory.getLog(SpielverwaltungImpl.class);
    private static final String KEINESPIELER_EXCEPTION_MESSAGE = "Keine Spielerliste im Spiel";
    private static final String KEINEKARTEN_EXCEPTION_MESSAGE = "Keine Karten sind an der Aktion beteiligt";
    private static final String NEUE_RUNDE_MESSAGE = "Neue Runde im Spiel";
    private static final String KARTE_ZIEHEN_MESSAGE = "Eine Karte wurde zogen";
    private static final String WUNSCHTYP_GESETZT_MESSAGE = "Wunschtyp gesetzt: ";
    private static final String MAU_GERUFEN_MESSAGE = "MauMau wurde gerufen";
    private static final String MAU_NICHT_GERUFEN_MESSAGE = "MauMau wurde nicht gerufen";
    private static final String NEUES_SPIEL_MESSAGE = "Happy game!";
    private static final String KARTE_ABLEGEN_MESSAGE = "Karte aus Hand in den Ablagestapel gelegt";
    private static final String MAU_RUFEN_MESSAGE = "Spieler ruft MauMau!";
    private static final String MINUSPUNKTE_MESSAGE = "Der Spieler hat so viele Minuspunkte erhalten: ";
    private static final String ABLAGESTAPEL_LEER_MESSAGE = "Ablagestapel ist leer";

    public MauMauSpiel neuesSpielStarten(List<Spieler> spielerliste) throws KeineSpielerException{
        MauMauSpiel spiel = new MauMauSpiel(spielerliste);
        log.info(NEUES_SPIEL_MESSAGE);
        if(spielerliste.isEmpty()){
            log.error(KEINESPIELER_EXCEPTION_MESSAGE);
            throw new KeineSpielerException(KEINESPIELER_EXCEPTION_MESSAGE);
        }
        return spiel;
    }

    public void neueRundeStarten(MauMauSpiel spiel) {
        spiel.setRunde(spiel.getRunde()+1);
        log.info(NEUE_RUNDE_MESSAGE);
    }

    public void karteZiehen(Spieler spieler, List<Karte> kartenstapel) {
        List<Karte> hand = spieler.getHand();
        //Wenn Kartenstapel leer, nutze kartenstapelGenerieren
        hand.add(kartenstapel.get(0));
        spieler.setHand(hand);
        kartenstapel.remove(0);
        log.info(KARTE_ZIEHEN_MESSAGE);
    }

    public void karteZiehen(Spieler spieler, List<Karte> kartenstapel, int anzahl) {
        List<Karte> hand = spieler.getHand();
        int alteMenge = kartenstapel.size();
        //Wenn Kartenstapel leer, nutze kartenstapelGenerieren
        for (Iterator<Karte> iterator = kartenstapel.iterator(); alteMenge - kartenstapel.size() < anzahl;){
            if(iterator.hasNext()){
                hand.add(iterator.next());
                iterator.remove();
                log.info(KARTE_ZIEHEN_MESSAGE);
            }
        }
        spieler.setHand(hand);
    }

    public void karteLegen(MauMauSpiel spiel, Karte gewaehlteKarte, List<Karte> hand, List<Karte> ablagestapel, Kartentyp wunschtyp) {
        for (Iterator<Karte> iterator = hand.iterator(); iterator.hasNext();) {
            Karte karte = iterator.next();
            if(karte.getWert().equals(gewaehlteKarte.getWert()) && karte.getTyp().equals(gewaehlteKarte.getTyp())) {
                iterator.remove();
                ablagestapel.add(karte);
                log.info(KARTE_ABLEGEN_MESSAGE);
                break;
            }
        }
        if (wunschtyp != null){
            spiel.setWunschtyp(wunschtyp);
            log.info(new StringBuilder(WUNSCHTYP_GESETZT_MESSAGE + wunschtyp.toString()));
        }
    }

    public void karteLegen(Karte gewaehlteKarte, List<Karte> hand, List<Karte> ablagestapel) {
        for (Iterator<Karte> iterator = hand.iterator(); iterator.hasNext();) {
            Karte karte = iterator.next();
            if(karte.getWert().equals(gewaehlteKarte.getWert()) && karte.getTyp().equals(gewaehlteKarte.getTyp())) {
                iterator.remove();
                ablagestapel.add(karte);
                log.info(KARTE_ABLEGEN_MESSAGE);
            }
        }
    }

    public Karte letzteKarteErmitteln(List<Karte> ablagestapel) throws KeineKarteException {
        if(ablagestapel.isEmpty()) {
            log.info(KEINEKARTEN_EXCEPTION_MESSAGE);
            throw new KeineKarteException(ABLAGESTAPEL_LEER_MESSAGE);
        }
        return ablagestapel.get(ablagestapel.size() - 1);
    }

    public void maumauPruefen(Spieler spieler, List<Karte> kartenstapel) {
        if(spieler.hatMauGerufen()){
            spieler.setHatMauGerufen(false);
            log.info(MAU_GERUFEN_MESSAGE);
        }else{
            log.info(MAU_NICHT_GERUFEN_MESSAGE);
            karteZiehen(spieler,kartenstapel,2);
        }
    }

    public void maumauRufen(Spieler spieler) {
        spieler.setHatMauGerufen(true);
        log.info(MAU_RUFEN_MESSAGE);
    }

    public int minuspunkteBerechnen(List<Karte> hand) {
        int punktzahl = 0;
        for(Karte karte : hand){
            Kartenwert wert = karte.getWert();
            switch (wert){
                case SIEBEN: punktzahl+=7; break;
                case ACHT: punktzahl+=8; break;
                case NEUN: punktzahl+=9; break;
                case ZEHN: punktzahl+=10; break;
                case ASS: punktzahl+=11; break;
                case BUBE:punktzahl+=2; break;
                case DAME:punktzahl+=3; break;
                case KOENIG:punktzahl+=4; break;
                default: punktzahl+=0;break;
            }
        }
        log.info(new StringBuilder(MINUSPUNKTE_MESSAGE + String.valueOf(punktzahl)));
        return punktzahl;
    }
}
