import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {

    ParkingLot parkingLot = null;
     Object vehicle=null;

    @Before
    public void setUp() throws Exception {
        vehicle = new Object();
        parkingLot = new ParkingLot(1);
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
            Assert.assertEquals("Parking Fulled",e.getMessage());
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
        parkingLot.ownerRegistration(owner);
        try{
            parkingLot.park(vehicle);
        }catch (ParkingLotException e){
            e.printStackTrace();
        }
    }
}
