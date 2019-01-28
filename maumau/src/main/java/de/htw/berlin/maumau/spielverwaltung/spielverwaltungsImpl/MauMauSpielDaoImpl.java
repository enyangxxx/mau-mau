package de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl;

import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoCreateException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoFindException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoRemoveException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoUpdateException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartentyp;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartenwert;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Transactional
public class MauMauSpielDaoImpl implements MauMauSpielDao {

    private Log log = LogFactory.getLog(MauMauSpielDaoImpl.class);

    //EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("managerHsqldb");

    //private EntityManager entityManager = entityManagerFactory.createEntityManager();

    @PersistenceContext
    private EntityManager entityManager;

    //private EntityManager entityManager = (EntityManager) ConfigServiceImpl.context.getBean("myEmf");


    public MauMauSpielDaoImpl() {
        super();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(MauMauSpiel spiel) throws DaoCreateException {
        try {
            entityManager.persist(spiel);
        } catch (PersistenceException e) {
            throw new DaoCreateException(e.getMessage());
        }
    }

    public void remove(MauMauSpiel spiel) throws DaoRemoveException {
        try {
            entityManager.remove(spiel);
        } catch (PersistenceException e) {
            throw new DaoRemoveException(e.getMessage());
        }
    }

    public void update(MauMauSpiel spiel) throws DaoUpdateException {
        Session session = (Session) entityManager.getDelegate();
        //session.merge(spiel);
        try {
            //session.merge(spiel);
            //session.update(spiel);
            entityManager.merge(spiel);
        } catch (PersistenceException e) {
            throw new DaoUpdateException(e.getMessage());
        }
    }

    public List<Spieler> findSpielerlist() throws DaoFindException {
        //TypedQuery<Spieler> q = entityManager.createQuery("SELECT s FROM MauMauSpiel_Spieler s",Spieler.class);
        //Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Session session = (Session) entityManager.getDelegate();
        List<Spieler> result;
        try {
            result = (List<Spieler>) session.createQuery("from Spieler").list();
        } catch (PersistenceException e) {
            throw new DaoFindException(e.getMessage());
        }

        return result;
    }

    public List<Karte> findKartenstapel() throws DaoFindException {
        //TypedQuery<Spieler> q = entityManager.createQuery("SELECT s FROM MauMauSpiel_Spieler s",Spieler.class);
        //Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Session session = (Session) entityManager.getDelegate();
        List<Integer> resultKartentyp;
        List<Integer> resultKartenwert;

        try {
            resultKartentyp = (List<Integer>) session.createSQLQuery("Select typ from MauMauSpiel_kartenstapel").list();
            resultKartenwert = (List<Integer>) session.createSQLQuery("Select wert from MauMauSpiel_kartenstapel").list();
        } catch (PersistenceException e) {
            throw new DaoFindException(e.getMessage());
        }

        List<Karte> finalResult = new ArrayList<Karte>();

        for(int i=0;i<resultKartentyp.size();i++){
            //log.info("Typ aus findMethode: "+Kartentyp.getName(resultKartentyp.get(i)));
            //log.info("Wert aus findMethode: "+Kartenwert.getName(resultKartenwert.get(i)));

            Karte karte = new Karte(Kartentyp.getName(resultKartentyp.get(i)),Kartenwert.getName(resultKartenwert.get(i)));
            finalResult.add(karte);
        }

        //List<Karte> result = (List<Karte>) session.createSQLQuery("Select * from MauMauSpiel_kartenstapel").list();

        return finalResult;
    }

    public List<Karte> findAblagestapel() throws DaoFindException {
        //TypedQuery<Spieler> q = entityManager.createQuery("SELECT s FROM MauMauSpiel_Spieler s",Spieler.class);
        //Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Session session = (Session) entityManager.getDelegate();

        List<Integer> resultKartentyp;
        List<Integer> resultKartenwert;

        try {
            resultKartentyp = (List<Integer>) session.createSQLQuery("Select typ from MauMauSpiel_ablagestapel").list();
            resultKartenwert = (List<Integer>) session.createSQLQuery("Select wert from MauMauSpiel_ablagestapel").list();
        } catch (PersistenceException e) {
            throw new DaoFindException(e.getMessage());
        }

        List<Karte> finalResult = new ArrayList<Karte>();

        for(int i=0;i<resultKartentyp.size();i++){
            //log.info("Typ aus findMethode: "+Kartentyp.getName(resultKartentyp.get(i)));
            //log.info("Wert aus findMethode: "+Kartenwert.getName(resultKartenwert.get(i)));

            Karte karte = new Karte(Kartentyp.getName(resultKartentyp.get(i)),Kartenwert.getName(resultKartenwert.get(i)));
            finalResult.add(karte);
        }

        //List<Karte> result = (List<Karte>) session.createSQLQuery("Select * from MauMauSpiel_kartenstapel").list();

        return finalResult;
    }

    ///////////////////////
    public void updateRunde(int runde) throws DaoUpdateException {
        Session session = (Session) entityManager.getDelegate();
        try {
            session.createSQLQuery("Update MauMauSpiel set runde ="+runde+" where spielid=0").executeUpdate();
        } catch (PersistenceException e) {
            throw new DaoUpdateException(e.getMessage());
        }

    }

    public void updateKartenstapel(List<Karte> kartenstapel){

    }

    public MauMauSpiel findById(int spielId) throws DaoFindException {
        MauMauSpiel mauMauSpiel;
        try {
            mauMauSpiel = entityManager.find(MauMauSpiel.class,spielId);
        } catch (PersistenceException e) {
            throw new DaoFindException(e.getMessage());
        }
        return mauMauSpiel;
    }
}
