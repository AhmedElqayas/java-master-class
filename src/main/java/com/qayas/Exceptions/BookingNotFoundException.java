package com.qayas.Exceptions;

import java.util.UUID;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(UUID bookingId) {
        super("Booking with ID " + bookingId + " was not found.");
    }
}
