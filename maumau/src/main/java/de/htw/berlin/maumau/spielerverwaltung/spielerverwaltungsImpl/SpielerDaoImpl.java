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
        }catch(PersistenceException e){
            throw new DaoFindException(e.toString());
        }
        return spieler;
    }

    public List<Spieler> findAll() throws DaoFindException {
        /*CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Spieler> cq = cb.createQuery(Spieler.class);
        Root<Spieler> rootEntry = cq.from(Spieler.class);
        CriteriaQuery<Spieler> all = cq.select(rootEntry);
        TypedQuery<Spieler> allQuery = entityManager.createQuery(all);
        //log.info(allQuery.getSingleResult().getName());
        //return allQuery.getResultList();
        */
        List<Spieler> spielerliste;

        try {
            entityManager.getTransaction().begin();
            spielerliste = entityManager.createQuery("Select t from Spieler t").getResultList();
            entityManager.getTransaction().commit();
        }catch(PersistenceException e){
            throw new DaoFindException(e.toString());
        }finally {
            entityManager.close();
        }

        return spielerliste;
    }

    public void createSpielerlisteTable() throws DaoCreateException {
        try {
            entityManager.createNativeQuery("Create table Spielerliste").executeUpdate();
        } catch (PersistenceException e) {
            throw new DaoCreateException(e.toString());
        }
    }

    public void addToTableSpielerliste(Spieler spieler) throws DaoUpdateException {
        try {
            entityManager.createNativeQuery("Insert into spielerliste "+spieler).executeUpdate();
        } catch (PersistenceException e) {
            throw new DaoUpdateException(e.toString());
        }
    }

    public List<Karte> findHand(int s_id) throws DaoFindException {
        //TypedQuery<Spieler> q = entityManager.createQuery("SELECT s FROM MauMauSpiel_Spieler s",Spieler.class);
        //Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Session session = (Session) entityManager.getDelegate();
        List<Integer> resultKartentyp;
        List<Integer> resultKartenwert;

        try {
            resultKartentyp = (List<Integer>) session.createSQLQuery("Select typ from Spieler_hand where spieler_s_id=" +s_id).list();
            resultKartenwert = (List<Integer>) session.createSQLQuery("Select wert from Spieler_hand where spieler_s_id=" +s_id).list();

        } catch (PersistenceException e) {
            throw new DaoFindException(e.toString());
        }

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
      
    public Spieler findAktuellerSpieler() throws DaoFindException {
        Session session = (Session) entityManager.getDelegate();
        Spieler aktuellerSpieler;

        try {
            aktuellerSpieler = (Spieler) session.createSQLQuery("Select spieler from spieler where spieler_dran = true");
        } catch (PersistenceException e) {
            throw new DaoFindException(e.toString());
        }

        return aktuellerSpieler;

    }

    public void updateHatMauGerufen(boolean status, int s_id) throws DaoUpdateException {
        Session session = (Session) entityManager.getDelegate();
        try {
            session.createSQLQuery("Update Spieler set maugerufen ="+String.valueOf(status)+" where s_id="+s_id).executeUpdate();
        } catch (PersistenceException e) {
            throw new DaoUpdateException(e.toString());
        }
        //session.createSQLQuery("Update MauMauSpiel set runde ="+runde+" where spielid=0").executeUpdate();
    }


}
