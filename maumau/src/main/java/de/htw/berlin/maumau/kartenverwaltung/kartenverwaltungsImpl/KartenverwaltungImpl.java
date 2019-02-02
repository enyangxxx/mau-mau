package de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsImpl;


import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoFindException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoUpdateException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartentyp;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartenwert;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.LeererStapelException;
import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.InkorrekterStapelException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.IKartenverwaltung;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl.MauMauSpielDao;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class KartenverwaltungImpl implements IKartenverwaltung {


    private Log log = LogFactory.getLog(KartenverwaltungImpl.class);

    private static final String KARTENSTAPEL_GENERIERT_MESSAGE = "Kartenstapel wurde generiert!";
    private static final String KARTEN_GEMISCHT_MESSAGE = "Kartenstapel wurde gemischt!";
    private static final String ABLAGESTAPEL_WIEDERVERWENDET_MESSAGE = "Ablagestapel wurde in Kartenstapel gemischt!";

    private KarteDao karteDao;
    private MauMauSpielDao maumauSpielDao;

    public KartenverwaltungImpl(final KarteDao karteDaoImpl, final MauMauSpielDao maumauSpielDaoImpl) {
        this.karteDao = karteDaoImpl;
        this.maumauSpielDao = maumauSpielDaoImpl;

        log.info("KartenverwaltungImpl Konstruktor called");
    }

    /**
     * Generiert einen Kartenstapel mit 32 Karten, wobei jede Karte einmal vorkommt.
     *
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    public void kartenstapelGenerieren() throws DaoUpdateException, DaoFindException {
        ArrayList<Karte> kartenstapel = new ArrayList<Karte>();
        for (int i = 0; i < Kartentyp.values().length; i++) {
            for (int a = 0; a < Kartenwert.values().length; a++) {
                //karteDao.create(new Karte(Kartentyp.values()[i], Kartenwert.values()[a]));
                kartenstapel.add(new Karte(Kartentyp.values()[i], Kartenwert.values()[a]));
            }
        }
        MauMauSpiel spiel = maumauSpielDao.findById(0);
        spiel.setKartenstapel(kartenstapel);
        maumauSpielDao.update(spiel);
        //karteDao.createKartenstapel(kartenstapel);
        //karteDao.create(kartenstapel);
        log.info(KARTENSTAPEL_GENERIERT_MESSAGE);
    }

    /**
     * Mischt den Kartenstapel, sodass die Reihenfolge der Karten zufällig ist.
     *
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     */
    public void kartenMischen() throws DaoUpdateException, DaoFindException {
        MauMauSpiel spiel = maumauSpielDao.findById(0);
        List<Karte> kartenstapel = maumauSpielDao.findKartenstapel();
        if(kartenstapel.isEmpty()){
            try {
                throw new InkorrekterStapelException("Der Kartenstapel ist leer");
            } catch (InkorrekterStapelException e) {
                log.error(e.toString());
            }
        }else {
            log.info(KARTEN_GEMISCHT_MESSAGE);
            Collections.shuffle(kartenstapel);
        }
        spiel.setKartenstapel(kartenstapel);
        maumauSpielDao.update(spiel);
    }


    /**
     * Mischt die Karten des Ablagestapels in den Kartenstapel, außer die oberste Karte des Ablagestapels.
     *
     * @throws LeererStapelException - Der leerer Stapel darf nicht leer sein.
     * @throws DaoFindException - beim fehlerhaften Lesen in der Dao-Klasse
     * @throws DaoUpdateException - beim fehlerhaften Updaten in der Dao-Klasse
     */
    public void ablagestapelWiederverwenden() throws LeererStapelException, DaoFindException, DaoUpdateException {
        MauMauSpiel spiel = maumauSpielDao.findById(0);
        List<Karte> kartenstapel = maumauSpielDao.findKartenstapel();
        List<Karte> ablagestapel = maumauSpielDao.findAblagestapel();

        if(!kartenstapel.isEmpty()){
            try {
                throw new InkorrekterStapelException("Der Kartenstapel darf keine Karten haben.");
            } catch (InkorrekterStapelException e) {
            }
        }else{
            try{
                Karte letzteKarte = ablagestapel.get(ablagestapel.size()-1);
                ablagestapel.remove(ablagestapel.size()-1);
                kartenstapel.addAll(ablagestapel);
                ablagestapel.removeAll(ablagestapel);
                log.info(ABLAGESTAPEL_WIEDERVERWENDET_MESSAGE);
                ablagestapel.add(letzteKarte);
            }catch(Exception e){
                throw new LeererStapelException("Der Ablagestapel darf nicht leer sein.");
            }

        }
        spiel.setAblagestapel(ablagestapel);
        spiel.setKartenstapel(kartenstapel);
        maumauSpielDao.update(spiel);

    }


}
