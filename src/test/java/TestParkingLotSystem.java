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
    Vehicles vehicle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        managementSystemOfParkingLot = mock(ManagementSystemOfParkingLot.class);
        vehicle = new Vehicles("White");
        parkingLotSystem = new ParkingLotSystem(2);
    }

    @Test
    public void when_Called_ParkFunction_ShouldParkTheVehicle() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            return null;
        }).when(managementSystemOfParkingLot).park(vehicle, driverType.NORMAL);
        boolean isParked = parkingLotSystem.park(vehicle, driverType.NORMAL);
        assertTrue(isParked);
    }

    @Test
    public void when_Called_UnParkFunction_ShouldParkTheVehicle() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLotSystem.unPark(vehicle);
            return null;
        }).when(managementSystemOfParkingLot).unPark(vehicle);
        parkingLotSystem.park(vehicle, driverType.NORMAL);
        boolean isParked = parkingLotSystem.unPark(vehicle);
        assertTrue(isParked);
    }

    @Test
    public void when_Called_FindVehicleFunction_ShouldParkTheVehicle() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLotSystem.findMyCar(vehicle);
            return null;
        }).when(managementSystemOfParkingLot).findMyCar(vehicle);
        try {
            parkingLotSystem.findMyCar(vehicle);
        } catch (ParkingLotException e) {
            assertSame(ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND, e.exceptionTypes);
        }
    }

    @Test
    public void when_Called_ParkFunction_ShouldParkLargeVehicle() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.LARGE_VEHICLE);
            return null;
        }).when(managementSystemOfParkingLot).park(vehicle, driverType.LARGE_VEHICLE);
        boolean isParked = parkingLotSystem.park(vehicle, driverType.LARGE_VEHICLE);
        assertTrue(isParked);
    }

}
