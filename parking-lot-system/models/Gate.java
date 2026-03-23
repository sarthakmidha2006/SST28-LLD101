package models;

public class Gate {
    private int gateId;
    private int floorNumber;

    public Gate(int gateId, int floorNumber) {
        this.gateId = gateId;
        this.floorNumber = floorNumber;
    }

    public int getGateId() {
        return gateId;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}
