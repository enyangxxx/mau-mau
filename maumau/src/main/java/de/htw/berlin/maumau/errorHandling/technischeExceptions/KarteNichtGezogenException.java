package de.htw.berlin.maumau.errorHandling.technischeExceptions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class KarteNichtGezogenException extends Exception {

    private static final String DEFAULT_MESSAGE = "Karte konnte nicht gezogen werden";

    private Log log = LogFactory.getLog(KarteNichtGezogenException.class);

    public KarteNichtGezogenException(String message) {
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
