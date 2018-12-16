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


public class ConfigServiceImpl {

    public static final ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

    static Controller controller;


    public static void main(String[] args) throws KeineSpielerException, IdDuplikatException, KeineKarteException, KeinWunschtypException {
        Console.init(new Font(null, Font.BOLD,30));
        controller = new Controller();

        controller.updateViewSpielerlisteBefuellen();

        run();
    }


    public static void run() throws KeineSpielerException, IdDuplikatException, KeineKarteException, KeinWunschtypException {

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
