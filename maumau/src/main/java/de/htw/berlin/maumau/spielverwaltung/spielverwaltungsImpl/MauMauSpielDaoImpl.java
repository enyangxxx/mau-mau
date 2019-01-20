package de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl;

import de.htw.berlin.maumau.configurator.ConfigServiceImpl;
import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.IKartenverwaltung;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl.SpielerverwaltungImpl;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
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

    public void create(MauMauSpiel spiel) throws Exception {
        try {
            entityManager.persist(spiel);
        } catch (PersistenceException e) {
            //throw new DaoException(e);
            throw new Exception(e.getMessage());
        }
    }

    public void remove(MauMauSpiel spiel) throws Exception {
        try {
            entityManager.remove(spiel);
        } catch (PersistenceException e) {
            //throw new DaoException(e);
            throw new Exception(e.getMessage());
        }
    }

    public void update(MauMauSpiel spiel) throws Exception {
        Session session = (Session) entityManager.getDelegate();
        //session.merge(spiel);
        try {
            //session.merge(spiel);
            //session.update(spiel);
            entityManager.merge(spiel);
        } catch (PersistenceException e) {
            //throw new DaoException(e);
            throw new Exception(e.getMessage());
        }
    }

    public void insert_update(MauMauSpiel spiel) throws Exception {
        try {
            if(entityManager.contains(spiel)){
                entityManager.merge(spiel);
            }
            else{
                entityManager.persist(spiel);
            }
        } catch (PersistenceException e) {
            //throw new DaoException(e);
            throw new Exception(e.getMessage());
        }
    }
    public List<Spieler> findSpielerlist(){
        //TypedQuery<Spieler> q = entityManager.createQuery("SELECT s FROM MauMauSpiel_Spieler s",Spieler.class);
        //Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Session session = (Session) entityManager.getDelegate();
        List<Spieler> result = (List<Spieler>) session.createQuery("from Spieler").list();
        return result;
    }

    public List<Karte> findKartenstapel(){
        //TypedQuery<Spieler> q = entityManager.createQuery("SELECT s FROM MauMauSpiel_Spieler s",Spieler.class);
        //Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Session session = (Session) entityManager.getDelegate();

        List<Integer> resultKartentyp = (List<Integer>) session.createSQLQuery("Select typ from MauMauSpiel_kartenstapel").list();
        List<Integer> resultKartenwert = (List<Integer>) session.createSQLQuery("Select wert from MauMauSpiel_kartenstapel").list();

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

    public List<Karte> findAblagestapel(){
        //TypedQuery<Spieler> q = entityManager.createQuery("SELECT s FROM MauMauSpiel_Spieler s",Spieler.class);
        //Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Session session = (Session) entityManager.getDelegate();
        List<Karte> result = (List<Karte>) session.createSQLQuery("Select * from MauMauSpiel_ablagestapel").list();
        return result;
    }



    public MauMauSpiel findById(int spielId)
    {
        return entityManager.find(MauMauSpiel.class,spielId);
    }
}
