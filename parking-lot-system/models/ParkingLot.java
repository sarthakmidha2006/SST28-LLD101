package models;

import java.util.List;
import java.util.ArrayList;

public class ParkingLot {
    private static ParkingLot instance;
    private String name;
    private List<Floor> floors;
    private List<Gate> gates;

    private ParkingLot() {
        this.floors = new ArrayList<>();
        this.gates = new ArrayList<>();
    }

    public static ParkingLot getInstance() {
        if (instance == null) {
            instance = new ParkingLot();
        }
        return instance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public List<Gate> getGates() {
        return gates;
    }

    public void addFloor(Floor floor) {
        floors.add(floor);
    }

    public void addGate(Gate gate) {
        gates.add(gate);
    }
}
