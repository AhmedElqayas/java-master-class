package com.qayas.Exceptions;

public class CarAlreadyBookedException extends RuntimeException {
    public CarAlreadyBookedException(String message) {
        super(message);
    }
}
