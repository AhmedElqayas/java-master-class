package com.qayas.booking;

import com.qayas.Exceptions.BookingNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CarBookingFileDataAccessService implements CarBookingDao {
    private final String filePath;

    public CarBookingFileDataAccessService(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<CarBooking> getBookings() {
        return getBookingsFromFile();
    }

    @Override
    public CarBooking getBookingById(UUID bookingId) {
        return getBookingsFromFile().stream()
                .filter(booking -> booking.getId().equals(bookingId))
                .findFirst()
                .orElseThrow(() -> new BookingNotFoundException(bookingId));
    }

    @Override
    public List<CarBooking> getBookingsByUserId(UUID userId) {
        return getBookingsFromFile().stream()
                .filter(booking -> booking.getUser().getId().equals(userId))
                .toList();
    }

    @Override
    public List<CarBooking> getActiveBookings() {
        return getBookingsFromFile().stream()
                .filter(booking -> booking.getBookingStatus() == BookingStatus.ACTIVE)
                .toList();
    }

    @Override
    public CarBooking saveBooking(CarBooking booking) {
        saveBookingToFile(booking);

        return booking;
    }

    @Override
    public CarBooking updateBooking(CarBooking updatedBooking) {
        List<CarBooking> carBookings = getBookingsFromFile();

        int index = IntStream.range(0, carBookings.size())
                .filter(i -> carBookings.get(i).getId().equals(updatedBooking.getId()))
                .findFirst()
                .orElseThrow(() -> new BookingNotFoundException(updatedBooking.getId()));

        carBookings.set(index, updatedBooking);

        saveBookingsToFile(carBookings);
        return updatedBooking;
    }

    private void saveBookingToFile(CarBooking booking) {
        List<CarBooking> bookings = getBookingsFromFile();
        bookings.add(booking);
        saveBookingsToFile(bookings);
    }

    private void saveBookingsToFile(List<CarBooking> bookings) {
        //Serialization
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(bookings);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<CarBooking> getBookingsFromFile() {
        //Deserialization
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<CarBooking>) in.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}