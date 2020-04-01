import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class ParkingLotOwnerTest {
    @Mock
    ParkingLotInformer informer;
    ParkingLotOwner owner;
    Object vehicle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        informer = mock(ParkingLotInformer.class);
        owner = new ParkingLotOwner();
        vehicle = new Object();
    }

    @Test
    public void return_isCapacityAvailable_InParkingLotOwner() {
        informer.registerParkingLotObserver(owner);
        doAnswer((Answer<Void>) invocationOnMock -> {
            owner.isCapacityAvailable();
            return null;
        }).when(informer).notifyParkCapacityAvailable();
        informer.notifyParkCapacityAvailable();
        assertFalse(owner.isParkingLotFull());
    }

    @Test
    public void return_isCapacityFullFunction_InParkingLotOwner() {
        informer.registerParkingLotObserver(owner);
        doAnswer((Answer<Void>) invocationOnMock -> {
            owner.setCapacityFull();
            return null;
        }).when(informer).notifyParkCapacityFull();
        informer.notifyParkCapacityFull();
        assertTrue(owner.isParkingLotFull());
    }
}