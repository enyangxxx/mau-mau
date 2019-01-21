package de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsImpl;

import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartentyp;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartenwert;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;


import java.util.List;

public class KarteDaoImpl implements KarteDao{

    @PersistenceContext
    private EntityManager entityManager;

    public KarteDaoImpl() {
        super();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public void create(Karte karte) throws Exception {
        try {
            entityManager.persist(karte);
        } catch (PersistenceException e) {
            //throw new DaoException(e);
            throw new Exception(e.getMessage());
        }
    }

    public void createKartenstapel(List<Karte> kartenstapel) throws Exception {
        try {
            entityManager.persist(kartenstapel);
        } catch (PersistenceException e) {
            //throw new DaoException(e);
            throw new Exception(e.getMessage());
        }
    }

    public void remove(Karte karte) throws Exception {
        try {
            entityManager.remove(karte);
        } catch (PersistenceException e) {
            //throw new DaoException(e);
            throw new Exception(e.getMessage());
        }
    }

    public void update(Karte karte) throws Exception {
        try {
            entityManager.merge(karte);
        } catch (PersistenceException e) {
            //throw new DaoException(e);
            throw new Exception(e.getMessage());
        }
    }

    public void insert_update(Karte karte) throws Exception {
        try {
            if(entityManager.contains(karte)){
                entityManager.merge(karte);
            }
            else{
               entityManager.persist(karte);
            }
        } catch (PersistenceException e) {
            //throw new DaoException(e);
            throw new Exception(e.getMessage());
        }
    }


    public Karte findByTypAndWert(Kartentyp typ, Kartenwert wert) {

        return null;
    }

    public List<Karte> findAllByTyp(Kartentyp typ) {
        return null;
    }

    public List<Karte> findAllByWert(Kartenwert wert) {
        return null;
    }
}
