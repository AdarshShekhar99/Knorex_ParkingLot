package parkings;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

class FlatCostStrategy implements CostStrategy{
    private static final Map<String, Double> rates = new HashMap<>();

    static {
        rates.put("Bike", 10.0);
        rates.put("Car", 20.0);
        rates.put("Bus", 30.0);
        rates.put("Truck", 40.0);
    }

    @Override
    public double calculateCost(String vehicleType, LocalDateTime entryTime, LocalDateTime exitTime) {
        if (!rates.containsKey(vehicleType)) {
            throw new IllegalArgumentException("Unsupported vehicle type: " + vehicleType);
        }
        long hours = Math.max(1, java.time.Duration.between(entryTime, exitTime).toHours());
        return rates.get(vehicleType) * hours;
    }
}
