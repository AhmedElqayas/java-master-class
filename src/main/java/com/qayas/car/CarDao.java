package com.qayas.car;

import java.util.UUID;

public interface CarDao {
    Car[] getCars();
    Car getCarById(UUID carId);
}