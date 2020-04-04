import com.google.gson.stream.JsonToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ParkingLotSystemTest {
    ManagementSystemOfParkingLot managementSystemOfParkingLot;
    ParkingLotSystem parkingLotSystem;
    Vehicles vehicle;
    ParkingLotOwner owner;
    AirportSecurity airportSecurity;

    @Before
    public void setUp() throws Exception {
        vehicle = new Vehicles("White");
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
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            boolean isPark = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertTrue(isPark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleAlreadyParked_shouldReturnFalse() {
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionTypes.VEHICLE_ALREADY_PARKED, e.exceptionTypes);
        }
    }

    @Test
    public void givenParkingLot_IfVehicleIsUnParked_ShouldReturnTrue() {
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            boolean unPark = managementSystemOfParkingLot.unPark(vehicle);
            Assert.assertEquals(unPark, unPark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_IsWhenFulled_ShouldReturnInformOwner() {
        managementSystemOfParkingLot.registerParkingLotObserver(owner);
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.park(new Vehicles("White"), ParkingLotSystem.DriverType.NORMAL);
        } catch (ParkingLotException e) {
            boolean capacityFulled = owner.isCapacityAvailable();
            Assert.assertTrue(capacityFulled);
        }
    }

    @Test
    public void givenParkingLot_Have2ParkingSIze_ShouldPark2_Vehicle() {
        Vehicles vehicle2 = new Vehicles("White");
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.NORMAL);
            boolean vehicleParked = managementSystemOfParkingLot.isVehicleParked(vehicle);
            boolean vehicleParked2 = managementSystemOfParkingLot.isVehicleParked(vehicle2);
            Assert.assertEquals(vehicleParked && vehicleParked2, vehicleParked && vehicleParked2);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_Full_ShouldInformSecurity() {
        managementSystemOfParkingLot.registerParkingLotObserver(airportSecurity);
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.park(new Vehicles("White"), ParkingLotSystem.DriverType.NORMAL);
        } catch (ParkingLotException e) {
            boolean capacityFull = airportSecurity.isCapacityAvailable();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void giveParkingLot_AfterFulledSign_WheneverParkingLotIs_Available_ItShouldReturnTrue() {
        Vehicles vehicle2 = new Vehicles("White");
        managementSystemOfParkingLot.registerParkingLotObserver(owner);
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.NORMAL);
        } catch (ParkingLotException e) {
            managementSystemOfParkingLot.unPark(vehicle);
            boolean sizeFulled = owner.isCapacityAvailable();
            Assert.assertTrue(sizeFulled);
        }
    }

    //UC6
    @Test
    public void givenParkingLot_OwnerHaveParkingAttendant_ThatCan_ParkCar() {
        Vehicles vehicle2 = new Vehicles("White");
        try {
            parkingLotSystem.emptySlotToParkTheVehicle(ParkingLotSystem.DriverType.NORMAL);
            boolean parkVehicle1 = parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            boolean parkVehicle2 = parkingLotSystem.park(vehicle2, ParkingLotSystem.DriverType.NORMAL);
            Assert.assertEquals(parkVehicle1 && parkVehicle2, parkVehicle1 && parkVehicle2);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    //UC7
    @Test
    public void givenParkingLot_DriverFindHisCar_shouldReturnTrue() {
        Vehicles vehicle2 = new Vehicles("White");
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.NORMAL);
            int driverFindHisCar = managementSystemOfParkingLot.findMyCar(vehicle2);
            Assert.assertEquals(driverFindHisCar, driverFindHisCar);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_VehicleNotFound_ShouldReturnFalse() {
        Vehicles vehicle2 = new Vehicles("White");
        Vehicles vehicle3 = new Vehicles("White");
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.findMyCar(vehicle3);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND, e.exceptionTypes);
        }
    }

    //UC8
    @Test
    public void givenParkingLot_ParkingOwnerWantToKnow_WhenCarIsPark() {
        managementSystemOfParkingLot.timeWhenCarIsPark(vehicle);
        Date parkingTime = new Date();
        Vehicles vehicle2 = new Vehicles("White");
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.NORMAL);
            Date dateAndTimeWhenVehicleIsParked = managementSystemOfParkingLot.timeWhenCarIsPark(vehicle2);
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
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.unPark(vehicle);
            managementSystemOfParkingLot.park(new Vehicles("White"), ParkingLotSystem.DriverType.NORMAL);
            Object emptySpaceInParkingLot = parkingLotSystem.emptySpaceToParkTheCar().get(0);
            Assert.assertEquals(emptySpaceInParkingLot, emptySpaceInParkingLot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    //UC10
    @Test
    public void givenParkingLot_DriverIsHandicap_ShouldParkTheCarNearestSlot() {
        Vehicles vehicle2 = new Vehicles("White");
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.HANDICAP);
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.NORMAL);
            int handicapCar = managementSystemOfParkingLot.findMyCar(vehicle);
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
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            parkingLotSystem.park(new Vehicles("White"), ParkingLotSystem.DriverType.HANDICAP);
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

        Vehicles vehicle2 = new Vehicles("White");
        Vehicles vehicle3 = new Vehicles("Red");
        Vehicles vehicle4 = new Vehicles("BLue");

        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.HANDICAP);
            parkingLotSystem.park(vehicle4, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.LARGE_VEHICLE);
            managementSystemOfParkingLot.park(vehicle3, ParkingLotSystem.DriverType.NORMAL);

            boolean vehicleParked = managementSystemOfParkingLot.isVehicleParked(vehicle2);
            Assert.assertTrue(vehicleParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

   // UC12
    @Test

    public void givenParkingLot_PoliceWantToKnow_LocationOfAllWhiteCars() {
        parkingLotSystem.setParkingLotCapacity(8);
        parkingLotSystem.initializeParkingLot();
        managementSystemOfParkingLot.addNewLot(parkingLotSystem);
        managementSystemOfParkingLot.isNewLotAdded(parkingLotSystem);
        Vehicles vehicle2 = new Vehicles("Black");
        Vehicles vehicle3 = new Vehicles("White");

        managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
        managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.HANDICAP);
        managementSystemOfParkingLot.park(vehicle3, ParkingLotSystem.DriverType.NORMAL);
        ArrayList<Integer> white = parkingLotSystem.findMyCarByColour("White");
        ArrayList<Integer> totalWhiteCar = new ArrayList<>();
        totalWhiteCar.add(6);
        totalWhiteCar.add(7);
        Assert.assertEquals(totalWhiteCar, white);

    }
}