import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
            parkingLot.park(vehicle);
            boolean isPark = parkingLot.isVehicleParked(vehicle);
            Assert.assertTrue(isPark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleAlreadyParked_shouldReturnFalse() {
        try {
            parkingLot.park(vehicle);
            boolean isPark = parkingLot.isVehicleParked(new Object());
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking Fulled", e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_IfVehicleIsUnParked_ShouldReturnTrue() {
        try {
            parkingLot.park(vehicle);
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
            parkingLot.park(vehicle);
            parkingLot.park(new Object());
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
            parkingLot.park(vehicle);
            parkingLot.park(vehicle2);
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
            parkingLot.park(vehicle);
            parkingLot.park(new Object());
        }catch (ParkingLotException e) {
            boolean capacityFull=airportSecurity.sizeFulled();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void giveParkingLot_AfterFulledSign_WheneverParkingLotIs_Available_ItShouldReturnTrue() {
        Object vehicle2 = new Object();
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLot.registerParkingLotObserver(parkingLotOwner);
        try{
            parkingLot.park(vehicle);
            parkingLot.park(vehicle2);
        }catch (ParkingLotException e) {
            parkingLot.unPark(vehicle);
            boolean sizeFulled = parkingLotOwner.sizeFulled();
            Assert.assertTrue(sizeFulled);
        }

    }
}
