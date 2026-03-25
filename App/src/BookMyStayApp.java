// Version 2.1

// Abstract class representing a generic Room
abstract class Room {
    private String roomType;
    private int beds;
    private double size; // in square meters
    private double price; // per night

    // Constructor
    public Room(String roomType, int beds, double size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    // Getters
    public String getRoomType() {
        return roomType;
    }

    public int getBeds() {
        return beds;
    }

    public double getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    // Abstract method to display room details
    public abstract void displayDetails();
}

// Concrete class for Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 20.0, 50.0);
    }

    @Override
    public void displayDetails() {
        System.out.println(getRoomType() + ": " + getBeds() + " bed, " + getSize() + " sqm, $" + getPrice() + " per night");
    }
}

// Concrete class for Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 35.0, 90.0);
    }

    @Override
    public void displayDetails() {
        System.out.println(getRoomType() + ": " + getBeds() + " beds, " + getSize() + " sqm, $" + getPrice() + " per night");
    }
}

// Concrete class for Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 60.0, 150.0);
    }

    @Override
    public void displayDetails() {
        System.out.println(getRoomType() + ": " + getBeds() + " beds, " + getSize() + " sqm, $" + getPrice() + " per night");
    }
}

// Main class to initialize rooms and availability
public class BookMyStayApp {
    // Static availability variables
    static int singleRoomAvailable = 5;
    static int doubleRoomAvailable = 3;
    static int suiteRoomAvailable = 2;

    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App - Version 2.1");
        System.out.println("-----------------------------------------");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleR = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Display room details
        single.displayDetails();
        System.out.println("Available: " + singleRoomAvailable);

        doubleR.displayDetails();
        System.out.println("Available: " + doubleRoomAvailable);

        suite.displayDetails();
        System.out.println("Available: " + suiteRoomAvailable);

        System.out.println("-----------------------------------------");
        System.out.println("Application terminated.");
    }
}