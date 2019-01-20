package de.htw.berlin.maumau.errorHandling;

public class FalscherInputException extends Throwable {

    private static final String DEFAULT_MESSAGE = "Der Input ist fehlerhaft. Bitte überprüfen.";

    public FalscherInputException(String message) {
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
