package de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IdNichtVorhandenException extends Throwable {

    private static final String DEFAULT_MESSAGE = "Die Id ist nicht vorhanden";
    private Log log = LogFactory.getLog(IdNichtVorhandenException.class);

    public IdNichtVorhandenException(String message) {
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
