package parkings;

import java.time.LocalDateTime;

public class VehicleSpace {
    private final int spaceNumber;
    private Vehicle vehicle;
    private LocalDateTime entryTime;

    public VehicleSpace(int spaceNumber) {
        this.spaceNumber = spaceNumber;
        this.vehicle = null;
        this.entryTime = null;
    }

    public boolean isAvailable() {
        return vehicle == null;
    }

    public void assignVehicle(Vehicle vehicle, LocalDateTime entryTime) {
        if (vehicle == null || entryTime == null) {
            throw new IllegalArgumentException("Vehicle and entry time cannot be null.");
        }
        this.vehicle = vehicle;
        this.entryTime = entryTime;
    }

    public double releaseVehicle(LocalDateTime exitTime, CostStrategy costStrategy) {
        if (vehicle == null) {
            throw new IllegalStateException("No vehicle assigned to this space.");
        }
        if (exitTime.isBefore(entryTime)) {
            throw new IllegalArgumentException("Exit time cannot be before entry time.");
        }
        double fee = costStrategy.calculateCost(vehicle.getType(), entryTime, exitTime);
        this.vehicle = null;
        this.entryTime = null;
        return fee;
    }

    public int getSpaceNumber() {
        return spaceNumber;
    }
}
