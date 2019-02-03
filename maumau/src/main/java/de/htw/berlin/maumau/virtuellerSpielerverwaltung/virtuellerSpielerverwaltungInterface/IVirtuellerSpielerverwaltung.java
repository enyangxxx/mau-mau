package de.htw.berlin.maumau.virtuellerSpielerverwaltung.virtuellerSpielerverwaltungInterface;

import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoFindException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoUpdateException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.KarteNichtGezogenException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.LeererStapelException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

import java.util.List;

public interface IVirtuellerSpielerverwaltung {

    void spielzugDurchfuehren() throws DaoFindException, LeererStapelException, KarteNichtGezogenException, DaoUpdateException;

    boolean karteLegen(MauMauSpiel spiel, Karte letzteKarte, List<Karte> hand) throws DaoUpdateException, DaoFindException, LeererStapelException, KarteNichtGezogenException;

    void wunschtypFestlegen() throws DaoFindException, DaoUpdateException;


}
