// Version 6.1
// UseCase6RoomAllocationService.java
// Demonstrates reservation confirmation with safe room allocation

import java.util.*;

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

// Reservation representing a guest's booking intent
class Reservation {
    private String guestName;
    private String roomType;
    private int roomsRequested;

    public Reservation(String guestName, String roomType, int roomsRequested) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomsRequested = roomsRequested;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getRoomsRequested() {
        return roomsRequested;
    }
}

// Centralized Inventory Management
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public boolean allocateRooms(String roomType, int count) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available >= count) {
            inventory.put(roomType, available - count);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " | Available: " + inventory.get(type));
        }
    }
}

// Booking Request Queue
class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation res) {
        requestQueue.add(res);
        System.out.println("Booking request added for guest: " + res.getGuestName());
    }

    public Reservation pollRequest() {
        return requestQueue.poll();
    }

    public boolean isEmpty() {
        return requestQueue.isEmpty();
    }
}

// Booking Service – confirms reservations and allocates rooms
class BookingService {
    private RoomInventory inventory;
    private Map<String, Set<String>> allocatedRoomIDs; // Maps room type → allocated IDs
    private int roomIDCounter; // For generating unique room IDs

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRoomIDs = new HashMap<>();
        roomIDCounter = 1000; // starting room ID
    }

    // Confirm a reservation
    public void confirmReservation(Reservation res) {
        String type = res.getRoomType();
        int requested = res.getRoomsRequested();

        // Check availability
        if (inventory.getAvailability(type) >= requested) {
            // Allocate unique room IDs
            Set<String> allocatedIDs = allocatedRoomIDs.getOrDefault(type, new HashSet<>());
            List<String> assignedIDs = new ArrayList<>();
            for (int i = 0; i < requested; i++) {
                String newID;
                do {
                    newID = type.substring(0, 2).toUpperCase() + "-" + roomIDCounter++;
                } while (allocatedIDs.contains(newID));
                allocatedIDs.add(newID);
                assignedIDs.add(newID);
            }
            allocatedRoomIDs.put(type, allocatedIDs);

            // Update inventory
            inventory.allocateRooms(type, requested);

            // Confirm reservation
            System.out.println("\nReservation Confirmed for " + res.getGuestName());
            System.out.println("Room Type: " + type);
            System.out.println("Assigned Room IDs: " + assignedIDs);
        } else {
            System.out.println("\nReservation Failed for " + res.getGuestName() +
                    ". Not enough " + type + " available.");
        }
    }

    public void displayAllocatedRooms() {
        System.out.println("\nAllocated Room IDs:");
        for (String type : allocatedRoomIDs.keySet()) {
            System.out.println(type + " → " + allocatedRoomIDs.get(type));
        }
    }
}

// Main Application
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App (Use Case 6.1)");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 3);
        inventory.addRoomType("Suite Room", 2);

        // Initialize booking request queue
        BookingRequestQueue queue = new BookingRequestQueue();
        queue.addRequest(new Reservation("Alice", "Single Room", 1));
        queue.addRequest(new Reservation("Bob", "Suite Room", 2));
        queue.addRequest(new Reservation("Charlie", "Double Room", 1));
        queue.addRequest(new Reservation("David", "Suite Room", 1)); // Should fail if only 2 suites

        // Initialize booking service
        BookingService service = new BookingService(inventory);

        // Process queued requests
        while (!queue.isEmpty()) {
            Reservation res = queue.pollRequest();
            service.confirmReservation(res);
        }

        // Display allocated rooms and remaining inventory
        service.displayAllocatedRooms();
        inventory.displayInventory();

        System.out.println("\nThank you for using Book My Stay App!");
    }
}