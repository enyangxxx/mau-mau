package de.htw.berlin.maumau.errorHandling;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class KeinWunschtypException extends Exception{

    public static final String errorMessage = "Keine Wunschtyp wurde festgelegt.";

    public KeinWunschtypException(String message){
        super(message);
    }

    @Override
    public String getMessage(){
        return errorMessage;
    }
}
