package de.htw.berlin.maumau.errorHandling.technischeExceptions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LeererStapelException extends Throwable {
    private static final String DEFAULT_MESSAGE = "Der Stapel ist leer";

    private Log log = LogFactory.getLog(LeererStapelException.class);

    public LeererStapelException(String message) {
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
