import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class AirPortSecurityTest {
    @Mock
    ParkingLotInformer informer;
    AirportSecurity airportSecurity;
    Object vehicle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        informer = mock(ParkingLotInformer.class);
        airportSecurity = new AirportSecurity();
        vehicle = new Object();
    }

    @Test
    public void return_isCapacityFullFunction_InAirportSecurity() {
        informer.registerParkingLotObserver(airportSecurity);
        doAnswer((Answer<Void>) invocationOnMock -> {
            airportSecurity.setCapacityFull();
            return null;
        }).when(informer).notifyParkCapacityFull();
        informer.notifyParkCapacityFull();
        assertTrue(airportSecurity.isParkingLotFull());
    }
}