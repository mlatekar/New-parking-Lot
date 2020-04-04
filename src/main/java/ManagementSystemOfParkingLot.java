import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ManagementSystemOfParkingLot {
    List<ParkingLotSystem> parkingLotSystemList;
    ParkingLotInformer informer;

    public ManagementSystemOfParkingLot() {
        informer = new ParkingLotInformer();
        this.parkingLotSystemList = new ArrayList<>();
    }

    public void addNewLot(ParkingLotSystem parkingLot) {
        this.parkingLotSystemList.add(parkingLot);
    }

    public boolean isNewLotAdded(ParkingLotSystem parkingLot) {
        return this.parkingLotSystemList.contains(parkingLot);
    }

    public boolean park(Vehicles vehicle, ParkingLotSystem.DriverType driverType) {
        ParkingLotSystem lot = maximumFreeSpaceToParkTheCar();
        return lot.park(vehicle, driverType);
    }

    public boolean isVehicleParked(Vehicles vehicle) {
        for (ParkingLotSystem parkingLots : this.parkingLotSystemList) {
            if (parkingLots.isVehicleParked(vehicle))
                return true;
        }
        throw new ParkingLotException("VEHICLE IS NOT AVAILABLE", ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
    }

    public boolean unPark(Vehicles vehicle) {
        for (ParkingLotSystem parkingLots : this.parkingLotSystemList) {
            return parkingLots.unPark(vehicle);
        }
        throw new ParkingLotException("VEHICLE IS NOT AVAILABLE", ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
    }

    public int findMyCar(Vehicles vehicle) {
        for (ParkingLotSystem parkingLots : this.parkingLotSystemList)
            return parkingLots.findMyCar(vehicle);
        throw new ParkingLotException("VEHICLE IS NOT AVAILABLE", ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
    }

    public ParkingLotSystem maximumFreeSpaceToParkTheCar() {
        return parkingLotSystemList.stream().sorted(Comparator.comparing(list -> list.emptySpaceToParkTheCar().size(), Comparator.reverseOrder())).collect(Collectors.toList()).get(0);
    }

    public Date timeWhenCarIsPark(Vehicles vehicle) {
        for (ParkingLotSystem parkingLots : this.parkingLotSystemList)
            return parkingLots.timeWhenCarIsPark(vehicle);
        throw new ParkingLotException("VEHICLE IS NOT AVAILABLE", ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
    }

    public void registerParkingLotObserver(ParkingLotObserver observer) {
        informer.registerParkingLotObserver(observer);
    }
}