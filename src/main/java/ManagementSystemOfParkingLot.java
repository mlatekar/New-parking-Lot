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

    public boolean park(Object vehicle, ParkingLotSystem.DriverType driverType,String colour) {
        ParkingLotSystem lot = maximumFreeSpaceToParkTheCar();
        return lot.park(vehicle, driverType, colour);
    }

    public boolean isVehicleParked(Object vehicle,String colour) {
        for (ParkingLotSystem parkingLots : this.parkingLotSystemList) {
            if (parkingLots.isVehicleParked(vehicle,colour))
                return true;
        }
        throw new ParkingLotException("VEHICLE IS NOT AVAILABLE", ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
    }

    public int findMyCar(Object vehicle,String colour) {
        for (ParkingLotSystem parkingLots : this.parkingLotSystemList)
            return parkingLots.findMyCar(vehicle,colour);
        throw new ParkingLotException("VEHICLE IS NOT AVAILABLE", ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
    }
 /*   public List findCarByColour(String vehicleColour) throws ParkingLotException {
        List<ArrayList> listOfCars = new ArrayList();
            for(Object carColour : this.parkingLotsList)
                ArrayList<Integer> wholeListOfCars=carColour.findMyCar(vehicleColour);
        listOfCars.add(wholeListOfCars);
        return listOfCars;
        throw new ParkingLotException("Vehicle Found", ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
    }*/
    public boolean unPark(Object vehicle,String colour) {
        for (ParkingLotSystem parkingLots : this.parkingLotSystemList) {
            return parkingLots.unPark(vehicle,colour);
        }
        throw new ParkingLotException("VEHICLE IS NOT AVAILABLE", ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
    }

    public Date timeWhenCarIsPark(Object vehicle,String colour) {
        for (ParkingLotSystem parkingLots : this.parkingLotSystemList)
            return parkingLots.timeWhenCarIsPark(vehicle,colour);
        throw new ParkingLotException("VEHICLE IS NOT AVAILABLE", ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
    }

    public ParkingLotSystem maximumFreeSpaceToParkTheCar() {
        return parkingLotSystemList.stream().sorted(Comparator.comparing(list -> list.emptySpaceToParkTheCar().size(), Comparator.reverseOrder())).collect(Collectors.toList()).get(0);
    }
   /* public List findVehicleByField(String fieldName) {
        List<ArrayList> parkingLotsList = new ArrayList<>();
        for (ParkingLot list : this.parkingLots) {
            ArrayList<Integer> onField = list.findOnField(fieldName);
            parkingLotsList.add(onField);
        }
        return parkingLotsList;
    }*/
    public void registerParkingLotObserver(ParkingLotObserver observer) {
        informer.registerParkingLotObserver(observer);
    }
}