package parkings;


import java.time.LocalDateTime;

public class ParkingLotSystem {
     public static void main(String[] args) {
        // Initialize Parking Lot
        ParkingLot parkingLot = new ParkingLot(2); // 2 floors
        parkingLot.initializeFloor(1, 2, 0, 0, 0); // Floor 1: 2 Cars, 0 Buses, 0 Bikes, 0 Trucks
        parkingLot.initializeFloor(2, 1, 1, 0, 0); // Floor 2: 1 Car, 1 Bus, 0 Bikes, 0 Trucks

        // Configure cost strategy
        CostStrategy costStrategy = new FlatCostStrategy();
        parkingLot.setCostStrategy(costStrategy);

        // Add vehicles
        String token1 = parkingLot.addVehicle(new Vehicle("Car", "KA01AB1234", "Red"), LocalDateTime.now());
        System.out.println("Vehicle added. Token: " + token1);

        String token2 = parkingLot.addVehicle(new Vehicle("Car", "KA02CD5678", "Blue"), LocalDateTime.now());
        System.out.println("Vehicle added. Token: " + token2);

        // Check availability
        System.out.println("Availability for Cars on Floor 1: " + parkingLot.checkAvailability(1, "Car"));

        // Try adding another vehicle to a full lot
        try {
            parkingLot.addVehicle(new Vehicle("Car", "KA03EF9012", "White"), LocalDateTime.now());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        // Remove vehicle and calculate parking fee
        LocalDateTime exitTime = LocalDateTime.now().plusHours(3);
        double fee = parkingLot.removeVehicle(token1, exitTime);
        System.out.println("Vehicle removed. Fee: " + fee);
    }
}
