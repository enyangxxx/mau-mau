package de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FalscherInputException extends Throwable {

    private static final String DEFAULT_MESSAGE = "Der Input ist fehlerhaft. Bitte überprüfen.";

    private Log log = LogFactory.getLog(FalscherInputException.class);

    public FalscherInputException(String message) {
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
