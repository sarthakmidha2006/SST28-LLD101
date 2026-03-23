package services;

import models.Slot;
import models.Ticket;
import models.Vehicle;

import java.time.LocalDateTime;

public class TicketService {
    private int ticketCounter = 0;

    public Ticket generateTicket(Vehicle vehicle, Slot slot, int entryGateId) {
        String ticketId = "TKT-" + (++ticketCounter);
        return new Ticket(ticketId, vehicle, slot, LocalDateTime.now(), entryGateId);
    }
}
