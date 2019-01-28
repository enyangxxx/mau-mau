package de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class InkorrekterStapelException extends NullPointerException {

    private static final String DEFAULT_MESSAGE = "Unerwarteter Fehler des Stapels.";
    private Log log = LogFactory.getLog(InkorrekterStapelException.class);

    public InkorrekterStapelException(String message) {
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
