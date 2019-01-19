package de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsImpl;

import de.htw.berlin.maumau.enumeration.Kartentyp;
import de.htw.berlin.maumau.enumeration.Kartenwert;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;

import java.util.List;

public interface KarteDao {

    void create(Karte karte) throws Exception;

    void createKartenstapel(List<Karte> kartenstapel) throws Exception;

    void remove(Karte karte) throws Exception;

    void update(Karte karte) throws Exception;

    void insert_update(Karte karte) throws Exception;

    Karte findByTypAndWert(Kartentyp typ, Kartenwert wert);

    List<Karte> findAllByTyp(Kartentyp typ);

    List<Karte> findAllByWert(Kartenwert wert);

}
