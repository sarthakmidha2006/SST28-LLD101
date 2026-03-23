package strategies;

import enums.SlotType;

public class HourlyPricingStrategy implements PricingStrategy {

    @Override
    public double calculatePrice(long durationInHours, SlotType slotType) {
        double rate;
        switch (slotType) {
            case SMALL:  rate = 10; break;
            case MEDIUM: rate = 20; break;
            case LARGE:  rate = 30; break;
            default:     rate = 20;
        }
        return Math.max(1, durationInHours) * rate;
    }
}
