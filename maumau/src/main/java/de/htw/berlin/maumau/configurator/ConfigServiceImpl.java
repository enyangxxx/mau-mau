package de.htw.berlin.maumau.configurator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ConfigServiceImpl {

    public static final ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

    public static void main(String[] args) {
        System.out.println("Er ist in die main reingegangen.");
        //ISpielverwaltung spielverwaltung = (ISpielverwaltung) context.getBean("spielverwaltungimpl");
        //spielverwaltung.callExampleMethod();
    }
}
