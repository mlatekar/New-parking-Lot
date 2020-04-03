import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class TestParkingLotSystem {
    @Mock
    ManagementSystemOfParkingLot managementSystemOfParkingLot;
    ParkingLotSystem parkingLotSystem;
    ParkingLotSystem.DriverType driverType;
    Object vehicle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        managementSystemOfParkingLot = mock(ManagementSystemOfParkingLot.class);
        vehicle = new Object();
        parkingLotSystem = new ParkingLotSystem(2);
    }

    @Test
    public void when_Called_ParkFunction_ShouldParkTheVehicle() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL,"White");
            return null;
        }).when(managementSystemOfParkingLot).park(vehicle, driverType.NORMAL,"White");
        boolean isParked = parkingLotSystem.park(vehicle, driverType.NORMAL,"White");
        assertTrue(isParked);
    }

    @Test
    public void when_Called_UnParkFunction_ShouldParkTheVehicle() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLotSystem.unPark(vehicle,"White");
            return null;
        }).when(managementSystemOfParkingLot).unPark(vehicle,"White");
        parkingLotSystem.park(vehicle, driverType.NORMAL,"White");
        boolean isParked = parkingLotSystem.unPark(vehicle,"White");
        assertTrue(isParked);
    }

    @Test
    public void when_Called_FindVehicleFunction_ShouldParkTheVehicle() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLotSystem.findMyCar(vehicle,"White");
            return null;
        }).when(managementSystemOfParkingLot).findMyCar(vehicle,"White");
        try {
            parkingLotSystem.findMyCar(vehicle,"White");
        } catch (ParkingLotException e) {
            assertSame(ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND, e.exceptionTypes);
        }
    }

    @Test
    public void when_Called_ParkFunction_ShouldParkLargeVehicle() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.LARGE_VEHICLE,"White");
            return null;
        }).when(managementSystemOfParkingLot).park(vehicle, driverType.LARGE_VEHICLE,"White");
        boolean isParked = parkingLotSystem.park(vehicle, driverType.LARGE_VEHICLE,"White");
        assertTrue(isParked);
    }

}
