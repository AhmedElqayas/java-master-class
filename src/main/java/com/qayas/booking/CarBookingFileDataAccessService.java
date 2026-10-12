package com.qayas.booking;

import com.qayas.Exceptions.BookingNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        for (CarBooking booking : getBookingsFromFile()) {
            if (booking.getId().equals(bookingId))
                return booking;
        }

        throw new BookingNotFoundException(bookingId);
    }

    @Override
    public List<CarBooking> getBookingsByUserId(UUID userId) {
        List<CarBooking> carBookings = getBookingsFromFile();

        List<CarBooking> userBookings = new ArrayList<>();

        for (CarBooking booking : carBookings) {
            if (booking.getUser().getId().equals(userId))
                userBookings.add(booking);
        }

        return userBookings;
    }

    @Override
    public List<CarBooking>  getActiveBookings() {
        List<CarBooking> carBookings = getBookingsFromFile();

        List<CarBooking> activeBookings = new ArrayList<>();

        for (CarBooking booking : carBookings) {
            if (booking.getBookingStatus() == BookingStatus.ACTIVE)
                activeBookings.add(booking);
        }

        return activeBookings;
    }

    @Override
    public CarBooking saveBooking(CarBooking booking) {
        saveBookingToFile(booking);

        return booking;
    }

    @Override
    public CarBooking updateBooking(CarBooking updatedBooking) {
        List<CarBooking> carBookings = getBookingsFromFile();

        for (int i = 0; i < carBookings.size(); i++) {
            if (carBookings.get(i).getId().equals(updatedBooking.getId())) {
                carBookings.set(i, updatedBooking);
                saveBookingsToFile(carBookings);
                return updatedBooking;
            }
        }

        throw new BookingNotFoundException(updatedBooking.getId());
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