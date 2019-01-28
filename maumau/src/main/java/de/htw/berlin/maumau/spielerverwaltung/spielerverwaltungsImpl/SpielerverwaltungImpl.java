package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl;

import de.htw.berlin.maumau.errorHandling.IdDuplikatException;
import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.KeinSpielerException;
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

    private SpielerDao spielerDao;
    private MauMauSpielDao maumauSpielDao;

    //= (SpielerDao) ConfigServiceImpl.context.getBean("spielerdaoimpl");
    //private  SpielerDao spielerDao = new SpielerDaoImpl();


    public SpielerverwaltungImpl(final SpielerDao spielerDaoImpl, final MauMauSpielDao maumauSpielDao) {
        log.info("SpielerverwaltungsImpl Konstruktor called");
        this.spielerDao = spielerDaoImpl;
        this.maumauSpielDao = maumauSpielDao;
        //private SpielerDao spielerDao = (SpielerDao) ConfigServiceImpl.context.getBean("spielerdaoimpl");
    }

    /**
     * Ein Spieler wird generiert.
     * @param name        - Name des Spielers
     * @param id          - ID des Spielers
     * @param istComputer - true wenn Computer-Spieler
     * @return spieler - der generierte Spieler
     */
    public void spielerGenerieren(String name, int id, boolean istComputer) throws Exception {
        Spieler spieler = new Spieler(name, id, istComputer);
        spielerDao.create(spieler);
        log.info("spielerDao.create -> Name: "+spielerDao.findBys_id(id).getName()+" ID: "+spielerDao.findBys_id(id).getS_id());
        //return spieler;
    }

    /**
     * Der Spieler wird der Spielerliste hinzugefügt.
     * @param spieler      - der neue Spieler
     * @param spielerliste - aktuelle Spielerliste
     * @throws IdDuplikatException - Wenn eine ID doppelt vergeben wird
     */
    public void addSpielerZurListe(Spieler spieler, List<Spieler> spielerliste) throws IdDuplikatException {
        for (Spieler spielerx : spielerliste) {
            if (spielerx.getS_id() == spieler.getS_id()) {
                throw new IdDuplikatException("Die ID ist bereits vergeben");
            }
        }

        spielerliste.add(spielerDao.findBys_id(spieler.getS_id()));
        log.info("Spieler zur Liste hinzugefügt."+ "Name: "+spielerDao.findBys_id(spieler.getS_id()).getName() + "ID:"+spielerDao.findBys_id(spieler.getS_id()).getS_id());
    }


    /**
     * Der aktuelle Spieler ist nicht mehr dran, der nächste Spieler ist dran.
     //* @param spiel - das aktuelle MauMau-Spiel
     */
    public void spielerWechseln() throws Exception {
        MauMauSpiel spiel = maumauSpielDao.findById(0);
        List<Spieler> spielerliste = maumauSpielDao.findSpielerlist();
        Spieler alterSpieler = spielerDao.findBys_id(spielerDao.findAktuellerSpielerId());
        Spieler neuerSpieler;

        int alterSpielerId = alterSpieler.getS_id();
        log.info("alterSpieler ID: "+alterSpielerId);
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

        if(spiel.isSonderregelAssAktiv()){
            spiel.setSonderregelAssAktiv(false);
            maumauSpielDao.update(spiel);
            spielerWechseln();
        }
        maumauSpielDao.update(spiel);
    }


    /**
     * BRAUCHEN WIR EVTL. NICHT MEHR
     * Der Spieler wird durch die eindeutige ID ermittelt.
     * @param id           - ID des Spielers
     * @param spielerliste - aktuelle Spielerliste
     * @return gefundenerSpieler - der gesuchte Spieler mit der ID
     * @throws KeinSpielerException - Wenn kein Spieler mit der ID gefunden wurde
     */
    public Spieler getSpielerById(int id, List<Spieler> spielerliste) throws KeinSpielerException {
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
    public void kartenAusteilen() throws LeererStapelException, Exception {
        MauMauSpiel spiel = maumauSpielDao.findById(0);
        List<Spieler> spielerliste = maumauSpielDao.findSpielerlist();
        List<Karte> kartenstapel = maumauSpielDao.findKartenstapel();
        List<Karte> ablagestapel = maumauSpielDao.findAblagestapel();

        for (Spieler spieler : spielerliste) {
            List<Karte> hand = new ArrayList<Karte>();
            for (int i = 0; i < 2; i++) {
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
        //MauMauSpiel spiel = maumauSpielDao.findById(0);
        //List<Karte> kartenstapel = maumauSpielDao.findKartenstapel();
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
