import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLotSystem {

    private ParkingSlot parkingSlot;
    private int actualCapacity;
    private List<ParkingLotObserver> observersList;
    public List<ParkingSlot> vehicles;
    public int last30min = 30;

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

    public boolean park(Vehicles vehicle, Enum driverType) throws ParkingLotException {
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

    public boolean isVehicleParked(Vehicles vehicle) {
        parkingSlot = new ParkingSlot(vehicle);
        return this.vehicles.contains(parkingSlot);
    }

    public boolean unPark(Vehicles vehicle) {
        parkingSlot = new ParkingSlot(vehicle);
        if (vehicle == null)
            return false;
        if (this.vehicles.contains(parkingSlot)) {
            this.vehicles.set(this.vehicles.indexOf(parkingSlot), null);
            return true;
        }
        return false;
    }

    public Integer emptySlotToParkTheVehicle(Enum driverType) {
        DriverType[] driverTypes = DriverType.values();
        for (DriverType type : driverTypes) {
            switch (type) {
                case HANDICAP:
                    return emptySpaceToParkTheCar().stream().sorted().collect(Collectors.toList()).get(0);
                case NORMAL:
                    return emptySpaceToParkTheCar().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).get(0);
            }
        }
        VehicleType[] vehicleTypes = VehicleType.values();
        for (VehicleType type1 : vehicleTypes) {
            switch (type1) {
                case LARGE_VEHICLE:
                    return largeVehicleThatPark();
                case SMALL_VEHICLE:
                    return emptySpaceToParkTheCar().stream().sorted().collect(Collectors.toList()).get(0);
            }
        }
        return null;
    }

    public int largeVehicleThatPark() {
        for (int placeToPark = 0; placeToPark < vehicles.size(); placeToPark++)
            if (vehicles.get(placeToPark) == null && vehicles.get(placeToPark + 1) == null && vehicles.get(placeToPark + 2) == null)
                return placeToPark + 1;
        return emptySpaceToParkTheCar().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).get(0);

    }

    public int findMyCar(Vehicles vehicle) throws ParkingLotException {
        parkingSlot = new ParkingSlot(vehicle);
        if (this.vehicles.contains(parkingSlot))
            return this.vehicles.indexOf(parkingSlot);
        throw new ParkingLotException("Vehicle Found", ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
    }

    public ArrayList<Integer> searchByCarColour(String colour) {
        try {
            ArrayList<Integer> carColour = new ArrayList<>();
            for (int getCarColour = 0; getCarColour < this.vehicles.size(); getCarColour++)
                if ((this.vehicles.get(getCarColour) != null))
                    if (this.vehicles.get(getCarColour).vehicle.searchingCarColour().equals(colour))
                        carColour.add(getCarColour);
            return carColour;
        } catch (ParkingLotException e) {
            throw new ParkingLotException("This colour Vehicle not Found", ParkingLotException.ExceptionTypes.THIS_COLOUR_OF_VEHICLES_NOT_FOUND);
        }
    }

    public List<String> searchByCarColourAndCarType(String carsColour, String carsType) {
        try {
            List<String> carColour = new ArrayList<>();
            carColour = this.vehicles.stream()
                    .filter(parkingSlot -> parkingSlot != null)
                    .filter(parkingSlot -> parkingSlot.vehiclesData().carColour.equals(carsColour))
                    .filter(parkingSlot -> parkingSlot.vehiclesData().carsType.equals(carsType))
                    .map(parkingSlot -> parkingSlot.vehiclesData().carsNumberPlate())
                    .collect(Collectors.toList());
            System.out.println("vehicles data " + carColour);
            return carColour;
        } catch (ParkingLotException e) {
            throw new ParkingLotException("This colour Vehicle not Found", ParkingLotException.ExceptionTypes.THIS_COLOUR_OF_VEHICLES_NOT_FOUND);
        }
    }

    public List<String> searchAllSmallHandicapCars(DriverType driverType) {
        try {
            List<String> smallHandicapCars = new ArrayList<>();
            smallHandicapCars = this.vehicles.stream()
                    .filter(parkingSlot -> parkingSlot != null)
                    .filter(parkingSlot -> driverType.equals(driverType))
                    .map(parkingSlot -> (parkingSlot.vehiclesData().carsNumberPlate()) + " "
                            + (parkingSlot.vehiclesData().carsType) + " "
                            + (parkingSlot.vehiclesData().carColour))
                    .collect(Collectors.toList());
            System.out.println("vehicles data " + smallHandicapCars);
            return smallHandicapCars;
        } catch (ParkingLotException e) {
            throw new ParkingLotException("Vehicle not Found", ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
        }
    }

    public List<String> searchByCarsType(String carsType) {
        try {
            List<String> carCompany = new ArrayList<>();
            carCompany = this.vehicles.stream()
                    .filter(parkingSlot -> parkingSlot != null)
                    .filter(parkingSlot -> parkingSlot.vehiclesData().carsType.equals(carsType))
                    .map(parkingSlot -> parkingSlot.vehiclesData().carsNumberPlate())
                    .collect(Collectors.toList());
            return carCompany;
        } catch (ParkingLotException e) {
            throw new ParkingLotException("Vehicle Type not Found", ParkingLotException.ExceptionTypes.VEHICLE_TYPE_NOT_FOUND);
        }
    }

    public ArrayList<Integer> emptySpaceToParkTheCar() {
        ArrayList<Integer> emptyParkingSpace = new ArrayList();
        IntStream.range(0, this.actualCapacity).filter(vehicle -> vehicles.get(vehicle) == null).forEach(slot -> emptyParkingSpace.add(slot));
        ArrayList<Integer> emptyParkingSpace1 = emptyParkingSpace;
        return emptyParkingSpace;
    }

    public Date timeWhenCarIsPark(Vehicles vehicle) {
        parkingSlot = new ParkingSlot(vehicle);
        return parkingSlot.parkedTime;
    }

    public ArrayList<String> findLast30MinuteParkedCars() {
        try {
            ArrayList<String> ListOf30Min = new ArrayList<>();
            ListOf30Min = (ArrayList<String>) this.vehicles.stream()
                    .filter(parkingSlot -> parkingSlot != null)
                    .filter(parkingSlot -> parkingSlot.timeWhenCarIsParked().getMinute() - LocalDateTime.now().getMinute() <= last30min)
                    .map(parkingSlot -> parkingSlot.vehiclesData().carsNumberPlate())
                    .collect(Collectors.toList());
            return ListOf30Min;
        } catch (ParkingLotException e) {
            throw new ParkingLotException("Vehicle not Found", ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
        }
    }

    public List<String> findAllCars() {
        try {
            List<String> allParkedCars = new ArrayList<>();
            allParkedCars = this.vehicles.stream()
                    .filter(parkingSlot -> parkingSlot != null)
                    .map(parkingSlot -> (parkingSlot.vehiclesData().carsNumberPlate()) + " "
                            + (parkingSlot.vehiclesData().carsType) + " "
                            + (parkingSlot.vehiclesData().carColour))
                    .collect(Collectors.toList());
            System.out.println("vehicles data " + allParkedCars);
            return allParkedCars;
        } catch (ParkingLotException e) {
            throw new ParkingLotException("Nothing in Parking Lot", ParkingLotException.ExceptionTypes.PARKING_LOT_IS_EMPTY);
        }
    }

    public void registerParkingLotObserver(ParkingLotObserver observer) {
        observersList.add(observer);
    }
}