package de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class KeinSpielerException extends Throwable{

    private static final String DEFAULT_MESSAGE = "Kein Spieler konnte gefunden werden!";

    private Log log = LogFactory.getLog(KeinSpielerException.class);

    public KeinSpielerException(String message) {
        super(bestimmeMessage(message));
        log.error(this.toString());
    }

    public static String bestimmeMessage(String message){
        if(message.isEmpty()){
            return DEFAULT_MESSAGE;
        }else{
            return message;
        }
    }
}
