package de.htw.berlin.maumau.configurator;

import ch.aplu.util.Console;
import de.htw.berlin.maumau.controller.Controller;
import de.htw.berlin.maumau.errorHandling.IdDuplikatException;
import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.KeinSpielerException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.KarteNichtGezogenException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.LeererStapelException;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl.SpielerDao;
import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.awt.*;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */


public class ConfigServiceImpl {

    public static final ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

    private static Controller controller = (Controller) context.getBean("controller");
    //private static Controller controller;



    /**
     * Initialisiert eine Konsole, auf der das MauMau spiel gespielt werden kann.
     * Ruft die Methode Spielerliste Befuellen des Controllers auf.
     * Startet das Spiel mit run.
     *
     * @param args
     * @throws KeinSpielerException  - falls keine Spieler vorhanden sind
     * @throws IdDuplikatException    - Wenn eine ID doppelt vergeben wird
     */
    public static void main(String[] args) throws KeinSpielerException, Exception, KarteNichtGezogenException, LeererStapelException {
        Console.init(new Font(null, Font.BOLD,20));

        //controller = new Controller();


        // controller get bean holen


        controller.updateViewSpielerlisteBefuellen();

        // run in den controller verschieben
        controller.run();

        // enum package auflösen und in die exports der jeweiligen komponenten verschieben
    }



}
