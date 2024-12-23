package parkings;

public class Vehicle {
    private final String type;
    private final String registrationNumber;
    private final String color;

    public Vehicle(String type, String registrationNumber, String color) {
        if (type == null || registrationNumber == null || color == null) {
            throw new IllegalArgumentException("Vehicle type, registration number, and color cannot be null.");
        }
        this.type = type;
        this.registrationNumber = registrationNumber;
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getColor() {
        return color;
    }
}
