package de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LeereInitialeSpielerlisteException extends Exception {

    private static final String DEFAULT_MESSAGE = "Die Spielerliste darf nicht leer sein";

    private Log log = LogFactory.getLog(LeereInitialeSpielerlisteException.class);

    public LeereInitialeSpielerlisteException(String message) {
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
