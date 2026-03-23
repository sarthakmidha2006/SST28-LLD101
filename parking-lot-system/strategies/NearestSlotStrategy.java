package strategies;

import enums.SlotType;
import enums.VehicleType;
import models.Floor;
import models.Gate;
import models.ParkingLot;
import models.Slot;

public class NearestSlotStrategy implements SlotAllocationStrategy {

    @Override
    public Slot findSlot(ParkingLot parkingLot, VehicleType vehicleType, Gate entryGate) {
        SlotType requiredType = mapVehicleToSlot(vehicleType);
        Slot nearestSlot = null;
        int minDistance = Integer.MAX_VALUE;

        for (Floor floor : parkingLot.getFloors()) {
            for (Slot slot : floor.getSlots()) {
                if (!slot.isOccupied() && slot.getSlotType() == requiredType) {
                    int distance = Math.abs(slot.getFloorNumber() - entryGate.getFloorNumber()) * 100
                            + slot.getSlotNumber();
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestSlot = slot;
                    }
                }
            }
        }
        return nearestSlot;
    }

    private SlotType mapVehicleToSlot(VehicleType vehicleType) {
        switch (vehicleType) {
            case TWO_WHEELER: return SlotType.SMALL;
            case CAR:         return SlotType.MEDIUM;
            case TRUCK:       return SlotType.LARGE;
            default: throw new IllegalArgumentException("Unknown vehicle type");
        }
    }
}
