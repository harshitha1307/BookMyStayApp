// Version 3.1
// UseCase3InventorySetup.java
// Demonstrates centralized room inventory management using HashMap

import java.util.HashMap;

// Abstract Room class (from Use Case 2)
abstract class Room {
    private String type;
    private int beds;
    private double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    public abstract void displayDetails();
}

// Concrete Room classes
class SingleRoom extends Room {
    public SingleRoom(double price) {
        super("Single Room", 1, price);
    }

    @Override
    public void displayDetails() {
        System.out.println(getType() + " | Beds: " + getBeds() + " | Price: $" + getPrice());
    }
}

class DoubleRoom extends Room {
    public DoubleRoom(double price) {
        super("Double Room", 2, price);
    }

    @Override
    public void displayDetails() {
        System.out.println(getType() + " | Beds: " + getBeds() + " | Price: $" + getPrice());
    }
}

class SuiteRoom extends Room {
    public SuiteRoom(double price) {
        super("Suite Room", 3, price);
    }

    @Override
    public void displayDetails() {
        System.out.println(getType() + " | Beds: " + getBeds() + " | Price: $" + getPrice());
    }
}

// Centralized Inventory Management
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Register a room type with initial availability
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Get available count for a room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability after booking or cancellation
    public void updateAvailability(String roomType, int change) {
        int current = inventory.getOrDefault(roomType, 0);
        inventory.put(roomType, current + change);
    }

    // Display current inventory state
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " | Available: " + inventory.get(roomType));
        }
    }
}

// Main Application
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App (Use Case 3.1)\n");

        // Initialize room objects
        Room singleRoom = new SingleRoom(100.0);
        Room doubleRoom = new DoubleRoom(180.0);
        Room suiteRoom = new SuiteRoom(300.0);

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType(singleRoom.getType(), 5);
        inventory.addRoomType(doubleRoom.getType(), 3);
        inventory.addRoomType(suiteRoom.getType(), 2);

        // Display room details
        singleRoom.displayDetails();
        doubleRoom.displayDetails();
        suiteRoom.displayDetails();

        // Display current inventory
        inventory.displayInventory();

        // Example of updating inventory
        System.out.println("\nBooking 1 Single Room...");
        inventory.updateAvailability(singleRoom.getType(), -1);

        System.out.println("Booking 2 Suite Rooms...");
        inventory.updateAvailability(suiteRoom.getType(), -2);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nThank you for using Book My Stay App!");
    }
}