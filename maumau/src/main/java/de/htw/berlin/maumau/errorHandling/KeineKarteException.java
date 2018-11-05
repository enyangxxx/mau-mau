package de.htw.berlin.maumau.errorHandling;

public class KeineKarteException extends Exception{

    public static final String errorMessage = "Keine Karte wurde für die Aktion selektiert";

    public KeineKarteException(String message){
        super(message);
    }

    @Override
    public String getMessage(){
        return errorMessage;
    }
}
