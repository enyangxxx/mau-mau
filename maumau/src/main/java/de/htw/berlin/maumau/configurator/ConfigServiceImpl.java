package de.htw.berlin.maumau.configurator;

import ch.aplu.util.Console;
import de.htw.berlin.maumau.controller.Controller;
import de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions.KeinSpielerException;
import de.htw.berlin.maumau.errorHandling.technischeExceptions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */


public class ConfigServiceImpl {

    public static final ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

    private static Controller controller = (Controller) context.getBean("controller");
    public static final String PROPERTY_FILENAME = "application.properties";
    public static final String PROPERTY_FONTSIZE = "fontsize";

    /**
     * Initialisiert eine Konsole, auf der das MauMau spiel gespielt werden kann.
     * Ruft die Methode Spielerliste Befuellen des Controllers auf.
     * Startet das Spiel mit run.
     *
     * @param args
     * @throws IOException                - fehlerhafte Kommunikation mit der Property-Datei
     * @throws LeererStapelException      - Wenn ein leerer Stapel nicht leer sein darf
     * @throws DaoCreateException         - beim fehlerhaften Erstellen in der Dao-Klasse
     * @throws KeinSpielerException       - wenn kein Spieler vorhanden ist
     * @throws DaoUpdateException         - beim fehlerhaften Updaten in der Dao-Klasse
     * @throws DaoFindException           - beim fehlerhaften Lesen in der Dao-Klasse
     * @throws KarteNichtGezogenException - Wenn Karte auf fehlerhafter Weise nicht gezogen werden konnte
     */

    public static void main(String[] args) throws IOException, DaoCreateException, DaoFindException, LeererStapelException, KarteNichtGezogenException, KeinSpielerException, DaoUpdateException {

        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + PROPERTY_FILENAME;
        Properties property = new Properties();
        property.load(new FileInputStream(appConfigPath));

        Console.init(new Font(null, Font.BOLD, Integer.valueOf(property.getProperty(PROPERTY_FONTSIZE))));

        controller.updateViewSpielerlisteBefuellenComputer();
        controller.updateViewSpielerlisteBefuellenMenschlich();

        controller.run();
    }


}
