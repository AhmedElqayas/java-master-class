package com.qayas.car;

import java.math.BigDecimal;
import java.util.UUID;

public class CarArrayDataAccessService implements CarDao {
    private static final Car[] cars;

    static {
        cars = new Car[]{
                new Car(UUID.fromString("11111111-1111-1111-1111-111111111111"), "ABC123", BigDecimal.valueOf(100L), Brand.AUDI, true)
        };
    }

    public Car[] getCars() {
        return cars;
    }

    public Car getCarById(UUID carId) {
        for (Car car : cars) {
            if (car.getId().equals(carId))
                return car;
        }
        return null;
    }
}
