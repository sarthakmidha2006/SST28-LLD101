```
┌─────────────────────────────────────────────────────────────────────┐
│                          <<Singleton>>                              │
│                           ParkingLot                                │
│─────────────────────────────────────────────────────────────────────│
│ - instance: ParkingLot                                              │
│ - name: String                                                      │
│ - floors: List<Floor>                                               │
│ - gates: List<Gate>                                                 │
│─────────────────────────────────────────────────────────────────────│
│ + getInstance(): ParkingLot                                         │
│ + addFloor(Floor) / addGate(Gate)                                   │
│ + getFloors() / getGates() / getName()                              │
└──────────────┬──────────────────────────┬───────────────────────────┘
               │ 1..*                     │ 1..*
               ▼                          ▼
┌──────────────────────┐     ┌──────────────────────┐
│        Floor          │     │         Gate          │
│───────────────────────│     │───────────────────────│
│ - floorNumber: int    │     │ - gateId: int         │
│ - slots: List<Slot>   │     │ - floorNumber: int    │
│───────────────────────│     │───────────────────────│
│ + addSlot(Slot)       │     │ + getGateId()         │
│ + getSlots()          │     │ + getFloorNumber()    │
└──────────┬────────────┘     └───────────────────────┘
           │ 1..*
           ▼
┌──────────────────────────┐          ┌──────────────────────────┐
│          Slot             │          │        Vehicle            │
│───────────────────────────│          │───────────────────────────│
│ - slotNumber: int         │◆────────▶│ - vehicleNumber: String   │
│ - slotType: SlotType      │  0..1    │ - color: String           │
│ - floorNumber: int        │          │ - vehicleType: VehicleType│
│ - vehicle: Vehicle        │          │───────────────────────────│
│───────────────────────────│          │ + getVehicleNumber()      │
│ + parkVehicle(Vehicle)    │          │ + getColor()              │
│ + freeSlot()              │          │ + getVehicleType()        │
│ + isOccupied(): boolean   │          └───────────────────────────┘
└───────────────────────────┘                     ▲
               ▲                                  │
               │                    ┌─────────────┴──────────────┐
               │                    │    <<Factory>>              │
┌──────────────┴───────────────┐    │    VehicleFactory           │
│          Ticket               │    │────────────────────────────│
│──────────────────────────────│    │ + createVehicle(String,     │
│ - ticketId: String           │    │     String, VehicleType)    │
│ - vehicle: Vehicle           │    │   : Vehicle                 │
│ - slot: Slot                 │    └─────────────────────────────┘
│ - entryTime: LocalDateTime   │
│ - entryGateId: int           │
│──────────────────────────────│
│ + getTicketId() / getSlot()  │
│ + getVehicle() / getEntryTime│
│ + toString()                 │
└──────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────┐
│                         STRATEGIES                                   │
├──────────────────────────────────┬──────────────────────────────────┤
│  <<interface>>                   │  <<interface>>                    │
│  SlotAllocationStrategy          │  PricingStrategy                  │
│──────────────────────────────────│──────────────────────────────────│
│ + findSlot(ParkingLot,           │ + calculatePrice(long,            │
│     VehicleType, Gate): Slot     │     SlotType): double             │
│          ▲                       │          ▲                        │
│          │                       │          │                        │
│  NearestSlotStrategy             │  HourlyPricingStrategy            │
│──────────────────────────────────│──────────────────────────────────│
│ + findSlot(...)                  │ + calculatePrice(...)             │
│ - calculateDistance(Slot, Gate)   │  SMALL=10, MEDIUM=20, LARGE=30   │
│ - mapVehicleToSlot(VehicleType)  │                                   │
└──────────────────────────────────┴──────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────┐
│                          SERVICES                                    │
├────────────────┬───────────────────┬────────────────────────────────┤
│ ParkingService │  TicketService    │  PricingService                 │
│────────────────│───────────────────│────────────────────────────────│
│ - slotStrategy │ - ticketCounter   │ - pricingStrategy               │
│ - ticketService│───────────────────│────────────────────────────────│
│────────────────│ + generateTicket()│ + calculateFee(Ticket,          │
│ + parkVehicle()│                   │     LocalDateTime): double      │
│ + unparkVehicle│                   │                                 │
└────────────────┴───────────────────┴────────────────────────────────┘

┌────────────────┐    ┌────────────────┐
│  <<enum>>       │    │  <<enum>>       │
│  VehicleType    │    │  SlotType       │
│─────────────────│    │─────────────────│
│  TWO_WHEELER    │    │  SMALL          │
│  CAR            │    │  MEDIUM         │
│  TRUCK          │    │  LARGE          │
└─────────────────┘    └─────────────────┘
```
