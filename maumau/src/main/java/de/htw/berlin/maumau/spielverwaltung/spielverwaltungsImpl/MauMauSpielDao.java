package de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl;

import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

import java.util.List;

public interface MauMauSpielDao {

    void create(MauMauSpiel spiel) throws Exception;

    void remove(MauMauSpiel spiel) throws Exception;

    void update(MauMauSpiel spiel) throws Exception;

    void insert_update(MauMauSpiel spiel) throws Exception;

    List<Spieler> findSpielerlist();

    List<Karte> findKartenstapel();

    List<Karte> findAblagestapel();

    MauMauSpiel findById(int spielId);

}
