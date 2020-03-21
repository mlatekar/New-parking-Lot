import org.junit.Assert;
import org.junit.Test;

public class ParkingLotTest {
    @Test
    public void givenParkingLot_WhenVehicleParked_ShouldReturnTrue() {
        ParkingLot parkingLot = new ParkingLot();
        boolean park = ParkingLot.park(new Object());
        Assert.assertTrue(park);
    }
}
