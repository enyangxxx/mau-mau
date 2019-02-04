package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl;


import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoCreateException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoFindException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoRemoveException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoUpdateException;

import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartentyp;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartenwert;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import java.util.ArrayList;
import java.util.List;

@Transactional
public class SpielerDaoImpl implements SpielerDao {

    @PersistenceContext
    private EntityManager entityManager;


    public SpielerDaoImpl() {
        super();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(Spieler spieler) throws DaoCreateException {
        try {
            entityManager.persist(spieler);
        } catch (PersistenceException e) {
            throw new DaoCreateException(e.toString());
        }
    }

    public void remove(Spieler spieler) throws DaoRemoveException {
        try {
            entityManager.remove(spieler);
        } catch (PersistenceException e) {
            throw new DaoRemoveException(e.toString());
        }
    }

    public void update(Spieler spieler) throws DaoUpdateException {
        try {
            entityManager.merge(spieler);
        } catch (PersistenceException e) {
            throw new DaoUpdateException(e.toString());
        }
    }

    public Spieler findBys_id(int s_id) throws DaoFindException {
        Spieler spieler;
        try {
            spieler = entityManager.find(Spieler.class, s_id);
        } catch (PersistenceException e) {
            throw new DaoFindException(e.toString());
        }
        return spieler;
    }


    public List<Karte> findHand(int s_id) throws DaoFindException {
        Session session = (Session) entityManager.getDelegate();
        List<Integer> resultKartentyp;
        List<Integer> resultKartenwert;

        try {
            resultKartentyp = (List<Integer>) session.createSQLQuery("Select typ from Spieler_hand where spieler_s_id=" + s_id).list();
            resultKartenwert = (List<Integer>) session.createSQLQuery("Select wert from Spieler_hand where spieler_s_id=" + s_id).list();

        } catch (PersistenceException e) {
            throw new DaoFindException(e.toString());
        }

        List<Karte> finalResult = new ArrayList<Karte>();

        for (int i = 0; i < resultKartentyp.size(); i++) {
            Karte karte = new Karte(Kartentyp.getName(resultKartentyp.get(i)), Kartenwert.getName(resultKartenwert.get(i)));
            finalResult.add(karte);
        }
        return finalResult;
    }


    public int findAktuellerSpielerId() {
        Session session = (Session) entityManager.getDelegate();
        int aktuellerSpielerId = 0;
        aktuellerSpielerId= (Integer) session.createSQLQuery("Select s_id from Spieler where dran = true").uniqueResult();
        return aktuellerSpielerId;
    }

    public void updateHatMauGerufen(boolean status, int s_id) throws DaoUpdateException {
        Session session = (Session) entityManager.getDelegate();
        try {
            session.createSQLQuery("Update Spieler set maugerufen =" + String.valueOf(status) + " where s_id=" + s_id).executeUpdate();
        } catch (PersistenceException e) {
            throw new DaoUpdateException(e.toString());
        }
    }

    public void updateDran(boolean status, int s_id) throws DaoUpdateException {
        Session session = (Session) entityManager.getDelegate();
        try {
            session.createSQLQuery("Update Spieler set dran =" + String.valueOf(status) + " where s_id=" + s_id).executeUpdate();
        } catch (PersistenceException e) {
            throw new DaoUpdateException(e.toString());
        }
    }

    public boolean isVirtuellerSpieler(int s_id) throws DaoUpdateException {
        if(s_id>findMaxId()){
            return true;
        }
        return false;
    }

    private int findMaxId() throws DaoUpdateException {
        Session session = (Session) entityManager.getDelegate();
        int maxId;
        try {
            maxId = (Integer) session.createSQLQuery("Select max(s_id) from Spieler").uniqueResult();
        } catch (PersistenceException e) {
            throw new DaoUpdateException(e.toString());
        }
        return maxId;
    }

    public boolean findIsIstComputer(int s_id) throws DaoFindException {
        Session session = (Session) entityManager.getDelegate();
        boolean status;

        try {
            status = (Boolean) session.createSQLQuery("Select istComputer from Spieler where s_id="+s_id).uniqueResult();
        } catch (PersistenceException e) {
            throw new DaoFindException(e.getMessage());
        }
        return status;
    }


}
