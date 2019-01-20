package de.htw.berlin.maumau.errorHandling;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class KeinWunschtypException extends Exception{

    private static final String DEFAULT_MESSAGE = "Kein Wunschtyp gesetzt";

    public KeinWunschtypException(String message) {
        super(returnMessage(message));
    }

    public static String returnMessage(String message){
        if(message.isEmpty()){
            return DEFAULT_MESSAGE;
        }else{
            return message;
        }

    }
}
