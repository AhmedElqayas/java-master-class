package com.qayas.car;

import com.qayas.Exceptions.CarNotFoundException;

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
        return cars.stream()
                .filter(car -> car.getId().equals(carId))
                .findFirst()
                .orElseThrow(() -> new CarNotFoundException(carId));
    }
}
