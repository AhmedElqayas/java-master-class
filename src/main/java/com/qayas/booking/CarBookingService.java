package com.qayas.booking;

import com.qayas.Exceptions.*;
import com.qayas.car.Car;
import com.qayas.car.CarDao;
import com.qayas.user.User;
import com.qayas.user.UserArrayDataAccessService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class CarBookingService {
    private final UserArrayDataAccessService userDao = new UserArrayDataAccessService();
    private final CarBookingDao carBookingDao;
    private final CarDao carDao;
    public CarBookingService(CarBookingDao carBookingDao, CarDao carDao) {
        this.carBookingDao = carBookingDao;
        this.carDao = carDao;
    }

    public CarBooking bookCar(UUID userId, UUID carId, LocalDate startDate, LocalDate endDate) {
        User user = userDao.getUserById(userId);

        if (user == null)
            throw new UserNotFoundException(userId);

        Car car = carDao.getCarById(carId);

        if (car == null)
            throw new CarNotFoundException(carId);

        if (startDate.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Start date cannot be in the past.");

        if (!endDate.isAfter(startDate))
            throw new IllegalArgumentException("End date must be after start date.");

        CarBooking[] bookings = carBookingDao.getBookings();

        for (CarBooking booking : bookings) {
            if (booking.getBookingStatus() == BookingStatus.ACTIVE && booking.getCar().getId().equals(carId))
                throw new CarAlreadyBookedException("Car already booked by another user.");
        }

        long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate);

        BigDecimal totalPrice = car.getRentalPricePerDay().multiply(BigDecimal.valueOf(numberOfDays));

        CarBooking booking = new CarBooking(UUID.randomUUID(), user, car, startDate, endDate, totalPrice, BookingStatus.ACTIVE, LocalDateTime.now());

        return carBookingDao.saveBooking(booking);
    }

    public CarBooking cancelBooking(UUID bookingId) {
        CarBooking carBooking = carBookingDao.getBookingById(bookingId);

        if (carBooking == null)
            throw new BookingNotFoundException(bookingId);

        if (carBooking.getBookingStatus() == BookingStatus.CANCELED)
            throw new IllegalStateException("Booking with Id " + bookingId + " is already cancelled.");

        carBooking.setBookingStatus(BookingStatus.CANCELED);

        return carBookingDao.updateBooking(carBooking);
    }

    public CarBooking[] getUserBookings(UUID userId) {
        User user = userDao.getUserById(userId);

        if (user == null) {
            throw new UserNotFoundException(userId);
        }

        return carBookingDao.getBookingByUserId(userId);
    }

    public CarBooking[] getAllBookings() {
        return carBookingDao.getBookings();
    }

    public void getUserBookedCars(UUID userId) {
        int activeBookingsForUser = 0;
        for (CarBooking booking : getUserBookings(userId)) {
            if (booking.getBookingStatus().equals(BookingStatus.ACTIVE)) {
                System.out.println(booking.getCar());
                activeBookingsForUser ++;
            }
        }
        if (activeBookingsForUser == 0)
            System.out.println("No booked cars for this user");
    }
}
