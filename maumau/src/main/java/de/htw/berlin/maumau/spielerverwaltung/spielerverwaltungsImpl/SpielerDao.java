package de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl;

import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;

import java.util.List;

public interface SpielerDao {

    void create(Spieler spieler) throws Exception;

    void remove(Spieler spieler) throws Exception;

    void update(Spieler spieler) throws Exception;

    void insert_update(Spieler spieler) throws Exception;

    Spieler findBys_id(int s_id);

    List<Spieler> findAll();

    public List<Karte> findHand(int s_id);

    public Spieler findAktuellerSpieler();

    public void updateHatMauGerufen(boolean status, int s_id);
    }
