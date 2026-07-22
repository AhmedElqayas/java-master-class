package com.qayas.car;

import com.qayas.booking.BookingStatus;
import com.qayas.booking.CarBooking;
import com.qayas.booking.CarBookingDao;

public class CarService {
    private CarDao carDao;
    private CarBookingDao carBookingDao;

    public CarService(CarDao carDao, CarBookingDao carBookingDao) {
        this.carDao = carDao;
        this.carBookingDao = carBookingDao;
    }

    public Car[] viewAvailableCars() {
        CarBooking[] activeBookings = carBookingDao.findActiveBookings();
        Car[] allCars = carDao.findAll();

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

    public Car[] viewAvailableElectricCars() {
        Car[] availableCars = viewAvailableCars();
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
