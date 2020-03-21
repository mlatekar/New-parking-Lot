import ParkingLot.ParkingLot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {

    ParkingLot parkingLot=null;
    private Object vehicle;

    @Before
    public void setUp() throws Exception {
        vehicle=new Object();
        parkingLot=new ParkingLot();
    }

    @Test
    public void givenParkingLot_WhenVehicleParked_ShouldReturnTrue() {
        boolean isPark = parkingLot.park(new Object());
        Assert.assertTrue(isPark);
    }

    @Test
    public void givenParkingLot_WhenVehicleAlreadyParked_shouldReturnFalse() {
        parkingLot.park(vehicle);
        boolean isPark = parkingLot.park(new Object());
        Assert.assertFalse(isPark);
    }

    @Test
    public void givenParkingLot_IfVehicleIsUnParked_ShouldReturnTrue() {
        parkingLot.park(vehicle);
        boolean unPark = parkingLot.unPark(vehicle);
        Assert.assertTrue(unPark);
    }
}
