package com.funguscow.clim;

/**
 * Exception thrown when an argument is missing or present when it should not be
 */
public class InvalidArgumentException extends RuntimeException {

    public InvalidArgumentException(String message) {
        super(message);
    }

}
