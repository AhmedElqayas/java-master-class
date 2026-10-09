package com.qayas.car;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarListDataAccessService implements CarDao {
    private static final List<Car> cars = new ArrayList<>();

    static {
        cars.add(new Car(UUID.fromString("11111111-1111-1111-1111-111111111111"), "ABC123", BigDecimal.valueOf(100L), Brand.AUDI, true));
    }

    public List<Car> getCars() {
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
