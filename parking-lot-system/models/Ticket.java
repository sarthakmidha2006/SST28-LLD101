package models;

import java.time.LocalDateTime;

public class Ticket {
    private String ticketId;
    private Vehicle vehicle;
    private Slot slot;
    private LocalDateTime entryTime;
    private int entryGateId;

    public Ticket(String ticketId, Vehicle vehicle, Slot slot,
                  LocalDateTime entryTime, int entryGateId) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.slot = slot;
        this.entryTime = entryTime;
        this.entryGateId = entryGateId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Slot getSlot() {
        return slot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public int getEntryGateId() {
        return entryGateId;
    }

    @Override
    public String toString() {
        return ticketId +
                " | " + vehicle.getVehicleNumber() +
                " | " + vehicle.getColor() +
                " | " + vehicle.getVehicleType() +
                " | Floor-" + slot.getFloorNumber() +
                " Slot-" + slot.getSlotNumber() +
                " [" + slot.getSlotType() + "]" +
                " | Gate-" + entryGateId;
    }
}
