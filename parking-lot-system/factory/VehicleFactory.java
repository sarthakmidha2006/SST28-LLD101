package factory;

import enums.VehicleType;
import models.Vehicle;

public class VehicleFactory {

    public static Vehicle createVehicle(String vehicleNumber, String color, VehicleType type) {
        return new Vehicle(vehicleNumber, color, type);
    }
}
