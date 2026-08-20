package com.qayas.Exceptions;

public class NoBookingsException extends RuntimeException {
    public NoBookingsException(String message) {
        super(message);
    }
}
