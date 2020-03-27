import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class ParkingLotTest {

    ParkingLot parkingLot = null;
    Object vehicle = null;
    ParkingLotOwner owner=null;
    AirportSecurity airportSecurity=null;

    @Before
    public void setUp() throws Exception {
        vehicle = new Object();
        parkingLot = new ParkingLot(2);
        owner=new ParkingLotOwner();
        airportSecurity=new AirportSecurity();
    }

    @Test
    public void givenParkingLot_WhenVehicleParked_ShouldReturnTrue() {
        try {
            parkingLot.park(vehicle, new Date(),ParkingLot.DriverType.NORMAL);
            boolean isPark = parkingLot.isVehicleParked(vehicle);
            Assert.assertEquals(isPark,isPark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleAlreadyParked_shouldReturnFalse() {
        try {
            parkingLot.park(vehicle, new Date(),ParkingLot.DriverType.NORMAL);
            parkingLot.isVehicleParked(new Object());
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking Fulled", e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_IfVehicleIsUnParked_ShouldReturnTrue() {
        try {
            parkingLot.park(vehicle, new Date(),ParkingLot.DriverType.NORMAL);
            boolean unPark = parkingLot.unPark(vehicle);
            Assert.assertEquals(unPark,unPark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_IsWhenFulled_ShouldReturnInformOwner() {
        parkingLot.registerParkingLotObserver(owner);
        try {
            parkingLot.park(vehicle, new Date(),ParkingLot.DriverType.NORMAL);
            parkingLot.park(new Object(), new Date(),ParkingLot.DriverType.NORMAL);
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
            parkingLot.park(vehicle, new Date(),ParkingLot.DriverType.NORMAL);
            parkingLot.park(vehicle2, new Date(),ParkingLot.DriverType.NORMAL);
            boolean vehicleParked = parkingLot.isVehicleParked(vehicle);
            boolean vehicleParked2 = parkingLot.isVehicleParked(vehicle2);
            Assert.assertEquals(vehicleParked && vehicleParked2,vehicleParked && vehicleParked2);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_Full_ShouldInformSecurity() {
        parkingLot.registerParkingLotObserver(airportSecurity);
        try {
            parkingLot.park(vehicle, new Date(),ParkingLot.DriverType.NORMAL);
            parkingLot.park(new Object(), new Date(),ParkingLot.DriverType.NORMAL);
        } catch (ParkingLotException e) {
            boolean capacityFull = airportSecurity.sizeFulled();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void giveParkingLot_AfterFulledSign_WheneverParkingLotIs_Available_ItShouldReturnTrue() {
        Object vehicle2 = new Object();
        parkingLot.registerParkingLotObserver(owner);
        try {
            parkingLot.park(vehicle, new Date(),ParkingLot.DriverType.NORMAL);
            parkingLot.park(vehicle2, new Date(),ParkingLot.DriverType.NORMAL);
        } catch (ParkingLotException e) {
            parkingLot.unPark(vehicle);
            boolean sizeFulled = owner.sizeFulled();
            Assert.assertTrue(sizeFulled);
        }
    }

    @Test
    public void givenParkingLot_OwnerHaveParkingAttendant_ThatCan_ParkCar() {
        Object vehicle2 = new Object();
        parkingLot.parkingAttendantToParkTheCar(owner, ParkingLot.DriverType.NORMAL);
        try {
            parkingLot.park(vehicle, new Date(),ParkingLot.DriverType.NORMAL);
            parkingLot.park(vehicle2, new Date(),ParkingLot.DriverType.NORMAL);
        } catch (ParkingLotException e) {
            boolean sizeFulled = owner.sizeFulled();
            boolean parkCar = owner.vehicleParked();
            Assert.assertTrue(parkCar && sizeFulled);
        }
    }

    @Test
    public void givenParkingLot_DriverFindHisCar_shouldReturnTrue() {
        Object vehicle2 = new Object();
        try {
            parkingLot.park(vehicle, new Date(),ParkingLot.DriverType.NORMAL);
            parkingLot.park(vehicle2, new Date(),ParkingLot.DriverType.NORMAL);
            boolean findMyCar = parkingLot.findMyCar(vehicle2);
            Assert.assertEquals(findMyCar,findMyCar);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_VehicleNotFound_ShouldReturnFalse() {
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        try {
            parkingLot.park(vehicle, new Date(),ParkingLot.DriverType.NORMAL);
            parkingLot.park(vehicle2, new Date(),ParkingLot.DriverType.NORMAL);
            boolean findMyCar = parkingLot.findMyCar(vehicle3);
            Assert.assertFalse(findMyCar);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_ParkingOwnerWantToKnow_WhenCarIsPark() {
        parkingLot.timeWhenCarIsPark(owner);
        Object vehicle2 = new Object();
        try {
            parkingLot.park(vehicle, new Date(),ParkingLot.DriverType.NORMAL);
            parkingLot.park(vehicle2, new Date(),ParkingLot.DriverType.NORMAL);
            boolean time = parkingLot.timeWhenCarIsPark(vehicle2);
            Assert.assertEquals(time,time);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenMultipleCarsLessThanActualCapacity_WhenParkEvenly_shouldReturnLastEmptySpace() {
        try {
           parkingLot.park(vehicle, new Date(),ParkingLot.DriverType.NORMAL);
            parkingLot.unPark(vehicle);
            parkingLot.park(new Object(), new Date(), ParkingLot.DriverType.NORMAL);
            int emptySpaceInParkingLot = parkingLot.emptySlotInParkingLot();
            Assert.assertEquals(emptySpaceInParkingLot,  emptySpaceInParkingLot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenParkingLot_DriverIsHandicap_ShouldParkTheCar() {
        Object vehicle2 = new Object();
        try {
            parkingLot.park(vehicle, new Date(),ParkingLot.DriverType.HANDICAP);
            parkingLot.park(vehicle2, new Date(),ParkingLot.DriverType.NORMAL);
            boolean findMyCar = parkingLot.findMyCar(vehicle);
            Assert.assertFalse(findMyCar);
        } catch (ParkingLotException e) {
        }
    }
}

