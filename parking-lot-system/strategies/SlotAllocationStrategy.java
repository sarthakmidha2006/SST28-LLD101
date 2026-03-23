package strategies;

import enums.VehicleType;
import models.Gate;
import models.ParkingLot;
import models.Slot;

public interface SlotAllocationStrategy {
    Slot findSlot(ParkingLot parkingLot, VehicleType vehicleType, Gate entryGate);
}
