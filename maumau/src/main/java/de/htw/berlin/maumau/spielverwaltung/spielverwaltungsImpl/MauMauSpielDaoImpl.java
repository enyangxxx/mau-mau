package de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl;

import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceException;

public class MauMauSpielDaoImpl implements MauMauSpielDao {

    private EntityManager entityManager;

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

    public MauMauSpiel findById(int spielId)
    {
        return entityManager.find(MauMauSpiel.class,spielId);
    }
}
