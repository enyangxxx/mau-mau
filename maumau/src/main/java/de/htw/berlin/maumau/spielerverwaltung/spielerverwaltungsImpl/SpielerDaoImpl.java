package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl;

import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.*;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class SpielerDaoImpl implements SpielerDao {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("managerHsqldb");

    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    private Log log = LogFactory.getLog(SpielerDaoImpl.class);



    public SpielerDaoImpl() {
        super();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(Spieler spieler) throws Exception {
        try {
            entityManager.persist(spieler);
        } catch (PersistenceException e) {
            //throw new DaoException(e);
            throw new Exception(e.getMessage());
        }
    }

    public void remove(Spieler spieler) throws Exception {
        try {
            entityManager.remove(spieler);
        } catch (PersistenceException e) {
            //throw new DaoException(e);
            throw new Exception(e.getMessage());
        }
    }

    public void update(Spieler spieler) throws Exception {
        try {
            entityManager.merge(spieler);
        } catch (PersistenceException e) {
            //throw new DaoException(e);
            throw new Exception(e.getMessage());
        }
    }

    public void insert_update(Spieler spieler) throws Exception {
        try {
            if(entityManager.contains(spieler)){
                //entityManager.merge(spieler);
                log.info("Spieler mit dieser ID bereits in der Datenbank vorhanden.");
            }
            else{
                entityManager.persist(spieler);
                log.info("Spieler erstellt und in die Datenbank eingefügt.");
            }
        } catch (PersistenceException e) {
            //throw new DaoException(e);
            throw new Exception(e.getMessage());
        }
    }


    public Spieler findBys_id(int s_id)
    {
        return entityManager.find(Spieler.class,s_id);
    }

    public List<Spieler> findAll() {
        /*CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Spieler> cq = cb.createQuery(Spieler.class);
        Root<Spieler> rootEntry = cq.from(Spieler.class);
        CriteriaQuery<Spieler> all = cq.select(rootEntry);
        TypedQuery<Spieler> allQuery = entityManager.createQuery(all);
        //log.info(allQuery.getSingleResult().getName());
        //return allQuery.getResultList();
        */

        entityManager.getTransaction().begin();
        List<Spieler> spielerliste = entityManager.createQuery("Select t from Spieler t").getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();
        //log.info(spielerliste.get(0));

        return spielerliste;
    }
}
