// Version 4.1
// UseCase4RoomSearch.java
// Demonstrates read-only room search using centralized inventory

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Abstract Room class
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

// Centralized Inventory Management (from UC3)
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int change) {
        int current = inventory.getOrDefault(roomType, 0);
        inventory.put(roomType, current + change);
    }

    public HashMap<String, Integer> getInventoryMap() {
        return new HashMap<>(inventory); // return a copy for read-only safety
    }
}

// Search Service – Read-only operations
class RoomSearchService {
    private RoomInventory inventory;
    private List<Room> rooms;

    public RoomSearchService(RoomInventory inventory, List<Room> rooms) {
        this.inventory = inventory;
        this.rooms = rooms;
    }

    public void displayAvailableRooms() {
        System.out.println("Available Rooms:");
        boolean anyAvailable = false;
        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getType());
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available + "\n");
                anyAvailable = true;
            }
        }
        if (!anyAvailable) {
            System.out.println("No rooms are currently available.\n");
        }
    }
}

// Main Application
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App (Use Case 4.1)\n");

        // Initialize room objects
        List<Room> roomList = new ArrayList<>();
        roomList.add(new SingleRoom(100.0));
        roomList.add(new DoubleRoom(180.0));
        roomList.add(new SuiteRoom(300.0));

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 0); // simulate unavailable room
        inventory.addRoomType("Suite Room", 2);

        // Initialize search service
        RoomSearchService searchService = new RoomSearchService(inventory, roomList);

        // Perform read-only search
        searchService.displayAvailableRooms();

        System.out.println("Thank you for using Book My Stay App!");
    }
}