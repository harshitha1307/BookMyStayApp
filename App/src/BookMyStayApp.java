// Version 5.1
// UseCase5BookingRequestQueue.java
// Demonstrates booking request intake using a FIFO queue

import java.util.LinkedList;
import java.util.Queue;

// Reservation class representing a guest's booking intent
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

    public void displayReservation() {
        System.out.println("Guest: " + guestName +
                " | Room Type: " + roomType +
                " | Rooms Requested: " + roomsRequested);
    }
}

// Booking Request Queue – preserves arrival order
class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add a reservation to the queue
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request added for guest: " + reservation.getGuestName());
    }

    // Peek at the next request (without removing)
    public Reservation peekNextRequest() {
        return requestQueue.peek();
    }

    // Process requests in arrival order
    public void processRequests() {
        System.out.println("\nProcessing booking requests (FIFO order):");
        while (!requestQueue.isEmpty()) {
            Reservation reservation = requestQueue.poll();
            reservation.displayReservation();
        }
    }

    // Get the number of requests waiting
    public int getQueueSize() {
        return requestQueue.size();
    }
}

// Main Application
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App (Use Case 5.1)\n");

        // Initialize booking request queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate guest booking requests
        Reservation res1 = new Reservation("Alice", "Single Room", 1);
        Reservation res2 = new Reservation("Bob", "Suite Room", 2);
        Reservation res3 = new Reservation("Charlie", "Double Room", 1);

        // Add requests to the queue
        bookingQueue.addRequest(res1);
        bookingQueue.addRequest(res2);
        bookingQueue.addRequest(res3);

        System.out.println("\nTotal requests in queue: " + bookingQueue.getQueueSize());

        // Process queued requests (for demonstration, no inventory mutation yet)
        bookingQueue.processRequests();

        System.out.println("\nAll booking requests processed. Inventory remains unchanged at this stage.");
    }
}