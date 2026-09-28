package com.qayas.car;

import com.qayas.booking.CarBooking;
import com.qayas.booking.CarBookingDao;

public class CarService {
    private final CarBookingDao carBookingDao;
    private final CarDao carDao;
    public CarService(CarBookingDao carBookingDao, CarDao carDao) {
        this.carBookingDao = carBookingDao;
        this.carDao = carDao;
    }

    public Car[] getAvailableCars() {
        CarBooking[] activeBookings = carBookingDao.getActiveBookings();
        Car[] allCars = carDao.getCars();

        int availableCarsCount = allCars.length - activeBookings.length;
        Car[] availableCars = new Car[availableCarsCount];
        int index = 0;


        for (Car car : allCars) {
            boolean isBooked = false;

            for (CarBooking activeBooking : activeBookings) {
                if (car.getId().equals(activeBooking.getCar().getId())) {
                    isBooked = true;
                    break;
                }
            }
            if (!isBooked) {
                availableCars[index++] = car;
            }
        }
        return availableCars;
    }

    public Car[] getAvailableElectricCars() {
        Car[] availableCars = getAvailableCars();
        int electricCarsCount = 0;

        for (Car car : availableCars) {
            if (car.isElectric())
                electricCarsCount++;
        }

        Car[] availableElectricCars = new Car[electricCarsCount];
        int index = 0;

        for (Car car : availableCars) {
            if (car.isElectric())
                availableElectricCars[index++] = car;
        }
        return availableElectricCars;
    }
}
