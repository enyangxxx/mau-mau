package de.htw.berlin.maumau.errorHandling.technischeExceptions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class DaoUpdateException extends Exception {

    private static final String DEFAULT_MESSAGE = "Update konnte nicht vollgezogen werden";

    private Log log = LogFactory.getLog(DaoUpdateException.class);

    public DaoUpdateException(String message) {
        super(bestimmeMessage(message));
        log.error(this.toString());
    }

    /**
     * Die Fehlermeldung wird bestimmt
     *
     * @param message - eigene Fehlermeldung
     * @return message - Default oder eigene Fehlermeldung
     */
    public static String bestimmeMessage(String message) {
        if (message.isEmpty()) {
            return DEFAULT_MESSAGE;
        } else {
            return message;
        }
    }
}
