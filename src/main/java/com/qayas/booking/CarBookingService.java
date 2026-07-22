package com.qayas.booking;

import com.qayas.Exceptions.*;
import com.qayas.car.Car;
import com.qayas.car.CarDao;
import com.qayas.user.User;
import com.qayas.user.UserDao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class CarBookingService {
    private final UserDao userDao;
    private final CarDao carDao;
    private final CarBookingDao carBookingDao;

    public CarBookingService(UserDao userDao, CarDao carDao, CarBookingDao carBookingDao) {
        this.userDao = userDao;
        this.carDao = carDao;
        this.carBookingDao = carBookingDao;
    }

    public CarBooking bookCar(UUID userId, UUID carId, LocalDate startDate, LocalDate endDate) {
        User user = userDao.findById(userId);

        if (user == null)
            throw new UserNotFoundException(userId);

        Car car = carDao.findById(carId);

        if (car == null)
            throw new CarNotFoundException(carId);

        if (startDate.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Start date cannot be in the past.");

        if (!endDate.isAfter(startDate))
            throw new IllegalArgumentException("End date must be after start date.");

        CarBooking[] bookings = carBookingDao.findAll();

        if (bookings == null)
            throw new NoBookingsException("No bookings found.");

        for (CarBooking booking : bookings) {
            if (booking.getBookingStatus() == BookingStatus.ACTIVE && booking.getCar().getId() == carId)
                throw new CarAlreadyBookedException("Car already booked by another user.");
        }

        long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate);

        BigDecimal totalPrice = car.getRentalPricePerDay().multiply(BigDecimal.valueOf(numberOfDays));

        CarBooking booking = new CarBooking(UUID.fromString(""), user, car, startDate, endDate, totalPrice, BookingStatus.ACTIVE, LocalDateTime.now());

        return carBookingDao.save(booking);
    }

    public CarBooking cancelBooking(UUID bookingId) {
        CarBooking carBooking = carBookingDao.findById(bookingId);

        if (carBooking == null)
            throw new BookingNotFoundException(bookingId);

        if (carBooking.getBookingStatus() == BookingStatus.CANCELED)
            throw new IllegalStateException("Booking with Id " + bookingId + " is already cancelled.");

        carBooking.setBookingStatus(BookingStatus.CANCELED);

        return carBookingDao.updateBooking(carBooking);
    }

    public CarBooking[] viewUserBookings(UUID userId) {
        User user = userDao.findById(userId);

        if (user == null) {
            throw new UserNotFoundException(userId);
        }

        return carBookingDao.findByUserId(userId);
    }

    public CarBooking[] viewAllBookings() {
        return carBookingDao.findAll();
    }
}
