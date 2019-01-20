package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl;

import de.htw.berlin.maumau.errorHandling.IdDuplikatException;
import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.KeinSpielerException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.LeererStapelException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.ISpielerverwaltung;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
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
    //= (SpielerDao) ConfigServiceImpl.context.getBean("spielerdaoimpl");
    //private  SpielerDao spielerDao = new SpielerDaoImpl();


    public SpielerverwaltungImpl(final SpielerDao spielerDaoImpl) {
        log.info("SpielerverwaltungsImpl Konstruktor called");
        this.spielerDao = spielerDaoImpl;
        //private SpielerDao spielerDao = (SpielerDao) ConfigServiceImpl.context.getBean("spielerdaoimpl");
    }

    /**
     * Ein Spieler wird generiert.
     * @param name        - Name des Spielers
     * @param id          - ID des Spielers
     * @param istComputer - true wenn Computer-Spieler
     * @return spieler - der generierte Spieler
     */
    public Spieler spielerGenerieren(String name, int id, boolean istComputer) throws Exception {
        Spieler spieler = new Spieler(name, id, istComputer);
        spielerDao.create(spieler);
        log.info("spielerDao.create -> Name: "+spielerDao.findBys_id(id).getName()+" ID: "+spielerDao.findBys_id(id).getS_id());
        return spielerDao.findBys_id(id);
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
     * @param spiel - das aktuelle MauMau-Spiel
     */
    public void spielerWechseln(MauMauSpiel spiel) {
        List<Spieler> spielerliste = spiel.getSpielerListe();
        for (int i = 0; i < spielerliste.size(); i++) {
            if (spielerliste.get(i).isDran()) {
                spielerliste.get(i).setDran(false);
                if (spielerliste.size() - 1 == i) {
                    spielerliste.get(0).setDran(true);
                } else {
                    spielerliste.get(i + 1).setDran(true);
                }
                break;
            }
        }

        if(spiel.isSonderregelAssAktiv()){
            spiel.setSonderregelAssAktiv(false);
            spielerWechseln(spiel);
        }
    }


    /**
     * Der Spieler wird durch die eindeutige ID ermittelt.
     * @param id           - ID des Spielers
     * @param spielerliste - aktuelle Spielerliste
     * @return gefundenerSpieler - der gesuchte Spieler mit der ID
     * @throws KeinSpielerException - Wenn kein Spieler mit der ID gefunden wurde
     */
    public Spieler getSpielerById(int id, List<Spieler> spielerliste) throws KeinSpielerException {
        Spieler gefundenerSpieler = null;
        for (Spieler spieler : spielerliste) {
            if (spieler.getS_id() == id) {
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
     * @param spielerliste - die aktuelle Spielerliste
     * @param kartenstapel - der aktuelle Kartenstapel
     * @param ablagestapel - der aktuelle Ablagestapel
     */
    public void kartenAusteilen(List<Spieler> spielerliste, List<Karte> kartenstapel, List<Karte> ablagestapel) throws LeererStapelException {
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
        }
        log.info(KARTE_ZUM_ABLAGESTAPEL_HINZUGEFUEGT_MESSAGE);
        try {
            ablagestapel.add(kartenstapel.get(kartenstapel.size() - 1));
            kartenstapel.remove(kartenstapel.get(kartenstapel.size() - 1));
        }catch(Exception e){
            throw new LeererStapelException("Es konnte keine Karte konnte nicht vom Kartenstapel auf Ablagestapel gelegt werden");
        }
    }
}
