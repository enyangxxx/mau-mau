package de.htw.berlin.maumau.errorHandling;

public class StapelInhaltException extends Throwable {

    private static final String DEFAULT_MESSAGE = "Unerwarteter Fehler des Stapels.";

    public StapelInhaltException(String message) {
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
