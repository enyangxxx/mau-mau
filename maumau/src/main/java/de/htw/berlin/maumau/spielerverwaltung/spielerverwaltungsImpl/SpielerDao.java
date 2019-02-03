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

    List<Karte> findHand(int s_id) throws DaoFindException;

    int findAktuellerSpielerId();

    void updateHatMauGerufen(boolean status, int s_id) throws DaoUpdateException;

    void updateDran(boolean status, int s_id) throws DaoUpdateException;

    boolean isVirtuellerSpieler(int s_id) throws DaoUpdateException;

    public boolean findIsIstComputer(int s_id) throws DaoFindException;

    }
