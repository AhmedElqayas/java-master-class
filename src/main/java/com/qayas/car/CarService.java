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

        List<Car> availableCars = new ArrayList<>();

        for (Car car : allCars) {
            boolean isBooked = false;

            for (CarBooking activeBooking : activeBookings) {
                if (car.getId().equals(activeBooking.getCar().getId())) {
                    isBooked = true;
                    break;
                }
            }
            if (!isBooked) {
                availableCars.add(car);
            }
        }

        return availableCars;
    }

    public List<Car> getAvailableElectricCars() {
        List<Car> availableCars = getAvailableCars();
        List<Car> availableElectricCars = new ArrayList<>();

        for (Car car : availableCars) {
            if (car.isElectric()) {
                availableElectricCars.add(car);
            }
        }

        return availableElectricCars;
    }
}
