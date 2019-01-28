package de.htw.berlin.maumau.errorHandling.technischeExceptions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DaoRemoveException extends Throwable {
    private static final String DEFAULT_MESSAGE = "Remove konnte nicht vollgezogen werden";

    private Log log = LogFactory.getLog(DaoRemoveException.class);

    public DaoRemoveException(String message) {
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
