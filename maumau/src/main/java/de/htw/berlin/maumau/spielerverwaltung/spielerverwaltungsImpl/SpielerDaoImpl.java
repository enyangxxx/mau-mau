package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl;

import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartentyp;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartenwert;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Transactional
public class SpielerDaoImpl implements SpielerDao {

    //EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("managerHsqldb");

    //private EntityManager entityManager = entityManagerFactory.createEntityManager();

    @PersistenceContext
    private EntityManager entityManager;

    //private EntityManager entityManager = (EntityManager) ConfigServiceImpl.context.getBean("myEmf");


    //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    //private EntityManager entityManager = context.getBean()


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

    public void createSpielerlisteTable(){
        entityManager.createNativeQuery("Create table Spielerliste").executeUpdate();
    }

    public void addToTableSpielerliste(Spieler spieler){
        entityManager.createNativeQuery("Insert into spielerliste "+spieler).executeUpdate();
    }

    public List<Karte> findHand(int s_id){
        //TypedQuery<Spieler> q = entityManager.createQuery("SELECT s FROM MauMauSpiel_Spieler s",Spieler.class);
        //Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Session session = (Session) entityManager.getDelegate();

        List<Integer> resultKartentyp = (List<Integer>) session.createSQLQuery("Select typ from Spieler_hand where spieler_s_id=" +s_id).list();
        List<Integer> resultKartenwert = (List<Integer>) session.createSQLQuery("Select wert from Spieler_hand where spieler_s_id=" +s_id).list();

        List<Karte> finalResult = new ArrayList<Karte>();

        for(int i=0;i<resultKartentyp.size();i++){
            //log.info("Typ aus findMethode: "+Kartentyp.getName(resultKartentyp.get(i)));
            //log.info("Wert aus findMethode: "+Kartenwert.getName(resultKartenwert.get(i)));

            Karte karte = new Karte(Kartentyp.getName(resultKartentyp.get(i)), Kartenwert.getName(resultKartenwert.get(i)));
            finalResult.add(karte);
        }

        //List<Karte> result = (List<Karte>) session.createSQLQuery("Select * from MauMauSpiel_kartenstapel").list();

        return finalResult;
    }

    public int findAktuellerSpielerId(){
        Session session = (Session) entityManager.getDelegate();
        int aktuellerSpielerId = (Integer) session.createSQLQuery("Select s_id from Spieler where dran = true").uniqueResult();
        return aktuellerSpielerId;
    }

    public void updateHatMauGerufen(boolean status, int s_id){
        Session session = (Session) entityManager.getDelegate();
        session.createSQLQuery("Update Spieler set maugerufen ="+String.valueOf(status)+" where s_id="+s_id).executeUpdate();

        //session.createSQLQuery("Update MauMauSpiel set runde ="+runde+" where spielid=0").executeUpdate();
    }


}
