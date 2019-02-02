package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl;

import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.KeinSpielerException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoCreateException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoFindException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoUpdateException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.LeererStapelException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.ISpielerverwaltung;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl.MauMauSpielDao;
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
    private static final String SPIELER_GENERIERT = "Ein neuer Spieler wurde generiert";
    private SpielerDao spielerDao;
    private MauMauSpielDao maumauSpielDao;


    public SpielerverwaltungImpl(final SpielerDao spielerDaoImpl, final MauMauSpielDao maumauSpielDao) {
        log.info("SpielerverwaltungsImpl Konstruktor called");
        this.spielerDao = spielerDaoImpl;
        this.maumauSpielDao = maumauSpielDao;
    }

    /**
     * Ein Spieler wird generiert.
     * @param name        - Name des Spielers
     * @param id          - ID des Spielers
     * @param istComputer - true wenn Computer-Spieler
     * @return spieler - der generierte Spieler
     */
    public void spielerGenerieren(String name, int id, boolean istComputer) throws DaoCreateException, DaoFindException {
        Spieler spieler = new Spieler(name, id, istComputer);
        spielerDao.create(spieler);
        log.info(SPIELER_GENERIERT);
    }

    /**
     * Der Spieler wird der Spielerliste hinzugefügt.
     * @param spieler      - der neue Spieler
     * @param spielerliste - aktuelle Spielerliste
     */
    public void addSpielerZurListe(Spieler spieler, List<Spieler> spielerliste) throws DaoFindException {
        spielerliste.add(spielerDao.findBys_id(spieler.getS_id()));
    }


    /**
     * Der aktuelle Spieler ist nicht mehr dran, der nächste Spieler ist dran.
     //* @param spiel - das aktuelle MauMau-Spiel
     */
    public void spielerWechseln() throws Exception, DaoFindException, DaoUpdateException {
        MauMauSpiel spiel = maumauSpielDao.findSpiel();
        Spieler alterSpieler = spielerDao.findBys_id(spielerDao.findAktuellerSpielerId());
        Spieler neuerSpieler;
        int alterSpielerId = alterSpieler.getS_id();
        log.info("Alter Spieler ID: "+alterSpielerId);
        log.info("Alter Spieler ID aus Objekgt: "+alterSpieler.getS_id());


        alterSpieler.setDran(false);
        spielerDao.update(alterSpieler);


        if(alterSpielerId==maumauSpielDao.findSpielerlist().size()){
            neuerSpieler = spielerDao.findBys_id(1);
            neuerSpieler.setDran(true);
            spielerDao.update(neuerSpieler);
        }
        else{
            neuerSpieler = spielerDao.findBys_id(alterSpielerId+1);
            neuerSpieler.setDran(true);
            spielerDao.update(neuerSpieler);
        }
        log.info("Neuer Spieler ID: "+neuerSpieler.getS_id());

        if(spiel.isSonderregelAssAktiv()){
            spiel.setSonderregelAssAktiv(false);
            maumauSpielDao.update(spiel);
            spielerWechseln();
        }
    }


    /**
     * BRAUCHEN WIR EVTL. NICHT MEHR
     * Der Spieler wird durch die eindeutige ID ermittelt.
     * @param id           - ID des Spielers
     * @param spielerliste - aktuelle Spielerliste
     * @return gefundenerSpieler - der gesuchte Spieler mit der ID
     * @throws KeinSpielerException - Wenn kein Spieler mit der ID gefunden wurde
     */
    public Spieler getSpielerById(int id, List<Spieler> spielerliste) throws DaoFindException {
        Spieler gefundenerSpieler = null;
        for (Spieler spieler : maumauSpielDao.findSpielerlist()) {
            if (spielerDao.findBys_id(spieler.getS_id()).getS_id() == id) {
                gefundenerSpieler = spieler;
            }
        }
        if (gefundenerSpieler == null) {
            try {
                throw new KeinSpielerException("Kein Spieler konnte mit der ID " + id + "gefunden werden!");
            }catch(KeinSpielerException e){}
        }

        return gefundenerSpieler;

    }

    /**
     * Alle {@link Spieler} bekommen je 5 {@link Karte} aus dem Kartenstapel und es wird eine Anfangskarte aufgedeckt.
     *
     * //@param spielerliste - die aktuelle Spielerliste
     * //@param kartenstapel - der aktuelle Kartenstapel
     * //@param ablagestapel - der aktuelle Ablagestapel
     */
    public void kartenAusteilen() throws LeererStapelException, DaoUpdateException, DaoFindException {
        MauMauSpiel spiel = maumauSpielDao.findSpiel();
        List<Spieler> spielerliste = maumauSpielDao.findSpielerlist();
        List<Karte> kartenstapel = maumauSpielDao.findKartenstapel();
        List<Karte> ablagestapel = maumauSpielDao.findAblagestapel();

        for (Spieler spieler : spielerliste) {
            List<Karte> hand = new ArrayList<Karte>();
            for (int i = 0; i < 5; i++) {
                try {
                    hand.add(kartenstapel.get(i));
                    kartenstapel.remove(kartenstapel.get(i));
                }catch(Exception e){
                    throw new LeererStapelException("Nicht genug Karten im Kartenstapel!");
                }
            }
            log.info(SPIELER_HAND_GESETZT + spieler.getName());
            spieler.setHand(hand);
            spielerDao.update(spieler);
            //log.info("SpielerDao Hand size: "+spielerDao.findBys_id(0).getHand().getClass());
            log.info("SpielerDao Hand size: "+spielerDao.findHand(spieler.getS_id()).size());

        }
        log.info(KARTE_ZUM_ABLAGESTAPEL_HINZUGEFUEGT_MESSAGE);
        try {
            ablagestapel.add(kartenstapel.get(kartenstapel.size() - 1));
            kartenstapel.remove(kartenstapel.get(kartenstapel.size() - 1));
            spiel.setAblagestapel(ablagestapel);
            spiel.setKartenstapel(kartenstapel);
            maumauSpielDao.update(spiel);
        }catch(Exception e){
            throw new LeererStapelException("Es konnte keine Karte konnte nicht vom Kartenstapel auf Ablagestapel gelegt werden");
        }
    }
}
