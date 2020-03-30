import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ParkingLotMockitoTest {

    @Mock
    ParkingLot parkingLot;
    ParkingLotOwner owner;
    AirportSecurity airportSecurity;
    Object vehicle;
    Date date;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        parkingLot = mock(ParkingLot.class);
        owner = new ParkingLotOwner();
        airportSecurity =new AirportSecurity();
        vehicle = new Object();
        date = new Date();
    }
    @Test
    public void return_isCapacityFullFunction_InAirportSecurity() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            airportSecurity.setCapacityFull();
            return null;
        }).when(parkingLot).park(vehicle, ParkingLot.DriverType.NORMAL);
        parkingLot.park(vehicle, ParkingLot.DriverType.NORMAL);
        Assert.assertTrue(airportSecurity.isCapacityFull());
    }


    @Test(expected = ParkingLotException.class)
    public void testParkingLotExceptionClass_ThrowParkingLotException_WhenCallingParkFunction() {
        doThrow(ParkingLotException.class)
                .when(parkingLot).park(any(), any(ParkingLot.DriverType.class));
        parkingLot.park(1, ParkingLot.DriverType.NORMAL);
    }
    @Test
    public void return_isCapacityFullFunction_InParkingLotOwner() {
        doAnswer((Answer<Void>) invocationOnMock -> {
            owner.setCapacityFull();
            return null;
        }).when(parkingLot).park(vehicle, ParkingLot.DriverType.NORMAL);
        parkingLot.park(vehicle, ParkingLot.DriverType.NORMAL);
        assertTrue(owner.isCapacityFull());
    }
    @Test
    public void test_WhenVehicleObjectPassedToParkFunction_VehicleAlReadyParkedException_ThrowAnException() {
        when(parkingLot.park(any(),any())).thenAnswer(
                (Answer) invocation -> {
                    if (invocation.getArgument(0).equals(vehicle)){
                        throw new ParkingLotException("",ParkingLotException.ExceptionTypes.VEHICLE_ALREADY_PARKED);
                    }
                    throw new ParkingLotException("",ParkingLotException.ExceptionTypes.PARKING_LOT_FULL);
                });
        try {
            parkingLot.park(vehicle, ParkingLot.DriverType.NORMAL);
        }
        catch (ParkingLotException e)
        {
            Assert.assertEquals(e.exceptionTypes,ParkingLotException.ExceptionTypes.VEHICLE_ALREADY_PARKED);
        }
    }
    @Test
    public void test_WhenAnotherObjectPassedToParkFunction_ParkingLotFullException_ThrowAnException() {
        when(parkingLot.park(any(),any())).thenAnswer(
                (Answer) invocation -> {
                    if (invocation.getArgument(0)==vehicle){
                        throw new ParkingLotException("",ParkingLotException.ExceptionTypes.VEHICLE_ALREADY_PARKED);
                    }
                    throw new ParkingLotException("",ParkingLotException.ExceptionTypes.PARKING_LOT_FULL);
                });
        try {
            parkingLot.park(new Object(), ParkingLot.DriverType.NORMAL);
        }
        catch (ParkingLotException e)
        {
            Assert.assertEquals(e.exceptionTypes,ParkingLotException.ExceptionTypes.PARKING_LOT_FULL);
        }
    }

    @Test
    public void test_WhenNewObjectPassedToUnParkFunctionNotMatchesVehicleObject_VehicleNotFoundException_ThrowVehicleNotFoundException() {
        when(parkingLot.unPark(any())).thenAnswer(
                (Answer) invocation -> {
                    if (invocation.getArgument(0)==vehicle){
                        return "unParked";
                    }
                    throw new ParkingLotException("",ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
                });
        try {
            parkingLot.unPark(new Object());
        }
        catch (ParkingLotException e)
        {
            Assert.assertEquals(e.exceptionTypes,ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND);
        }
    }
}
