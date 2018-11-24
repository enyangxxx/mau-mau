package de.htw.berlin.maumau.errorHandling;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class KeineSpielerException extends Throwable{

    public static final String errorMessage = "Kein Spieler wurde für die Aktion selektiert";

    public KeineSpielerException(String message){
        super(message);
    }

    @Override
    public String getMessage(){
        return errorMessage;
    }
}
