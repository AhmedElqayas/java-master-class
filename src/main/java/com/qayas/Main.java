package com.qayas;
// TODO 1. create a new branch called initial-implementation
// TODO 2. create a package with your name. i.e com.franco and move this file inside the new package
// TODO 3. implement https://amigoscode.com/learn/java-cli-build/lectures/3a83ecf3-e837-4ae5-85a8-f8ae3f60f7f5

import com.qayas.booking.*;
import com.qayas.car.Car;
import com.qayas.car.CarArrayDataAccessService;
import com.qayas.car.CarDao;
import com.qayas.car.CarService;
import com.qayas.user.User;
import com.qayas.user.UserArrayDataAccessService;
import com.qayas.user.UserDao;
import com.qayas.user.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    private static final CarDao carDao = new CarArrayDataAccessService();
    private static final UserDao userDao = new UserArrayDataAccessService();
    private static final CarBookingDao carBookingDao = new CarBookingFileDataAccessService("bookings.txt");
    private static final CarService carService = new CarService(carBookingDao, carDao);
    private static final UserService userService = new UserService(userDao);
    private static final CarBookingService carBookingService = new CarBookingService(carBookingDao, carDao);

    public static void main(String[] args) {
        System.out.println("""
                Welcome to Car Booking Platform.
                Please select the needed option:
                1 - Book Car
                2 - Delete Booking
                3 - View All User Booked Cars
                4 - View All Bookings
                5 - View Available Cars
                6 - View Available Electric Cars
                7 - View All Users
                8 - Exit
                """);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter your option: ");
            int optionNumber = Integer.parseInt(sc.nextLine());

            switch (optionNumber) {
                case 1 -> {
                    System.out.println("Enter user id: ");
                    UUID userId = UUID.fromString(sc.nextLine());

                    System.out.println("Enter car id: ");
                    UUID carId = UUID.fromString(sc.nextLine());

                    LocalDate startDate = readDate(sc, "Enter start date (yyyy-mm-dd): ");
                    LocalDate endDate = readDate(sc, "Enter end date (yyyy-mm-dd): ");

                    CarBooking carBooking = carBookingService.bookCar(userId, carId, startDate, endDate);

                    System.out.println("Booking created successfully!");
                    System.out.println("Booking id: " + carBooking.getId());
                }

                case 2 -> {
                    System.out.println("Enter booking id: ");
                    UUID bookingId = UUID.fromString(sc.nextLine());

                    CarBooking carBooking = carBookingService.cancelBooking(bookingId);

                    System.out.println("Booking with id " + carBooking.getId() + " canceled successfully!");
                }

                case 3 -> {
                    System.out.println("Enter user id: ");
                    UUID userId = UUID.fromString(sc.nextLine());

                    carBookingService.getUserBookedCars(userId);
                }

                case 4 -> {
                    System.out.println("All Bookings: ");

                    for (CarBooking booking : carBookingService.getAllBookings()) {
                        System.out.println(booking);
                    }
                }

                case 5 -> {
                    System.out.println("Available Cars: ");

                    Car[] availableCars = carService.getAvailableCars();

                    if (availableCars.length == 0)
                        System.out.println("No available cars.");

                    for (Car car : availableCars) {
                        System.out.println(car);
                    }
                }

                case 6 -> {
                    System.out.println("Available electric Cars: ");

                    Car[] availableElectricCars = carService.getAvailableElectricCars();

                    if (availableElectricCars.length == 0)
                        System.out.println("No available electric cars.");

                    for (Car car : availableElectricCars) {
                        System.out.println(car);
                    }
                }

                case 7 -> {
                    System.out.println("All Users: ");

                    for (User user : userService.getAllUsers()) {
                        System.out.println(user);
                    }
                }

                case 8 -> System.exit(0);

                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static LocalDate readDate(Scanner sc, String message) {
        while (true) {
            System.out.println(message);

            try {
                return LocalDate.parse(sc.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Please use the format yyyy-mm-dd.");
            }
        }
    }
}
