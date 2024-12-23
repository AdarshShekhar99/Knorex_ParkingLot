package parkings;

import java.time.LocalDateTime;

public interface CostStrategy {
    double calculateCost(String vehicleType, LocalDateTime entryTime, LocalDateTime exitTime);
}
