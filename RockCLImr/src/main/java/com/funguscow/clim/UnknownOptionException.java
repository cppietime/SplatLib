package com.funguscow.clim;

/**
 * Exception thrown when encountering an unknown option
 */
public class UnknownOptionException extends RuntimeException {

    public UnknownOptionException(String message) {
        super(message);
    }

}
