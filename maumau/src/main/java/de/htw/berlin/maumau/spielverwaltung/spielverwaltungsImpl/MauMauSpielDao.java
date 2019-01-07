package de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl;

import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

public interface MauMauSpielDao {

    void create(MauMauSpiel spiel) throws Exception;

    void remove(MauMauSpiel spiel) throws Exception;

    void update(MauMauSpiel spiel) throws Exception;

    void insert_update(MauMauSpiel spiel) throws Exception;

    MauMauSpiel findById(int spielId);

}
