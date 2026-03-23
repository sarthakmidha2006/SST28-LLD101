import enums.SlotType;
import enums.VehicleType;
import factory.VehicleFactory;
import models.Floor;
import models.Gate;
import models.ParkingLot;
import models.Slot;
import models.Ticket;
import models.Vehicle;
import services.ParkingService;
import services.PricingService;
import services.TicketService;
import strategies.HourlyPricingStrategy;
import strategies.NearestSlotStrategy;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        ParkingLot parkingLot = ParkingLot.getInstance();
        parkingLot.setName("City Center Parking");
        setupFloors(parkingLot, 3, 2, 3, 1);

        Gate entryGate1 = new Gate(1, 1);
        Gate entryGate2 = new Gate(2, 1);
        Gate exitGate = new Gate(3, 1);
        parkingLot.addGate(entryGate1);
        parkingLot.addGate(entryGate2);
        parkingLot.addGate(exitGate);

        System.out.println("=== " + parkingLot.getName() + " ===");
        System.out.println("Floors: " + parkingLot.getFloors().size());
        System.out.println("Gates: " + parkingLot.getGates().size());
        System.out.println();

        ParkingService parkingService = new ParkingService(new NearestSlotStrategy(), new TicketService());
        PricingService pricingService = new PricingService(new HourlyPricingStrategy());

        System.out.println("=== Parking Vehicles ===");
        System.out.println();

        Vehicle bike1 = VehicleFactory.createVehicle("KA-01-1234", "Black", VehicleType.TWO_WHEELER);
        Ticket t1 = parkingService.parkVehicle(parkingLot, bike1, entryGate1);
        System.out.println("Parked: " + t1);

        Vehicle car1 = VehicleFactory.createVehicle("MH-02-5678", "White", VehicleType.CAR);
        Ticket t2 = parkingService.parkVehicle(parkingLot, car1, entryGate1);
        System.out.println("Parked: " + t2);

        Vehicle truck1 = VehicleFactory.createVehicle("DL-03-9012", "Red", VehicleType.TRUCK);
        Ticket t3 = parkingService.parkVehicle(parkingLot, truck1, entryGate2);
        System.out.println("Parked: " + t3);

        Vehicle car2 = VehicleFactory.createVehicle("TN-04-3456", "Blue", VehicleType.CAR);
        Ticket t4 = parkingService.parkVehicle(parkingLot, car2, entryGate1);
        System.out.println("Parked: " + t4);

        Vehicle bike2 = VehicleFactory.createVehicle("AP-05-7890", "Grey", VehicleType.TWO_WHEELER);
        Ticket t5 = parkingService.parkVehicle(parkingLot, bike2, entryGate2);
        System.out.println("Parked: " + t5);

        System.out.println();
        System.out.println("=== Exiting Vehicles ===");
        System.out.println();

        printExit(pricingService, parkingService, t1, 3);
        printExit(pricingService, parkingService, t2, 5);
        printExit(pricingService, parkingService, t3, 2);

        System.out.println();
        System.out.println("=== Parking After Exit ===");
        System.out.println();

        Vehicle car3 = VehicleFactory.createVehicle("GJ-06-1111", "Silver", VehicleType.CAR);
        Ticket t6 = parkingService.parkVehicle(parkingLot, car3, entryGate1);
        System.out.println("Parked: " + t6);

        System.out.println();
        System.out.println("=== Slot Status ===");
        System.out.println();
        for (Floor floor : parkingLot.getFloors()) {
            System.out.println("Floor " + floor.getFloorNumber() + ":");
            for (Slot slot : floor.getSlots()) {
                String status = slot.isOccupied()
                        ? "OCCUPIED by " + slot.getVehicle().getVehicleNumber()
                        : "AVAILABLE";
                System.out.println("  Slot " + slot.getSlotNumber() +
                        " [" + slot.getSlotType() + "] -> " + status);
            }
        }
    }

    private static void printExit(PricingService pricingService, ParkingService parkingService,
                                   Ticket ticket, int hours) {
        LocalDateTime exitTime = ticket.getEntryTime().plusHours(hours);
        double fee = pricingService.calculateFee(ticket, exitTime);
        parkingService.unparkVehicle(ticket);
        System.out.println("Exit: " + ticket.getTicketId() +
                " | " + ticket.getVehicle().getVehicleNumber() +
                " | " + hours + " hrs | Fee: Rs." + fee);
    }

    private static void setupFloors(ParkingLot parkingLot, int numFloors,
                                     int smallSlots, int mediumSlots, int largeSlots) {
        for (int i = 1; i <= numFloors; i++) {
            Floor floor = new Floor(i);
            int slotNum = 1;
            for (int j = 0; j < smallSlots; j++)
                floor.addSlot(new Slot(slotNum++, SlotType.SMALL, i));
            for (int j = 0; j < mediumSlots; j++)
                floor.addSlot(new Slot(slotNum++, SlotType.MEDIUM, i));
            for (int j = 0; j < largeSlots; j++)
                floor.addSlot(new Slot(slotNum++, SlotType.LARGE, i));
            parkingLot.addFloor(floor);
        }
    }
}
