package com.qayas.booking;

import com.qayas.Exceptions.BookingNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CarBookingListDataAccessService implements CarBookingDao {
    private static final List<CarBooking> carBookings = new ArrayList<>();

    public List<CarBooking> getBookings() {
        return carBookings;
    }

    public CarBooking getBookingById(UUID bookingId) {
        return carBookings.stream()
                .filter(booking -> booking.getId().equals(bookingId))
                .findFirst()
                .orElseThrow(() -> new BookingNotFoundException(bookingId));
    }

    public List<CarBooking> getBookingsByUserId(UUID userId) {
        return carBookings.stream()
                .filter(booking -> booking.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<CarBooking> getActiveBookings() {
        return carBookings.stream()
                .filter(booking -> booking.getBookingStatus() == BookingStatus.ACTIVE)
                .collect(Collectors.toList());
    }

    public CarBooking saveBooking(CarBooking booking) {
        carBookings.add(booking);
        return booking;
    }

    public CarBooking updateBooking(CarBooking updatedBooking) {
        IntStream.range(0, carBookings.size())
                .filter(i -> carBookings.get(i).getId().equals(updatedBooking.getId()))
                .findFirst()
                .ifPresentOrElse(
                        i -> carBookings.set(i, updatedBooking),
                        () -> {
                            throw new BookingNotFoundException(updatedBooking.getId());
                        }
                );
        return updatedBooking;
    }
}
