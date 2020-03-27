import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class ParkingLotTest {

    ParkingLot parkingLot = null;
    Object vehicle = null;

    @Before
    public void setUp() throws Exception {
        vehicle = new Object();
        parkingLot = new ParkingLot(2);

    }

    @Test
    public void givenParkingLot_WhenVehicleParked_ShouldReturnTrue() {
        try {
            parkingLot.park(vehicle, new Date());
            boolean isPark = parkingLot.isVehicleParked(vehicle);
            Assert.assertTrue(isPark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleAlreadyParked_shouldReturnFalse() {
        try {
            parkingLot.park(vehicle, new Date());
            parkingLot.isVehicleParked(new Object());
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking Fulled", e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_IfVehicleIsUnParked_ShouldReturnTrue() {
        try {
            parkingLot.park(vehicle, new Date());
            boolean unPark = parkingLot.unPark(vehicle);
            Assert.assertTrue(unPark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_IsWhenFulled_ShouldReturnInformOwner() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registerParkingLotObserver(owner);
        try {
            parkingLot.park(vehicle, new Date());
            parkingLot.park(new Object(), new Date());
        } catch (ParkingLotException e) {
            boolean capacityFulled = owner.isCapacityFulled();
            Assert.assertTrue(capacityFulled);
        }
    }

    @Test
    public void givenParkingLot_Have2ParkingSIze_ShouldPark2_Vehicle() {
        Object vehicle2 = new Object();
        parkingLot.setSizeCapacity(2);
        try {
            parkingLot.park(vehicle, new Date());
            parkingLot.park(vehicle2, new Date());
            boolean vehicleParked = parkingLot.isVehicleParked(vehicle);
            boolean vehicleParked2 = parkingLot.isVehicleParked(vehicle2);
            Assert.assertTrue(vehicleParked && vehicleParked2);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_Full_ShouldInformSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLot.registerParkingLotObserver(airportSecurity);
        try {
            parkingLot.park(vehicle, new Date());
            parkingLot.park(new Object(), new Date());
        } catch (ParkingLotException e) {
            boolean capacityFull = airportSecurity.sizeFulled();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void giveParkingLot_AfterFulledSign_WheneverParkingLotIs_Available_ItShouldReturnTrue() {
        Object vehicle2 = new Object();
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLot.registerParkingLotObserver(parkingLotOwner);
        try {
            parkingLot.park(vehicle, new Date());
            parkingLot.park(vehicle2, new Date());
        } catch (ParkingLotException e) {
            parkingLot.unPark(vehicle);
            boolean sizeFulled = parkingLotOwner.sizeFulled();
            Assert.assertTrue(sizeFulled);
        }
    }

    @Test
    public void givenParkingLot_OwnerHaveParkingAttendant_ThatCan_ParkCar() {
        Object vehicle2 = new Object();
        ParkingLotOwner parkingAttendant = new ParkingLotOwner();
        parkingLot.parkingAttendantToParkTheCar(parkingAttendant);
        try {
            parkingLot.park(vehicle, new Date());
            parkingLot.park(vehicle2, new Date());
        } catch (ParkingLotException e) {
            boolean sizeFulled = parkingAttendant.sizeFulled();
            boolean parkCar = parkingAttendant.vehicleParked();
            Assert.assertTrue(parkCar && sizeFulled);
        }
    }

    @Test
    public void givenParkingLot_DriverFindHisCar_shouldReturnTrue() {
        Object vehicle2 = new Object();
        try {
            parkingLot.park(vehicle, new Date());
            parkingLot.park(vehicle2, new Date());
            boolean findMyCar = parkingLot.findMyCar(vehicle2);
            Assert.assertTrue(findMyCar);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_VehicleNotFound_ShouldReturnFalse() {
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        try {
            parkingLot.park(vehicle, new Date());
            parkingLot.park(vehicle2, new Date());
            boolean findMyCar = parkingLot.findMyCar(vehicle3);
            Assert.assertFalse(findMyCar);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_ParkingOwnerWantToKnow_WhenCarIsPark() {
        ParkingLotOwner parkingTime = new ParkingLotOwner();
        parkingLot.timeWhenCarIsPark(parkingTime);
        Object vehicle2 = new Object();
        try {
            parkingLot.park(vehicle, new Date());
            parkingLot.park(vehicle2, new Date());
            boolean time = parkingLot.timeWhenCarIsPark(vehicle2);
            Assert.assertTrue(time);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenMultipleCarsLessThanActualCapacity_WhenParkEvenly_shouldReturnLastEmptySpace() {
        try {
           parkingLot.park(vehicle, new Date());
            parkingLot.unPark(vehicle);
            parkingLot.park(new Object(), new Date());
            Object emptyParkingSpace = parkingLot.emptySpaceToParkTheCar().get(0);
            int emptySpaceInParkingLot = parkingLot.emptySlotInParkingLot();
            Assert.assertEquals(emptySpaceInParkingLot,emptyParkingSpace);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
}

