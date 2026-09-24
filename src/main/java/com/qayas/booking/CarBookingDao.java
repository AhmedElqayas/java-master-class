package com.qayas.booking;

import java.util.UUID;

public interface CarBookingDao {
    CarBooking[] getBookings();
    CarBooking getBookingById(UUID bookingId);
    CarBooking[] getBookingByUserId(UUID userId);
    CarBooking[] getActiveBookings();
    CarBooking saveBooking(CarBooking booking);
    CarBooking updateBooking(CarBooking booking);

}