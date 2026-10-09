package com.qayas.booking;

import java.util.List;
import java.util.UUID;

public interface CarBookingDao {
    List<CarBooking> getBookings();
    CarBooking getBookingById(UUID bookingId);
    List<CarBooking> getBookingByUserId(UUID userId);
    List<CarBooking> getActiveBookings();
    CarBooking saveBooking(CarBooking booking);
    CarBooking updateBooking(CarBooking booking);

}