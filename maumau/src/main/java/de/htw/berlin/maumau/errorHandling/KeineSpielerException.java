package de.htw.berlin.maumau.errorHandling;

public class KeineSpielerException extends Exception{

    public static final String errorMessage = "Kein Spieler wurde für die Aktion selektiert";

    public KeineSpielerException(String message){
        super(message);
    }

    @Override
    public String getMessage(){
        return errorMessage;
    }
}
