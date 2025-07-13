package dev.bakr.library_manager.exceptions;

public class InvalidBookStatusException extends RuntimeException {
    public InvalidBookStatusException(String message) {
        super(message);
    }
}
