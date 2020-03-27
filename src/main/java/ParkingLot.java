import java.util.*;
import java.util.stream.IntStream;

public class ParkingLot {
    private int actualSize;
    private Map vehicles;
    private List<ParkingLotObserver> observers;
    private List parkingVehicle;
    enum DriverType{NORMAL,HANDICAP}


    public ParkingLot(int size) {
        this.observers = new ArrayList<>();
        this.vehicles = new HashMap();
        this.actualSize = size;
        this.parkingVehicle = new ArrayList();
    }

    public void registerParkingLotObserver(ParkingLotObserver observer) {
        this.observers.add(observer);
    }

    public void setSizeCapacity(int parkingSize) {
        this.actualSize = parkingSize;
    }

    public void park(Object vehicle, Date parkTime,DriverType driverType) throws ParkingLotException {
    //    if (parkingAttendantToParkTheCar(parkingVehicle,driverType))
            if (isVehicleParked(vehicle))
                throw new ParkingLotException("Already parked");
        if (this.vehicles.size() == this.actualSize) {
            for (ParkingLotObserver observer : observers) {
                observer.sizeFulled();
            }
            throw new ParkingLotException("Parking Fulled");
        }
        this.vehicles.get(vehicle);
    }
    public int emptySlotInParkingLot(){

        int remainingParkingPlace=this.actualSize-this.vehicles.size();
        return remainingParkingPlace;

    }

    public boolean parkingAttendantToParkTheCar(Object parkingVehicle, DriverType driverType) {
        if(DriverType.NORMAL.equals(driverType)) {
        if (this.parkingVehicle.contains(parkingVehicle))
            return true;
        }
        if(DriverType.HANDICAP.equals(driverType)) {
            if (this.parkingVehicle.contains(parkingVehicle))
                return true;
        }
        return false;
    }

    public ArrayList emptySpaceToParkTheCar() {
        ArrayList<Integer> emptySpace = new ArrayList();
        IntStream.range(0,this.actualSize).filter(vehicle -> vehicles.get(vehicle) == null).forEach(slot -> emptySpace.add(slot));
        return emptySpace;
    }

    public boolean isVehicleParked(Object vehicle) {
        if (this.vehicles.equals(vehicle))
            return true;
        return false;
    }

    public boolean unPark(Object vehicle) {
        if (vehicle == null)
            return false;
        if (this.vehicles.equals(vehicle)) {
            this.vehicles.remove(vehicle);
            for (ParkingLotObserver observer : observers) {
                observer.sizeAvailable();
            }
            return true;
        }
        return false;
    }

    public boolean findMyCar(Object vehicle) throws ParkingLotException {
        if (this.vehicles.equals(vehicle)) {
            throw new ParkingLotException("Vehicle Found");
        }
        return false;
    }

    public boolean timeWhenCarIsPark(Object parkingTime) {
        if (vehicles.equals(parkingTime))
            return true;
        return false;
    }
}