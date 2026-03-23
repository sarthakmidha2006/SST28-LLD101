package services;

import models.Ticket;
import strategies.PricingStrategy;

import java.time.Duration;
import java.time.LocalDateTime;

public class PricingService {
    private PricingStrategy pricingStrategy;

    public PricingService(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public double calculateFee(Ticket ticket, LocalDateTime exitTime) {
        long hours = Duration.between(ticket.getEntryTime(), exitTime).toHours();
        return pricingStrategy.calculatePrice(hours, ticket.getSlot().getSlotType());
    }
}
