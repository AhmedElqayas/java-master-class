package com.qayas.car;

import java.math.BigDecimal;
import java.util.UUID;

public class CarDao {
    private static final Car[] cars;

    static {
        cars = new Car[] {
                new Car(UUID.fromString("33333333-3333-3333-3333-333333333333"), "ABC123", BigDecimal.valueOf(100L), Brand.AUDI, false)
        };
    }

    public Car findById(UUID carId) {
        for (Car car : cars) {
            if (car.getId().equals(carId))
                return car;
        }
        return null;
    }

    public Car[] findAll() {
        return cars;
    }
}
