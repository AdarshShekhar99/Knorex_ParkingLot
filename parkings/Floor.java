package parkings;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Floor {
    private final int floorNumber;
    private final Map<String, List<VehicleSpace>> vehicleSpaces;

    public Floor(int floorNumber, int carSpaces, int busSpaces, int bikeSpaces, int truckSpaces) {
        if (carSpaces < 0 || busSpaces < 0 || bikeSpaces < 0 || truckSpaces < 0) {
            throw new IllegalArgumentException("Number of spaces cannot be negative.");
        }
        this.floorNumber = floorNumber;
        this.vehicleSpaces = new HashMap<>();
        vehicleSpaces.put("Car", initializeSpaces(carSpaces));
        vehicleSpaces.put("Bus", initializeSpaces(busSpaces));
        vehicleSpaces.put("Bike", initializeSpaces(bikeSpaces));
        vehicleSpaces.put("Truck", initializeSpaces(truckSpaces));
    }

    private List<VehicleSpace> initializeSpaces(int count) {
        List<VehicleSpace> spaces = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            spaces.add(new VehicleSpace(i));
        }
        return spaces;
    }

    public String parkVehicle(Vehicle vehicle, LocalDateTime entryTime) {
        List<VehicleSpace> spaces = vehicleSpaces.get(vehicle.getType());
        if (spaces == null) {
            throw new IllegalArgumentException("Unsupported vehicle type: " + vehicle.getType());
        }
        for (VehicleSpace space : spaces) {
            if (space.isAvailable()) {
                space.assignVehicle(vehicle, entryTime);
                return "TOKEN-" + floorNumber + "-" + space.getSpaceNumber();
            }
        }
        return null;
    }

    public double unparkVehicle(String token, LocalDateTime exitTime, CostStrategy costStrategy) {
        String[] parts = token.split("-");
        if (parts.length != 3 || !parts[0].equals("TOKEN") || Integer.parseInt(parts[1]) != floorNumber) {
            return -1;
        }
        int spaceNumber = Integer.parseInt(parts[2]);
        for (List<VehicleSpace> spaces : vehicleSpaces.values()) {
            for (VehicleSpace space : spaces) {
                if (space.getSpaceNumber() == spaceNumber && !space.isAvailable()) {
                    return space.releaseVehicle(exitTime, costStrategy);
                }
            }
        }
        return -1;
    }

    public int getAvailableSpaces(String vehicleType) {
        List<VehicleSpace> spaces = vehicleSpaces.get(vehicleType);
        if (spaces == null) {
            throw new IllegalArgumentException("Unsupported vehicle type: " + vehicleType);
        }
        int count = 0;
        for (VehicleSpace space : spaces) {
            if (space.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    public void displayStatus() {
        System.out.println("Floor " + floorNumber + " status:");
        for (Map.Entry<String, List<VehicleSpace>> entry : vehicleSpaces.entrySet()) {
            long available = entry.getValue().stream().filter(VehicleSpace::isAvailable).count();
            System.out.println(entry.getKey() + ": Available = " + available + ", Occupied = " + (entry.getValue().size() - available));
        }
    }
}
