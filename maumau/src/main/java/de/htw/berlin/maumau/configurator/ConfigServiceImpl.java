package de.htw.berlin.maumau.configurator;

import ch.aplu.util.Console;
import de.htw.berlin.maumau.controller.Controller;
import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.KeinSpielerException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.KarteNichtGezogenException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.LeererStapelException;
import de.htw.berlin.maumau.spielerverwaltung.spielerverwaltungsImpl.SpielerDao;
import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.awt.*;
import java.io.FileInputStream;
import java.util.Properties;

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
     */
    public static void main(String[] args) throws KeinSpielerException, Exception, KarteNichtGezogenException, LeererStapelException {


        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "application.properties";
        Properties property = new Properties();
        property.load(new FileInputStream(appConfigPath));

        Console.init(new Font(null, Font.BOLD, Integer.valueOf(property.getProperty("fontsize"))));

        //controller = new Controller();



        // controller get bean holen


        controller.updateViewSpielerlisteBefuellen();

        // run in den controller verschieben
        controller.run();

        // enum package auflösen und in die exports der jeweiligen komponenten verschieben
    }


}
