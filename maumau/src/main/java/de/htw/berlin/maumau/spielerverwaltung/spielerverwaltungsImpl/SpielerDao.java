package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl;

import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoCreateException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoFindException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoRemoveException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoUpdateException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;

import java.util.List;

public interface SpielerDao {

    void create(Spieler spieler) throws DaoCreateException;

    void remove(Spieler spieler) throws DaoRemoveException;

    void update(Spieler spieler) throws DaoUpdateException;

    Spieler findBys_id(int s_id) throws DaoFindException;

    List<Spieler> findAll() throws DaoFindException;

    public List<Karte> findHand(int s_id) throws DaoFindException;

    public Spieler findAktuellerSpieler() throws DaoFindException;


    public int findAktuellerSpielerId();


    public void updateHatMauGerufen(boolean status, int s_id) throws DaoUpdateException;

    }
