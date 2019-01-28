package de.htw.berlin.maumau.errorHandling.technischeExceptions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DaoCreateException extends Throwable {

    private static final String DEFAULT_MESSAGE = "Create konnte nicht erfolgreich vollzogen werden";

    private Log log = LogFactory.getLog(DaoCreateException.class);

    public DaoCreateException(String message) {
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

