package de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl;

import de.htw.berlin.maumau.configurator.ConfigServiceImpl;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.IKartenverwaltung;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Transactional
public class MauMauSpielDaoImpl implements MauMauSpielDao {

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
        try {
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
        List<Spieler> result = (List<Spieler>) entityManager.createQuery("from Spieler").getResultList();
        return result;
    }

    public MauMauSpiel findById(int spielId)
    {
        return entityManager.find(MauMauSpiel.class,spielId);
    }
}
