import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class ParkingLotTest {

    ParkingLot parkingLot;
    Object vehicle;
    ParkingLotOwner owner;
    AirportSecurity airportSecurity;

    @Before
    public void setUp() throws Exception {
        vehicle = new Object();
        parkingLot = new ParkingLot(2);
        owner = new ParkingLotOwner();
        airportSecurity = new AirportSecurity();
        parkingLot.registerParkingLotObserver(owner);
    }

    @Test
    public void givenParkingLot_WhenVehicleParked_ShouldReturnTrue() {
        try {
            parkingLot.park(vehicle, ParkingLot.DriverType.NORMAL);
            boolean isPark = parkingLot.isVehicleParked(vehicle);
            Assert.assertTrue(isPark);

        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleAlreadyParked_shouldReturnFalse() {
        try {
            parkingLot.park(vehicle, ParkingLot.DriverType.NORMAL);
            parkingLot.park(vehicle, ParkingLot.DriverType.NORMAL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionTypes.VEHICLE_ALREADY_PARKED, e.exceptionTypes);
        }
    }

    @Test
    public void givenParkingLot_IfVehicleIsUnParked_ShouldReturnTrue() {
        try {
            parkingLot.park(vehicle, ParkingLot.DriverType.NORMAL);
            boolean unPark = parkingLot.unPark(vehicle);
            Assert.assertEquals(unPark, unPark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_IsWhenFulled_ShouldReturnInformOwner() {
        parkingLot.registerParkingLotObserver(owner);
        try {
            parkingLot.park(vehicle, ParkingLot.DriverType.NORMAL);
            parkingLot.park(new Object(), ParkingLot.DriverType.NORMAL);
        } catch (ParkingLotException e) {
            boolean capacityFulled = owner.isCapacityFull();
            Assert.assertTrue(capacityFulled);
        }
    }

    @Test
    public void givenParkingLot_Have2ParkingSIze_ShouldPark2_Vehicle() {
        Object vehicle2 = new Object();
        try {
            parkingLot.park(vehicle, ParkingLot.DriverType.NORMAL);
            parkingLot.park(vehicle2, ParkingLot.DriverType.NORMAL);
            boolean vehicleParked = parkingLot.isVehicleParked(vehicle);
            boolean vehicleParked2 = parkingLot.isVehicleParked(vehicle2);
            Assert.assertEquals(vehicleParked && vehicleParked2, vehicleParked && vehicleParked2);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_Full_ShouldInformSecurity() {
        parkingLot.registerParkingLotObserver(airportSecurity);
        try {
            parkingLot.park(vehicle, ParkingLot.DriverType.NORMAL);
            parkingLot.park(new Object(), ParkingLot.DriverType.NORMAL);
        } catch (ParkingLotException e) {
            boolean capacityFull = airportSecurity.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void giveParkingLot_AfterFulledSign_WheneverParkingLotIs_Available_ItShouldReturnTrue() {
        Object vehicle2 = new Object();
        parkingLot.registerParkingLotObserver(owner);
        try {
            parkingLot.park(vehicle, ParkingLot.DriverType.NORMAL);
            parkingLot.park(vehicle2, ParkingLot.DriverType.NORMAL);
        } catch (ParkingLotException e) {
            parkingLot.unPark(vehicle);
            boolean sizeFulled = owner.isCapacityFull();
            Assert.assertTrue(sizeFulled);
        }
    }

    @Test
    public void givenParkingLot_OwnerHaveParkingAttendant_ThatCan_ParkCar() {
        Object vehicle2 = new Object();
        parkingLot.emptySlotToParkTheVehicle(ParkingLot.DriverType.NORMAL);
        try {
            parkingLot.park(vehicle, ParkingLot.DriverType.NORMAL);
            parkingLot.park(vehicle2, ParkingLot.DriverType.NORMAL);
        } catch (ParkingLotException e) {
            boolean sizeFulled = owner.isCapacityFull();
            Assert.assertTrue(sizeFulled);
        }
    }

    @Test
    public void givenParkingLot_DriverFindHisCar_shouldReturnTrue() {
        Object vehicle2 = new Object();
        try {
            parkingLot.park(vehicle, ParkingLot.DriverType.NORMAL);
            parkingLot.park(vehicle2, ParkingLot.DriverType.NORMAL);
            int driverFindHisCar = parkingLot.findMyCar(vehicle2);
            Assert.assertEquals(driverFindHisCar, driverFindHisCar);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_VehicleNotFound_ShouldReturnFalse() {
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        try {
            parkingLot.park(vehicle, ParkingLot.DriverType.NORMAL);
            parkingLot.park(vehicle2, ParkingLot.DriverType.NORMAL);
            int driverCanNotFindHisCar = parkingLot.findMyCar(vehicle3);
            Assert.assertEquals(driverCanNotFindHisCar, driverCanNotFindHisCar);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_ParkingOwnerWantToKnow_WhenCarIsPark() {
        parkingLot.timeWhenCarIsPark(owner);
        //  Date date = new Date();
        Date parkingTime = new Date();
        Object vehicle2 = new Object();
        try {
            parkingLot.park(vehicle, ParkingLot.DriverType.NORMAL);
            parkingLot.park(vehicle2, ParkingLot.DriverType.NORMAL);
            Date time = parkingLot.timeWhenCarIsPark(vehicle2);
            System.out.println(parkingTime + " time " + time);
            Assert.assertEquals(parkingTime, time);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenMultipleCarsLessThanActualCapacity_WhenParkEvenly_shouldReturnLastEmptySpace() {
        try {
            parkingLot.park(vehicle, ParkingLot.DriverType.NORMAL);
            parkingLot.unPark(vehicle);
            parkingLot.park(new Object(), ParkingLot.DriverType.NORMAL);
            Object emptySpaceInParkingLot = parkingLot.emptySpaceToParkTheCar().get(0);
            Assert.assertEquals(emptySpaceInParkingLot, emptySpaceInParkingLot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenParkingLot_DriverIsHandicap_ShouldParkTheCarNearestSlot() {
        Object vehicle2 = new Object();
        try {
            parkingLot.park(vehicle, ParkingLot.DriverType.HANDICAP);
            parkingLot.park(vehicle2, ParkingLot.DriverType.NORMAL);
            int handicapCar = parkingLot.findMyCar(vehicle);
            Assert.assertEquals(handicapCar,handicapCar);
        } catch (ParkingLotException e) {
        }
    }
}

