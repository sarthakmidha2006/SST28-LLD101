package services;

import models.Gate;
import models.ParkingLot;
import models.Slot;
import models.Ticket;
import models.Vehicle;
import strategies.SlotAllocationStrategy;

public class ParkingService {
    private SlotAllocationStrategy slotAllocationStrategy;
    private TicketService ticketService;

    public ParkingService(SlotAllocationStrategy slotAllocationStrategy, TicketService ticketService) {
        this.slotAllocationStrategy = slotAllocationStrategy;
        this.ticketService = ticketService;
    }

    public Ticket parkVehicle(ParkingLot parkingLot, Vehicle vehicle, Gate entryGate) {
        Slot slot = slotAllocationStrategy.findSlot(parkingLot, vehicle.getVehicleType(), entryGate);
        if (slot == null) {
            System.out.println("No available slot for: " + vehicle.getVehicleType());
            return null;
        }
        slot.parkVehicle(vehicle);
        return ticketService.generateTicket(vehicle, slot, entryGate.getGateId());
    }

    public void unparkVehicle(Ticket ticket) {
        ticket.getSlot().freeSlot();
    }
}
