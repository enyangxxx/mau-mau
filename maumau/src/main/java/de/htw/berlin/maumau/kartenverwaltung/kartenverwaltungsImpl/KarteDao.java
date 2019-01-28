package de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsImpl;

import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoCreateException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoRemoveException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoUpdateException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartentyp;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartenwert;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;

import java.util.List;

public interface KarteDao {

    void create(Karte karte) throws DaoCreateException;

    void createKartenstapel(List<Karte> kartenstapel) throws DaoCreateException;

    void remove(Karte karte) throws DaoRemoveException;

    void update(Karte karte) throws DaoUpdateException;

    Karte findByTypAndWert(Kartentyp typ, Kartenwert wert);

    List<Karte> findAllByTyp(Kartentyp typ);

    List<Karte> findAllByWert(Kartenwert wert);

}
