package de.htw.berlin.maumau.errorHandling;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class KeineKarteException extends Exception {

    private static final String errorMessage = "Keine Karte wurde für die Aktion selektiert";

    public KeineKarteException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
