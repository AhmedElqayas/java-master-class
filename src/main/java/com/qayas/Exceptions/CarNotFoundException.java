package com.qayas.Exceptions;

import java.util.UUID;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(UUID carId) {
        super("Car with Id: " + carId + " was not found.");
    }
}
