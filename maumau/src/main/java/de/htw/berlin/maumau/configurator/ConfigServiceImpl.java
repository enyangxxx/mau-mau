package de.htw.berlin.maumau.configurator;

import ch.aplu.util.Console;
import de.htw.berlin.maumau.controller.Controller;
import de.htw.berlin.maumau.errorHandling.IdDuplikatException;
import de.htw.berlin.maumau.errorHandling.KeinWunschtypException;
import de.htw.berlin.maumau.errorHandling.KeineKarteException;
import de.htw.berlin.maumau.errorHandling.KeineSpielerException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.awt.*;
/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */


public class ConfigServiceImpl {

    public static final ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

    private static Controller controller;

    /**
     * Initialisiert eine Konsole, auf der das MauMau spiel gespielt werden kann.
     * Ruft die Methode Spielerliste Befuellen des Controllers auf.
     * Startet das Spiel mit run.
     * @param args
     * @throws KeineSpielerException - falls keine Spieler vorhanden sind
     * @throws IdDuplikatException - Wenn eine ID doppelt vergeben wird
     * @throws KeineKarteException - Wenn Keine Karte selektiert wurde
     * @throws KeinWunschtypException - Wenn kein Wunschtyp gesetzt wurde
     */
    public static void main(String[] args) throws KeineSpielerException, Exception {
        Console.init(new Font(null, Font.BOLD,30));
        controller = new Controller();

        // controller get bean holen

        controller.updateViewSpielerlisteBefuellen();

        // run in den controller verschieben
        run();

        // enum package auflösen und in die exports der jeweiligen komponenten verschieben
    }


    /**
     * Realisiert das MauMau spiel innerhalb einer Schleife, solange bis ein Spieler gewonnen hat.
     * @throws KeineSpielerException - falls keine Spieler vorhanden sind
     * @throws IdDuplikatException - Wenn eine ID doppelt vergeben wird
     * @throws KeineKarteException - Wenn Keine Karte selektiert wurde
     * @throws KeinWunschtypException - Wenn kein Wunschtyp gesetzt wurde
     */
    public static void run() throws KeineSpielerException, Exception {

        while(controller.checkNeueRundeStarten()) {
            controller.updateViewSpielStarten();
            while (!controller.checkSpielIstFertig()) {
                controller.updateViewNaechsterSpielzugStarten();
                controller.updateViewSpielzugDurchfuehren();
            }
            controller.updateViewMinuspunkte();
        }
        System.exit(0);
    }
}
