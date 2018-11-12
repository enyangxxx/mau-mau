package de.htw.berlin.maumau.errorHandling;

/**
 * @author Enyang Wang, Steve Engel, Theo Radig
 */
public class IdDuplikatException extends Exception {

    private static final String errorMessage = "Die ID ist bereits vergeben";

    public IdDuplikatException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
