import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class ParkingLotInformerTest {
    @Mock
    ParkingLotSystem parkingLotSystem;
    ParkingLotInformer informer;
    ParkingLotOwner owner;
    AirportSecurity security;
    Vehicles vehicle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        parkingLotSystem = mock(ParkingLotSystem.class);
        security = new AirportSecurity();
        owner = new ParkingLotOwner();
        informer = new ParkingLotInformer();
        vehicle = new Vehicles("White","MH06AX0987","BMW");
    }

    @Test
    public void return_isParkCapacityFull() {
        informer.registerParkingLotObserver(owner);
        informer.registerParkingLotObserver(security);
        doAnswer((Answer<Void>) invocationOnMock -> {
            informer.notifyParkCapacityFull();
            return null;
        }).when(parkingLotSystem).park(vehicle, ParkingLotSystem.DriverType.NORMAL);
        parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
        Assert.assertTrue(owner.isParkingLotFull() && security.isParkingLotFull());
    }

    @Test
    public void return_isParkCapacityAvailable() {
        informer.registerParkingLotObserver(owner);
        informer.registerParkingLotObserver(security);
        doAnswer((Answer<Void>) invocationOnMock -> {
            informer.notifyParkCapacityAvailable();
            return null;
        }).when(parkingLotSystem).park(vehicle, ParkingLotSystem.DriverType.NORMAL);
        parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
        Assert.assertFalse(owner.isParkingLotFull() && security.isParkingLotFull());
    }
}
