package strategies;

import enums.SlotType;

public interface PricingStrategy {
    double calculatePrice(long durationInHours, SlotType slotType);
}
