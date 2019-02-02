package de.htw.berlin.maumau.errorHandling.inhaltlicheExceptions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class IdNichtVorhandenException extends Exception {

    private static final String DEFAULT_MESSAGE = "Die Id ist nicht vorhanden";
    private Log log = LogFactory.getLog(IdNichtVorhandenException.class);

    public IdNichtVorhandenException(String message) {
        super(bestimmeMessage(message));
        log.error(this.toString());
    }

    /**
     * Die Fehlermeldung wird bestimmt
     *
     * @param message - eigene Fehlermeldung
     * @return message - Default oder eigene Fehlermeldung
     */
    public static String bestimmeMessage(String message){
        if(message.isEmpty()){
            return DEFAULT_MESSAGE;
        }else{
            return message;
        }
    }
}
