package com.qayas;
// TODO 1. create a new branch called initial-implementation
// TODO 2. create a package with your name. i.e com.franco and move this file inside the new package
// TODO 3. implement https://amigoscode.com/learn/java-cli-build/lectures/3a83ecf3-e837-4ae5-85a8-f8ae3f60f7f5

import com.qayas.booking.CarBooking;
import com.qayas.booking.CarBookingService;
import com.qayas.car.Car;
import com.qayas.car.CarService;
import com.qayas.user.User;
import com.qayas.user.UserService;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    private static CarBookingService carBookingService;
    private static CarService carService;
    private static UserService userService;

    public static void main(String[] args) {
        System.out.println("""
                Welcome to Car Booking Platform
                Please select the needed option:
                1 - Book Car
                2 - Delete Booking
                3 - View All User Booked Cars
                4 - View All Bookings
                5 - View Available Cars
                6 - View Available Electric Cars
                7 - View All Users
                8 - Exit""");

        Scanner sc = new Scanner(System.in);
        int optionNumber = sc.nextInt();

        if (optionNumber == 1) {
            System.out.println("Enter user id: ");
            UUID userId = UUID.fromString(sc.nextLine());

            System.out.println("Enter car id: ");
            UUID carId = UUID.fromString(sc.nextLine());

            System.out.println("Enter start date (yyyy-mm-dd): ");
            LocalDate startDate = LocalDate.parse(sc.nextLine());

            System.out.println("Enter end date (yyyy-mm-dd): ");
            LocalDate endDate = LocalDate.parse(sc.nextLine());

            CarBooking carBooking = carBookingService.bookCar(userId, carId, startDate, endDate);

            System.out.println("Booking created successfully!");
            System.out.println("Booking id: " + carBooking.getId());

        } else if (optionNumber == 2) {
            System.out.println("Enter booking id: ");
            UUID bookingId = UUID.fromString(sc.nextLine());

            CarBooking carBooking = carBookingService.cancelBooking(bookingId);

            System.out.println("Booking with id " + carBooking.getId() + " canceled successfully!");

        } else if (optionNumber == 3) {
            System.out.println("Enter user id: ");
            UUID userId = UUID.fromString(sc.nextLine());

            CarBooking[] userBookings = carBookingService.viewUserBookings(userId);
            for (CarBooking booking : userBookings) {
                System.out.println(booking);
            }
        } else if (optionNumber == 4) {
            System.out.println("All Bookings: ");
            for (CarBooking booking : carBookingService.viewAllBookings())
                System.out.println(booking);
        } else if (optionNumber == 5) {
            System.out.println("Available Cars: ");
            Car[] availableCars = carService.viewAvailableCars();
            for (Car car : availableCars)
                System.out.println(car);
        } else if (optionNumber == 6) {
            System.out.println("Available electric Cars: ");
            Car[] availableElectricCars = carService.viewAvailableCars();
            for (Car car : availableElectricCars)
                System.out.println(car);
        } else if (optionNumber == 7) {
            System.out.println("All Users: ");
            for (User user : userService.viewAllUsers())
                System.out.println(user);
        } else if (optionNumber == 8) {
            System.exit(0);
        }

        sc.close();
    }
}
