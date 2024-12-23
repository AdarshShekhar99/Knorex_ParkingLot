package parkings;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    private final int floors;
    private final Map<Integer, Floor> floorMap;
    private CostStrategy costStrategy;

    public ParkingLot(int floors) {
        if (floors <= 0) {
            throw new IllegalArgumentException("Number of floors must be greater than zero.");
        }
        this.floors = floors;
        this.floorMap = new HashMap<>();
    }

    public void initializeFloor(int floorNumber, int carSpaces, int busSpaces, int bikeSpaces, int truckSpaces) {
        if (floorNumber > floors || floorNumber <= 0) {
            throw new IllegalArgumentException("Invalid floor number. It must be between 1 and " + floors + ".");
        }
        if (floorMap.containsKey(floorNumber)) {
            throw new IllegalArgumentException("Floor number " + floorNumber + " is already initialized.");
        }
        floorMap.put(floorNumber, new Floor(floorNumber, carSpaces, busSpaces, bikeSpaces, truckSpaces));
    }

    public void setCostStrategy(CostStrategy costStrategy) {
        if (costStrategy == null) {
            throw new IllegalArgumentException("Cost strategy cannot be null.");
        }
        this.costStrategy = costStrategy;
    }

    public String addVehicle(Vehicle vehicle, LocalDateTime entryTime) {
        if (vehicle == null || entryTime == null) {
            throw new IllegalArgumentException("Vehicle and entry time cannot be null.");
        }
        for (Floor floor : floorMap.values()) {
            String token = floor.parkVehicle(vehicle, entryTime);
            if (token != null) {
                return token;
            }
        }
        throw new IllegalStateException("Parking lot is full for vehicle type: " + vehicle.getType());
    }

    public double removeVehicle(String token, LocalDateTime exitTime) {
        if (token == null || exitTime == null) {
            throw new IllegalArgumentException("Token and exit time cannot be null.");
        }
        for (Floor floor : floorMap.values()) {
            double fee = floor.unparkVehicle(token, exitTime, costStrategy);
            if (fee >= 0) {
                return fee;
            }
        }
        throw new IllegalArgumentException("Invalid token or vehicle not found.");
    }

    public int checkAvailability(int floorNumber, String vehicleType) {
        if (vehicleType == null || vehicleType.isEmpty()) {
            throw new IllegalArgumentException("Vehicle type cannot be null or empty.");
        }
        Floor floor = floorMap.get(floorNumber);
        if (floor == null) {
            throw new IllegalArgumentException("Floor number " + floorNumber + " does not exist.");
        }
        return floor.getAvailableSpaces(vehicleType);
    }

    public void displayStatus() {
        if (floorMap.isEmpty()) {
            System.out.println("No floors have been initialized.");
            return;
        }
        for (Floor floor : floorMap.values()) {
            floor.displayStatus();
        }
    }
}
