import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

public class ParkingLotExceptionTest {
    @Mock
    ParkingLotSystem parkingLotSystem;
    ParkingLotOwner owner;
    Object vehicle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        parkingLotSystem = mock(ParkingLotSystem.class);
        owner = new ParkingLotOwner();
        vehicle = new Object();
    }

    @Test
    public void when_Check_For_NewObjectPassedToUnParkFunctionNotMatchesVehicleObject_VehicleNotFoundException_ThrowVehicleNotFoundException() {
        when(parkingLotSystem.unPark(any(),any())).thenAnswer(
                (Answer) invocation -> {
                    if (invocation.getArgument(0) == vehicle) {
                        return "vehicle is unParked";
                    }
                    throw new ParkingLotException("", ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
                });
        try {
            parkingLotSystem.unPark(new Object(),any());
        } catch (ParkingLotException e) {
            Assert.assertEquals(e.exceptionTypes, ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
        }
    }

    @Test
    public void when_Check_For_AnotherObjectPassedToParkFunction_ParkingLotFullException_ThrowAnException() {
        when(parkingLotSystem.park(any(), any(),any())).thenAnswer(
                (Answer) invocation -> {
                    if (invocation.getArgument(0) == vehicle) {
                        throw new ParkingLotException("", ParkingLotException.ExceptionTypes.VEHICLE_ALREADY_PARKED);
                    }
                    throw new ParkingLotException("", ParkingLotException.ExceptionTypes.PARKING_LOT_FULL);
                });
        try {
            parkingLotSystem.park(new Object(), ParkingLotSystem.DriverType.NORMAL,"White");
        } catch (ParkingLotException e) {
            Assert.assertEquals(e.exceptionTypes, ParkingLotException.ExceptionTypes.PARKING_LOT_FULL);
        }
    }

    @Test
    public void when_Check_For_VehicleObjectPassedToParkFunction_VehicleAlReadyParkedException_ThrowAnException() {
        when(parkingLotSystem.park(any(), any(),any())).thenAnswer(
                (Answer) invocation -> {
                    if (invocation.getArgument(0).equals(vehicle)) {
                        throw new ParkingLotException("", ParkingLotException.ExceptionTypes.VEHICLE_ALREADY_PARKED);
                    }
                    throw new ParkingLotException("", ParkingLotException.ExceptionTypes.PARKING_LOT_FULL);
                });
        try {
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL,"White");
        } catch (ParkingLotException e) {
            Assert.assertEquals(e.exceptionTypes, ParkingLotException.ExceptionTypes.VEHICLE_ALREADY_PARKED);
        }
    }

    @Test(expected = ParkingLotException.class)
    public void when_Check_For_ParkingLotExceptionClass_ThrowParkingLotException_WhenCallingParkFunction() {
        doThrow(ParkingLotException.class)
                .when(parkingLotSystem).park(any(), any(ParkingLotSystem.DriverType.class),any());
        parkingLotSystem.park(1, ParkingLotSystem.DriverType.NORMAL,"White");
    }
}
