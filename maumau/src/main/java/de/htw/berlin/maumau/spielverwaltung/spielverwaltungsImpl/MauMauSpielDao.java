package de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl;

import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoCreateException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoFindException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoRemoveException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.DaoUpdateException;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartentyp;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;

import java.util.List;

public interface MauMauSpielDao {

    void create(MauMauSpiel spiel) throws DaoCreateException;

    void remove(MauMauSpiel spiel) throws DaoRemoveException;

    void update(MauMauSpiel spiel) throws DaoUpdateException;

    List<Spieler> findSpielerlist() throws DaoFindException;

    List<Karte> findKartenstapel() throws DaoFindException;

    List<Karte> findAblagestapel() throws DaoFindException;

    void updateRunde(int runde) throws DaoUpdateException;

    MauMauSpiel findSpiel() throws DaoFindException;

    void updateAssAktiv(boolean status) throws DaoUpdateException;

    boolean findAssAktivStatus() throws DaoFindException;

    void updateSiebenAktiv(boolean status) throws DaoUpdateException;

    boolean findSiebenAktivStatus() throws DaoFindException;

    void updateanzahlSonderregelKartenZiehen(int anzahl) throws DaoUpdateException;

    int findAnzahlSonderregelKartenZiehen() throws DaoFindException;

    public Kartentyp findAktuellerWunschtyp() throws DaoFindException;

    public void updateAktuellerWunschtyp(Kartentyp wunschtyp) throws DaoUpdateException;

}
