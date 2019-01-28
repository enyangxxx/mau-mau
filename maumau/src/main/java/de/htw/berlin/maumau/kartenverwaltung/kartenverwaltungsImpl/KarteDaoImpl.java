package de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsImpl;

import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoCreateException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoRemoveException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoUpdateException;
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


    public void create(Karte karte) throws DaoCreateException {
        try {
            entityManager.persist(karte);
        } catch (PersistenceException e) {
            throw new DaoCreateException(e.toString());
        }
    }

    public void createKartenstapel(List<Karte> kartenstapel) throws DaoCreateException {
        try {
            entityManager.persist(kartenstapel);
        } catch (PersistenceException e) {
            throw new DaoCreateException(e.toString());
        }
    }

    public void remove(Karte karte) throws DaoRemoveException {
        try {
            entityManager.remove(karte);
        } catch (PersistenceException e) {
            throw new DaoRemoveException(e.toString());
        }
    }

    public void update(Karte karte) throws DaoUpdateException {
        try {
            entityManager.merge(karte);
        } catch (PersistenceException e) {
            throw new DaoUpdateException(e.toString());
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
