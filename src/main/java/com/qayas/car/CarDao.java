package com.qayas.car;

import java.util.List;
import java.util.UUID;

public interface CarDao {
    List<Car> getCars();
    Car getCarById(UUID carId);
}