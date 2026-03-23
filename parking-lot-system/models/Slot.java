package models;

import enums.SlotType;

public class Slot {
    private int slotNumber;
    private SlotType slotType;
    private int floorNumber;
    private Vehicle vehicle;

    public Slot(int slotNumber, SlotType slotType, int floorNumber) {
        this.slotNumber = slotNumber;
        this.slotType = slotType;
        this.floorNumber = floorNumber;
    }

    public void parkVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void freeSlot() {
        this.vehicle = null;
    }

    public boolean isOccupied() {
        return vehicle != null;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public SlotType getSlotType() {
        return slotType;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
