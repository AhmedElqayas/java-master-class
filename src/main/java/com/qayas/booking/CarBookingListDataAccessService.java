package com.qayas.booking;

import com.qayas.Exceptions.BookingNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CarBookingListDataAccessService implements CarBookingDao {
    private static final List<CarBooking> carBookings = new ArrayList<>();

    public List<CarBooking> getBookings() {
        if (carBookings.isEmpty())
            return Collections.emptyList();
        return carBookings;
    }

    public CarBooking getBookingById(UUID bookingId) {
        for (CarBooking booking : getBookings()) {
            if (booking.getId().equals(bookingId))
                return booking;
        }
        return null;
    }

    public List<CarBooking> getBookingByUserId(UUID userId) {
        List<CarBooking> userBookings = new ArrayList<>();
        for (CarBooking booking : getBookings()) {
            if (booking.getUser().getId().equals(userId))
                userBookings.add(booking);
        }
        return userBookings;
    }

    public List<CarBooking> getActiveBookings() {
        List<CarBooking> activeBookings = new ArrayList<>();
        for (CarBooking booking : getBookings()) {
            if (booking.getBookingStatus() == BookingStatus.ACTIVE)
                activeBookings.add(booking);
        }
        return activeBookings;
    }

    public CarBooking saveBooking(CarBooking booking) {
        carBookings.add(booking);
        return booking;
    }

    public CarBooking updateBooking(CarBooking updatedBooking) {
        for (CarBooking booking : getBookings()) {
            if (booking.getId().equals(updatedBooking.getId())) {
                booking = updatedBooking;
                return booking;
            }
        }
        throw new BookingNotFoundException(updatedBooking.getId());
    }
}
