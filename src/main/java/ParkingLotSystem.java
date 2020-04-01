import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLotSystem {


    public enum DriverType {NORMAL, HANDICAP, LARGE_VEHICLE}

    private ParkingSlot parkingSlot;
    private int actualCapacity;
    private List<ParkingLotObserver> observersList;
    public List<Object> vehicles;

    public ParkingLotSystem(int actualCapacity) {
        setParkingLotCapacity(actualCapacity);
        this.observersList = new ArrayList<>();

    }

    public void setParkingLotCapacity(int capacity) {
        this.actualCapacity = capacity;
        initializeParkingLot();
    }

    public int initializeParkingLot() {
        this.vehicles = new ArrayList<>();
        IntStream.range(0, this.actualCapacity).forEach(slots -> vehicles.add(null));
        return vehicles.size();
    }

    public boolean park(Object vehicle, DriverType driverType) throws ParkingLotException {
        parkingSlot = new ParkingSlot(vehicle);
        if (isVehicleParked(vehicle))
            throw new ParkingLotException("Already parked", ParkingLotException.ExceptionTypes.VEHICLE_ALREADY_PARKED);
        if (vehicles.size() == actualCapacity && !vehicles.contains(null)) {
            observersList.forEach(ParkingLotObserver::setCapacityFull);
            throw new ParkingLotException("Parking Fulled", ParkingLotException.ExceptionTypes.PARKING_LOT_FULL);
        }
        int emptySlotToParkTheVehicleInSlot = emptySlotToParkTheVehicle(driverType);
        this.vehicles.set(emptySlotToParkTheVehicleInSlot, parkingSlot);
        return true;
    }

    public boolean isVehicleParked(Object vehicle) {
        parkingSlot = new ParkingSlot(vehicle);
        return this.vehicles.contains(parkingSlot);
    }

    public boolean unPark(Object vehicle) {
        parkingSlot = new ParkingSlot(vehicle);
        if (vehicle == null)
            return false;
        if (this.vehicles.contains(parkingSlot)) {
            this.vehicles.set(this.vehicles.indexOf(parkingSlot), null);
            return true;
        }
        return false;
    }

    public Integer emptySlotToParkTheVehicle(DriverType driverType) {
        if (DriverType.HANDICAP.equals(driverType)) {
            return emptySpaceToParkTheCar().stream().sorted().collect(Collectors.toList()).get(0);
        }
        if (DriverType.LARGE_VEHICLE.equals(driverType)) {
            return emptySpaceToParkTheCar().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).get(0);
        }
        return emptySpaceToParkTheCar().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).get(0);
    }


    public int findMyCar(Object vehicle) throws ParkingLotException {
        parkingSlot = new ParkingSlot(vehicle);
        if (this.vehicles.contains(parkingSlot))
            return this.vehicles.indexOf(parkingSlot);
        throw new ParkingLotException("Vehicle Found", ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
    }

    public ArrayList<Integer> emptySpaceToParkTheCar() {
        ArrayList<Integer> emptyParkingSpace = new ArrayList();
        IntStream.range(0, this.actualCapacity).filter(vehicle -> vehicles.get(vehicle) == null).forEach(slot -> emptyParkingSpace.add(slot));
        return emptyParkingSpace;
    }

    public Date timeWhenCarIsPark(Object vehicle) {
        parkingSlot = new ParkingSlot(vehicle);
        return parkingSlot.parkedTime;
    }

    public void registerParkingLotObserver(ParkingLotObserver observer) {
        observersList.add(observer);
    }
}