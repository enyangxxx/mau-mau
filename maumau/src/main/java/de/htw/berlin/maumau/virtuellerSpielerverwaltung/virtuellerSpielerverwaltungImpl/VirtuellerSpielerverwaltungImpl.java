package de.htw.berlin.maumau.virtuellerSpielerverwaltung.virtuellerSpielerverwaltungImpl;

import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.KeinSpielerException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.*;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Karte;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartentyp;
import de.htw.berlin.maumau.kartenverwaltung.kartenverwaltungsInterface.Kartenwert;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl.SpielerDao;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.ISpielerverwaltung;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsInterface.Spieler;
import de.htw.berlin.maumau.spielregeln.spielregelnInterface.ISpielregeln;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsImpl.MauMauSpielDao;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.ISpielverwaltung;
import de.htw.berlin.maumau.spielverwaltung.spielverwaltungsInterface.MauMauSpiel;
import de.htw.berlin.maumau.virtuellerSpielerverwaltung.virtuellerSpielerverwaltungInterface.IVirtuellerSpielerverwaltung;

import java.util.List;
import java.util.Random;

public class VirtuellerSpielerverwaltungImpl implements IVirtuellerSpielerverwaltung {

    private MauMauSpielDao maumauspielDao;
    private ISpielregeln spielregeln;
    private ISpielverwaltung spielverwaltung;
    private ISpielerverwaltung spielerverwaltung;
    private SpielerDao spielerdao;


    public VirtuellerSpielerverwaltungImpl(final MauMauSpielDao maumauspielDaoimpl, final ISpielregeln spielregelnImpl, final ISpielverwaltung spielverwaltungImpl, final ISpielerverwaltung spielerverwaltungImpl, final SpielerDao spielerdaoimpl){
        this.maumauspielDao = maumauspielDaoimpl;
        this.spielregeln = spielregelnImpl;
        this.spielverwaltung = spielverwaltungImpl;
        this.spielerverwaltung = spielerverwaltungImpl;
        this.spielerdao = spielerdaoimpl;
    }


    @Override
    public void spielzugDurchfuehren() throws DaoFindException, LeererStapelException, KarteNichtGezogenException, DaoUpdateException {
        MauMauSpiel spiel = maumauspielDao.findSpiel();
        List<Karte> ablagestapel = maumauspielDao.findAblagestapel();
        Karte letzteKarte = ablagestapel.get(ablagestapel.size()-1);
        List<Karte> hand = spielerdao.findHand(spielerdao.findAktuellerSpielerId());

        if(!karteLegen(spiel, letzteKarte, hand)){
            if (spiel.isSonderregelSiebenAktiv()) {
                spielverwaltung.karteZiehenSonderregel();
            } else {
                spielverwaltung.karteZiehen();
            }
        }

    }

    @Override
    public boolean karteLegen(MauMauSpiel spiel, Karte letzteKarte, List<Karte> hand) throws DaoUpdateException, DaoFindException, LeererStapelException, KarteNichtGezogenException {

        boolean tmp = false;

        for(Karte karte : hand){
            if (((spielregeln.sonderregelEingehaltenSieben(karte, letzteKarte)) ||
                    (!spiel.isSonderregelSiebenAktiv())) && spielregeln.sonderregelEingehaltenBube(letzteKarte, letzteKarte)) {
                if (spiel.getAktuellerWunschtyp() != null) {
                    if(karteLegenWennWunschtypVorhanden(spiel, karte, hand)){
                        tmp = true;
                        break;
                    }
                } else {
                    if(karteLegenWennKeinWunschtypVorhanden(spiel, karte, letzteKarte, hand)){
                        tmp = true;
                        break;
                    }
                }
            }
        }
        return tmp;
    }


    public void wunschtypFestlegen() throws DaoFindException, DaoUpdateException {
        MauMauSpiel spiel = maumauspielDao.findSpiel();
        Random rand = new Random();
        int i = rand.nextInt((3-0)+1);
        spiel.setAktuellerWunschtyp(Kartentyp.getName(i));
        maumauspielDao.update(spiel);
    }

    private void checkSonderregelSieben(){

    }

    private boolean karteLegenWennWunschtypVorhanden(MauMauSpiel spiel, Karte karte, List<Karte> hand) throws DaoUpdateException, DaoFindException, LeererStapelException, KarteNichtGezogenException {
        if (spielregeln.istLegbar(karte, spiel.getAktuellerWunschtyp())) {
            if(hand.size()==2){
                spielerdao.updateHatMauGerufen(true, spielerdao.findAktuellerSpielerId());
            }
            spielverwaltung.karteVonHandAufStapelLegen(karte);
            spielverwaltung.regelwerkUmsetzen(karte, hand);
            spielerverwaltung.spielerWechseln();
            spiel.setAktuellerWunschtyp(null);
            return true;
        }
        return false;
    }

    private boolean karteLegenWennKeinWunschtypVorhanden(MauMauSpiel spiel, Karte karte, Karte letzteKarte, List<Karte> hand) throws DaoUpdateException, DaoFindException, LeererStapelException, KarteNichtGezogenException {
        boolean tmp = false;
        if (spielregeln.istLegbar(letzteKarte, karte)) {
            if(hand.size()==2){
                spielerdao.updateHatMauGerufen(true, spielerdao.findAktuellerSpielerId());
            }
            spielverwaltung.karteVonHandAufStapelLegen(karte);
            tmp = true;
            spielverwaltung.regelwerkUmsetzen(karte, hand);
            if (!(karte.getWert().equals(Kartenwert.BUBE))) {
                spielerverwaltung.spielerWechseln();
            }
            else{
                wunschtypFestlegen();
                spielerverwaltung.spielerWechseln();
            }
        }
        return tmp;
    }
}
