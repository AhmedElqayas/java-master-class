package com.qayas.booking;

import com.qayas.Exceptions.BookingNotFoundException;

import java.io.*;
import java.util.UUID;

public class CarBookingFileDataAccessService implements CarBookingDao {
    private final String filePath;

    public CarBookingFileDataAccessService(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CarBooking[] getBookings() {
        return getBookingsFromFile();
    }

    @Override
    public CarBooking getBookingById(UUID bookingId) {
        for (CarBooking booking : getBookingsFromFile()) {
            if (booking.getId().equals(bookingId))
                return booking;
        }

        throw new BookingNotFoundException(bookingId);
    }

    @Override
    public CarBooking[] getBookingByUserId(UUID userId) {
        CarBooking[] carBookings = getBookingsFromFile();

        int matchingBookings = 0;
        for (CarBooking booking : carBookings) {
            if (booking.getUser().getId().equals(userId))
                matchingBookings++;
        }

        CarBooking[] userBookings = new CarBooking[matchingBookings];
        int index = 0;

        for (CarBooking carBooking : carBookings) {
            if (carBooking.getUser().getId().equals(userId)) {
                userBookings[index++] = carBooking;
            }
        }
        return userBookings;
    }

    @Override
    public CarBooking[] getActiveBookings() {
        CarBooking[] carBookings = getBookingsFromFile();

        int matchingBookings = 0;
        for (CarBooking booking : getBookings()) {
            if (booking.getBookingStatus() == BookingStatus.ACTIVE)
                matchingBookings++;
        }

        CarBooking[] activeBookings = new CarBooking[matchingBookings];
        int index = 0;

        for (CarBooking carBooking : carBookings) {
            if (carBooking.getBookingStatus() == BookingStatus.ACTIVE) {
                activeBookings[index++] = carBooking;
            }
        }
        return activeBookings;
    }

    @Override
    public CarBooking saveBooking(CarBooking booking) {
        saveBookingToFile(booking);

        return booking;
    }

    @Override
    public CarBooking updateBooking(CarBooking booking) {
        CarBooking[] carBookings = getBookingsFromFile();

        for (int i = 0; i < carBookings.length; i++) {
            if (carBookings[i].getId().equals(booking.getId())) {
                carBookings[i] = booking;
                saveBookingsToFile(carBookings);
                return booking;
            }
        }

        throw new BookingNotFoundException(booking.getId());
    }
    
    private void saveBookingToFile(CarBooking booking) {
        CarBooking[] existingBookings = getBookingsFromFile();
        CarBooking[] updatedBookings = new CarBooking[existingBookings.length + 1];

        System.arraycopy(existingBookings, 0, updatedBookings, 0, existingBookings.length);

        updatedBookings[existingBookings.length] = booking;
        saveBookingsToFile(updatedBookings);
    }

    private void saveBookingsToFile(CarBooking[] bookings) {
        //Serialization
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(bookings);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private CarBooking[] getBookingsFromFile() {
        //Deserialization
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (CarBooking[]) in.readObject();
        } catch (EOFException e) {
            return new CarBooking[0];
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}