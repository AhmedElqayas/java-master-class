package com.qayas.booking;

import com.qayas.Exceptions.BookingNotFoundException;

import java.util.UUID;

public class CarBookingArrayDataAccessService implements CarBookingDao {
    private static final CarBooking[] carBookings;

    static {
        carBookings = new CarBooking[100];
    }

    private static int bookingCount = 0;

    public CarBooking[] getBookings() {
        //I created another array to remove null records from the bookings and return only actual ones
        CarBooking[] result = new CarBooking[bookingCount];

        for (int i = 0; i < bookingCount; i++) {
            if (carBookings[i] == null)
                break;
            result[i] = carBookings[i];
        }
        return result;
    }

    public CarBooking getBookingById(UUID bookingId) {
        for (CarBooking booking : carBookings) {
            if (booking.getId().equals(bookingId))
                return booking;
        }
        return null;
    }

    public CarBooking[] getBookingByUserId(UUID userId) {
        int matchingBookings = 0;
        for (CarBooking booking : getBookings()) {
            if (booking.getUser().getId().equals(userId))
                matchingBookings++;
        }

        CarBooking[] userBookings = new CarBooking[matchingBookings];
        int index = 0;

        for (int i = 0; i < bookingCount; i++) {
            if (carBookings[i].getUser().getId().equals(userId)) {
                userBookings[index++] = carBookings[i];
            }
        }
        return userBookings;
    }

    public CarBooking[] getActiveBookings() {
        int matchingBookings = 0;
        for (CarBooking booking : getBookings()) {
            if (booking.getBookingStatus() == BookingStatus.ACTIVE)
                matchingBookings++;
        }

        CarBooking[] activeBookings = new CarBooking[matchingBookings];
        int index = 0;

        for (int i = 0; i < bookingCount; i++) {
            if (carBookings[i].getBookingStatus() == BookingStatus.ACTIVE) {
                activeBookings[index++] = carBookings[i];
            }
        }
        return activeBookings;
    }

    public CarBooking saveBooking(CarBooking booking) {
        if (bookingCount >= carBookings.length)
            throw new IllegalStateException("Booking storage is full.");

        carBookings[bookingCount++] = booking;

        return booking;
    }

    public CarBooking updateBooking(CarBooking booking) {
        for (int i = 0; i < bookingCount; i++) {
            if (carBookings[i].getId().equals(booking.getId())) {
                carBookings[i] = booking;
                return booking;
            }
        }
        throw new BookingNotFoundException(booking.getId());
    }
}
