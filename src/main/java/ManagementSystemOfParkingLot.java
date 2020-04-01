import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ManagementSystemOfParkingLot {
    List<ParkingLotSystem> parkingLotsList;
    ParkingLotInformer informer;

    public ManagementSystemOfParkingLot() {
        informer = new ParkingLotInformer();
        this.parkingLotsList = new ArrayList<>();
    }

    public void addNewLot(ParkingLotSystem parkingLot) {
        this.parkingLotsList.add(parkingLot);
    }

    public boolean isNewLotAdded(ParkingLotSystem parkingLot) {
        return this.parkingLotsList.contains(parkingLot);
    }

    public boolean park(Object vehicle, ParkingLotSystem.DriverType driverType) {
        ParkingLotSystem lot = emptySpaceToParkTheCar();
        return lot.park(vehicle, driverType);
    }

    public boolean isVehicleParked(Object vehicle) {
        for (ParkingLotSystem parkingLots : this.parkingLotsList) {
            if (parkingLots.isVehicleParked(vehicle))
                return true;
        }
        throw new ParkingLotException("VEHICLE IS NOT AVAILABLE", ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
    }

    public int findMyCar(Object vehicle) {
        for (ParkingLotSystem parkingLots : this.parkingLotsList)
            return parkingLots.findMyCar(vehicle);
        throw new ParkingLotException("VEHICLE IS NOT AVAILABLE", ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
    }

    public boolean unPark(Object vehicle) {
        for (ParkingLotSystem parkingLots : this.parkingLotsList) {
            return parkingLots.unPark(vehicle);
        }
        throw new ParkingLotException("VEHICLE IS NOT AVAILABLE", ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
    }

    public Date timeWhenCarIsPark(Object vehicle) {
        for (ParkingLotSystem parkingLots : this.parkingLotsList)
            return parkingLots.timeWhenCarIsPark(vehicle);
        throw new ParkingLotException("VEHICLE IS NOT AVAILABLE", ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
    }

    public ParkingLotSystem emptySpaceToParkTheCar() {
        return parkingLotsList.stream().sorted(Comparator.comparing(list -> list.emptySpaceToParkTheCar().size(), Comparator.reverseOrder())).collect(Collectors.toList()).get(0);
    }

    public void registerParkingLotObserver(ParkingLotObserver observer) {
        informer.registerParkingLotObserver(observer);
    }
}