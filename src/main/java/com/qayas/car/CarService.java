package com.qayas.car;

import com.qayas.booking.CarBooking;
import com.qayas.booking.CarBookingDao;

import java.util.ArrayList;
import java.util.List;

public class CarService {
    private final CarBookingDao carBookingDao;
    private final CarDao carDao;

    public CarService(CarBookingDao carBookingDao, CarDao carDao) {
        this.carBookingDao = carBookingDao;
        this.carDao = carDao;
    }

    public List<Car> getAvailableCars() {
        List<CarBooking> activeBookings = carBookingDao.getActiveBookings();
        List<Car> allCars = carDao.getCars();

        return allCars.stream()
                .filter(car ->
                        activeBookings.stream()
                                .noneMatch(activeBooking -> car.getId().equals(activeBooking.getCar().getId())))
                .toList();

    }

    public List<Car> getAvailableElectricCars() {
        List<Car> availableCars = getAvailableCars();

        return availableCars.stream()
                .filter(Car::isElectric)
                .toList();
    }
}
