import com.google.gson.stream.JsonToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class ParkingLotSystemTest {
    ManagementSystemOfParkingLot managementSystemOfParkingLot;
    ParkingLotSystem parkingLotSystem;
    Object vehicle;
    ParkingLotOwner owner;
    AirportSecurity airportSecurity;

    @Before
    public void setUp() throws Exception {
        vehicle = new Object();
        parkingLotSystem = new ParkingLotSystem(2);
        owner = new ParkingLotOwner();
        airportSecurity = new AirportSecurity();
        parkingLotSystem.registerParkingLotObserver(owner);
        managementSystemOfParkingLot = new ManagementSystemOfParkingLot();
        managementSystemOfParkingLot.addNewLot(parkingLotSystem);
        managementSystemOfParkingLot.registerParkingLotObserver(owner);
    }

    @Test
    public void givenParkingLot_WhenVehicleParked_ShouldReturnTrue() {
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL, "White");
            boolean isPark = parkingLotSystem.isVehicleParked(vehicle, "white");
            Assert.assertTrue(isPark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleAlreadyParked_shouldReturnFalse() {
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL, "White");
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL, "White");
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionTypes.VEHICLE_ALREADY_PARKED, e.exceptionTypes);
        }
    }

    @Test
    public void givenParkingLot_IfVehicleIsUnParked_ShouldReturnTrue() {
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL, "White");
            boolean unPark = managementSystemOfParkingLot.unPark(vehicle, "White");
            Assert.assertEquals(unPark, unPark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_IsWhenFulled_ShouldReturnInformOwner() {
        managementSystemOfParkingLot.registerParkingLotObserver(owner);
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL, "White");
            managementSystemOfParkingLot.park(new Object(), ParkingLotSystem.DriverType.NORMAL, "White");
        } catch (ParkingLotException e) {
            boolean capacityFulled = owner.isCapacityAvailable();
            Assert.assertTrue(capacityFulled);
        }
    }

    @Test
    public void givenParkingLot_Have2ParkingSIze_ShouldPark2_Vehicle() {
        Object vehicle2 = new Object();
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL, "White");
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.NORMAL, "White");
            boolean vehicleParked = managementSystemOfParkingLot.isVehicleParked(vehicle, "White");
            boolean vehicleParked2 = managementSystemOfParkingLot.isVehicleParked(vehicle2, "White");
            Assert.assertEquals(vehicleParked && vehicleParked2, vehicleParked && vehicleParked2);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_Full_ShouldInformSecurity() {
        managementSystemOfParkingLot.registerParkingLotObserver(airportSecurity);
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL, "White");
            managementSystemOfParkingLot.park(new Object(), ParkingLotSystem.DriverType.NORMAL, "White");
        } catch (ParkingLotException e) {
            boolean capacityFull = airportSecurity.isCapacityAvailable();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void giveParkingLot_AfterFulledSign_WheneverParkingLotIs_Available_ItShouldReturnTrue() {
        Object vehicle2 = new Object();
        managementSystemOfParkingLot.registerParkingLotObserver(owner);
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL, "White");
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.NORMAL, "White");
        } catch (ParkingLotException e) {
            managementSystemOfParkingLot.unPark(vehicle, "White");
            boolean sizeFulled = owner.isCapacityAvailable();
            Assert.assertTrue(sizeFulled);
        }
    }

    //UC6
    @Test
    public void givenParkingLot_OwnerHaveParkingAttendant_ThatCan_ParkCar() {
        Object vehicle2 = new Object();
        try {
            parkingLotSystem.emptySlotToParkTheVehicle(ParkingLotSystem.DriverType.NORMAL);
            boolean parkVehicle1 = parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL, "White");
            boolean parkVehicle2 = parkingLotSystem.park(vehicle2, ParkingLotSystem.DriverType.NORMAL, "White");
            Assert.assertEquals(parkVehicle1 && parkVehicle2, parkVehicle1 && parkVehicle2);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    //UC7
    @Test
    public void givenParkingLot_DriverFindHisCar_shouldReturnTrue() {
        Object vehicle2 = new Object();
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL, "White");
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.NORMAL, "White");
            int driverFindHisCar = managementSystemOfParkingLot.findMyCar(vehicle2, "White");
            Assert.assertEquals(driverFindHisCar, driverFindHisCar);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_VehicleNotFound_ShouldReturnFalse() {
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL, "White");
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.NORMAL, "White");
            managementSystemOfParkingLot.findMyCar(vehicle3, "White");
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND, e.exceptionTypes);
        }
    }

    //UC8
    @Test
    public void givenParkingLot_ParkingOwnerWantToKnow_WhenCarIsPark() {
        managementSystemOfParkingLot.timeWhenCarIsPark(owner, "White");
        Date parkingTime = new Date();
        Object vehicle2 = new Object();
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL, "White");
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.NORMAL, "White");
            Date dateAndTimeWhenVehicleIsParked = managementSystemOfParkingLot.timeWhenCarIsPark(vehicle2, "White");
            System.out.println(parkingTime + " time " + dateAndTimeWhenVehicleIsParked);
            Assert.assertEquals(parkingTime, dateAndTimeWhenVehicleIsParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    //UC9
    @Test
    public void givenMultipleCarsLessThanActualCapacity_WhenParkEvenly_shouldReturnLastEmptySpace() {
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL, "White");
            managementSystemOfParkingLot.unPark(vehicle, "White");
            managementSystemOfParkingLot.park(new Object(), ParkingLotSystem.DriverType.NORMAL, "White");
            Object emptySpaceInParkingLot = parkingLotSystem.emptySpaceToParkTheCar().get(0);
            Assert.assertEquals(emptySpaceInParkingLot, emptySpaceInParkingLot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    //UC10
    @Test
    public void givenParkingLot_DriverIsHandicap_ShouldParkTheCarNearestSlot() {
        Object vehicle2 = new Object();
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.HANDICAP, "White");
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.NORMAL, "White");
            int handicapCar = managementSystemOfParkingLot.findMyCar(vehicle, "White");
            Assert.assertEquals(handicapCar, handicapCar);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenMultipleCarParkingLots_WhenAdded_ShouldReturnTrue() {
        try {
            managementSystemOfParkingLot.addNewLot(parkingLotSystem);
            boolean newLot = managementSystemOfParkingLot.isNewLotAdded(parkingLotSystem);
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL, "White");
            parkingLotSystem.park(new Object(), ParkingLotSystem.DriverType.HANDICAP, "White");
            Assert.assertTrue(newLot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    //UC11
    @Test
    public void givenParkingLot_LargeCarsParkIn_HighestSpaceParkingLot_ShouldReturnTrue() {
        parkingLotSystem.setParkingLotCapacity(8);
        parkingLotSystem.initializeParkingLot();

        managementSystemOfParkingLot.addNewLot(parkingLotSystem);
        managementSystemOfParkingLot.isNewLotAdded(parkingLotSystem);

        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        Object vehicle4 = new Object();

        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.HANDICAP, "red");
            parkingLotSystem.park(vehicle4, ParkingLotSystem.DriverType.NORMAL, "red");
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.LARGE_VEHICLE, "White");
            managementSystemOfParkingLot.park(vehicle3, ParkingLotSystem.DriverType.NORMAL, "White");

            boolean vehicleParked = managementSystemOfParkingLot.isVehicleParked(vehicle2, "White");
            Assert.assertTrue(vehicleParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

}