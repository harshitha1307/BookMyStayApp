// Version 7.1
// UseCase7AddOnServiceSelection.java
// Demonstrates add-on service selection for confirmed reservations

import java.util.*;

// Service class representing an optional add-on
class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return name + " ($" + cost + ")";
    }
}

// Reservation class (simplified for add-on linking)
class Reservation {
    private String reservationID;
    private String guestName;
    private String roomType;

    public Reservation(String reservationID, String guestName, String roomType) {
        this.reservationID = reservationID;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationID() {
        return reservationID;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationID +
                " | Guest: " + guestName +
                " | Room Type: " + roomType);
    }
}

// Add-On Service Manager
class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Add a service to a reservation
    public void addService(Reservation res, Service service) {
        List<Service> services = reservationServices.getOrDefault(res.getReservationID(), new ArrayList<>());
        services.add(service);
        reservationServices.put(res.getReservationID(), services);
        System.out.println("Added service " + service + " to reservation " + res.getReservationID());
    }

    // Get services for a reservation
    public List<Service> getServices(Reservation res) {
        return reservationServices.getOrDefault(res.getReservationID(), new ArrayList<>());
    }

    // Calculate total cost of add-ons for a reservation
    public double calculateTotalCost(Reservation res) {
        return getServices(res).stream().mapToDouble(Service::getCost).sum();
    }

    // Display services for a reservation
    public void displayServices(Reservation res) {
        List<Service> services = getServices(res);
        if (services.isEmpty()) {
            System.out.println("No add-on services selected for reservation " + res.getReservationID());
        } else {
            System.out.println("Add-on services for reservation " + res.getReservationID() + ":");
            services.forEach(s -> System.out.println("- " + s));
            System.out.println("Total Add-on Cost: $" + calculateTotalCost(res));
        }
    }
}

// Main Application
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App (Use Case 7.1)\n");

        // Simulate confirmed reservations
        Reservation res1 = new Reservation("R-1001", "Alice", "Single Room");
        Reservation res2 = new Reservation("R-1002", "Bob", "Suite Room");

        // Initialize Add-On Service Manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Define some add-on services
        Service breakfast = new Service("Breakfast", 15.0);
        Service airportPickup = new Service("Airport Pickup", 30.0);
        Service spa = new Service("Spa Package", 50.0);

        // Guests select services
        serviceManager.addService(res1, breakfast);
        serviceManager.addService(res1, airportPickup);
        serviceManager.addService(res2, spa);

        // Display reservations and selected services
        System.out.println();
        res1.displayReservation();
        serviceManager.displayServices(res1);

        System.out.println();
        res2.displayReservation();
        serviceManager.displayServices(res2);

        System.out.println("\nThank you for using Book My Stay App!");
    }
}