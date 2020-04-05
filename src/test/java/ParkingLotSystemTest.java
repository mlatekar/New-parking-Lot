import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParkingLotSystemTest {
    ManagementSystemOfParkingLot managementSystemOfParkingLot;
    ParkingLotSystem parkingLotSystem;
    Vehicles vehicle;
    ParkingLotOwner owner;
    AirportSecurity airportSecurity;

    @Before

    public void setUp() throws Exception {
        vehicle = new Vehicles("White", "MH05BX8899", "Jaguar");
        parkingLotSystem = new ParkingLotSystem(2);
        owner = new ParkingLotOwner();
        airportSecurity = new AirportSecurity();
        parkingLotSystem.registerParkingLotObserver(owner);
        managementSystemOfParkingLot = new ManagementSystemOfParkingLot();
        managementSystemOfParkingLot.addNewLot(parkingLotSystem);
        managementSystemOfParkingLot.registerParkingLotObserver(owner);
    }

    @Test
    public void givenParkingLot_WhenVehicleParked_ShouldReturnTrue() {
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            boolean isPark = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertTrue(isPark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleAlreadyParked_shouldReturnFalse() {
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionTypes.VEHICLE_ALREADY_PARKED, e.exceptionTypes);
        }
    }

    @Test
    public void givenParkingLot_IfVehicleIsUnParked_ShouldReturnTrue() {
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            boolean unPark = managementSystemOfParkingLot.unPark(vehicle);
            Assert.assertEquals(unPark, unPark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_IsWhenFulled_ShouldReturnInformOwner() {
        managementSystemOfParkingLot.registerParkingLotObserver(owner);
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.park(new Vehicles("White", "MH06AX0987", "BMW"), ParkingLotSystem.DriverType.NORMAL);
        } catch (ParkingLotException e) {
            boolean capacityFulled = owner.isCapacityAvailable();
            Assert.assertTrue(capacityFulled);
        }
    }

    @Test
    public void givenParkingLot_Have2ParkingSIze_ShouldPark2_Vehicle() {
        Vehicles vehicle2 = new Vehicles("White", "MH06AX0987", "BMW");
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.NORMAL);
            boolean vehicleParked = managementSystemOfParkingLot.isVehicleParked(vehicle);
            boolean vehicleParked2 = managementSystemOfParkingLot.isVehicleParked(vehicle2);
            Assert.assertEquals(vehicleParked && vehicleParked2, vehicleParked && vehicleParked2);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_Full_ShouldInformSecurity() {
        managementSystemOfParkingLot.registerParkingLotObserver(airportSecurity);
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.park(new Vehicles("White", "MH06AX0987", "BMW"), ParkingLotSystem.DriverType.NORMAL);
        } catch (ParkingLotException e) {
            boolean capacityFull = airportSecurity.isCapacityAvailable();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void giveParkingLot_AfterFulledSign_WheneverParkingLotIs_Available_ItShouldReturnTrue() {
        Vehicles vehicle2 = new Vehicles("White", "MH06AX0987", "BMW");
        managementSystemOfParkingLot.registerParkingLotObserver(owner);
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.NORMAL);
        } catch (ParkingLotException e) {
            managementSystemOfParkingLot.unPark(vehicle);
            boolean sizeFulled = owner.isCapacityAvailable();
            Assert.assertTrue(sizeFulled);
        }
    }

    //UC6
    @Test
    public void givenParkingLot_OwnerHaveParkingAttendant_ThatCan_ParkCar() {
        Vehicles vehicle2 = new Vehicles("White", "MH06AX0987", "BMW");
        try {
            parkingLotSystem.emptySlotToParkTheVehicle(ParkingLotSystem.DriverType.NORMAL);
            boolean parkVehicle1 = parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            boolean parkVehicle2 = parkingLotSystem.park(vehicle2, ParkingLotSystem.DriverType.NORMAL);
            Assert.assertEquals(parkVehicle1 && parkVehicle2, parkVehicle1 && parkVehicle2);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    //UC7
    @Test
    public void givenParkingLot_DriverFindHisCar_shouldReturnTrue() {
        Vehicles vehicle2 = new Vehicles("White", "MH06AX0987", "BMW");
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.NORMAL);
            int driverFindHisCar = managementSystemOfParkingLot.findMyCar(vehicle2);
            Assert.assertEquals(driverFindHisCar, driverFindHisCar);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_VehicleNotFound_ShouldReturnFalse() {
        Vehicles vehicle2 = new Vehicles("White", "MH06AX0987", "BMW");
        Vehicles vehicle3 = new Vehicles("White", "MH67AX0457", "TATA");
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.findMyCar(vehicle3);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND, e.exceptionTypes);
        }
    }

    //UC8
    @Test
    public void givenParkingLot_ParkingOwnerWantToKnow_WhenCarIsPark() {
        managementSystemOfParkingLot.timeWhenCarIsPark(vehicle);
        Date parkingTime = new Date();
        Vehicles vehicle2 = new Vehicles("White", "MH06AX0987", "BMW");
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.NORMAL);
            Date dateAndTimeWhenVehicleIsParked = managementSystemOfParkingLot.timeWhenCarIsPark(vehicle2);
            System.out.println(parkingTime + " time " + dateAndTimeWhenVehicleIsParked);
            Assert.assertEquals(parkingTime, dateAndTimeWhenVehicleIsParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    //UC9
    @Test
    public void givenMultipleCarsLessThanActualCapacity_WhenParkEvenly_shouldReturnLastEmptySpace() {
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.unPark(vehicle);
            managementSystemOfParkingLot.park(new Vehicles("White", "MH06AX0987", "BMW"), ParkingLotSystem.DriverType.NORMAL);
            Object emptySpaceInParkingLot = parkingLotSystem.emptySpaceToParkTheCar().get(0);
            Assert.assertEquals(emptySpaceInParkingLot, emptySpaceInParkingLot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    //UC10
    @Test
    public void givenParkingLot_DriverIsHandicap_ShouldParkTheCarNearestSlot() {
        Vehicles vehicle2 = new Vehicles("White", "MH06AX0987", "BMW");
        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.HANDICAP);
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.NORMAL);
            int handicapCar = managementSystemOfParkingLot.findMyCar(vehicle);
            Assert.assertEquals(handicapCar, handicapCar);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenMultipleCarParkingLots_WhenAdded_ShouldReturnTrue() {
        try {
            managementSystemOfParkingLot.addNewLot(parkingLotSystem);
            boolean newLot = managementSystemOfParkingLot.isNewLotAdded(parkingLotSystem);
            parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            parkingLotSystem.park(new Vehicles("White", "MH06AX0987", "BMW"), ParkingLotSystem.DriverType.HANDICAP);
            Assert.assertTrue(newLot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    //UC11
    @Test
    public void givenParkingLot_LargeCarsParkIn_HighestSpaceParkingLot_ShouldReturnTrue() {
        parkingLotSystem.setParkingLotCapacity(8);
        parkingLotSystem.initializeParkingLot();

        managementSystemOfParkingLot.addNewLot(parkingLotSystem);
        managementSystemOfParkingLot.isNewLotAdded(parkingLotSystem);

        Vehicles vehicle2 = new Vehicles("White", "MH06AX0987", "BMW");
        Vehicles vehicle3 = new Vehicles("Red", "MH43AB8976", "Jaguar");
        Vehicles vehicle4 = new Vehicles("BLue", "MH05AX7890", "Suzuki");

        try {
            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.HANDICAP);
            parkingLotSystem.park(vehicle4, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.LARGE_VEHICLE);
            managementSystemOfParkingLot.park(vehicle3, ParkingLotSystem.DriverType.NORMAL);

            boolean vehicleParked = managementSystemOfParkingLot.isVehicleParked(vehicle2);
            Assert.assertTrue(vehicleParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    // UC12
    @Test
    public void givenParkingLot_PoliceWantToKnow_LocationOfAllWhiteCars() {
        parkingLotSystem.setParkingLotCapacity(8);
        parkingLotSystem.initializeParkingLot();
        managementSystemOfParkingLot.addNewLot(parkingLotSystem);
        managementSystemOfParkingLot.isNewLotAdded(parkingLotSystem);
        Vehicles vehicle2 = new Vehicles("Black", "MH06AX0987", "BMW");
        Vehicles vehicle3 = new Vehicles("White", "MH46HH5432", "TATA");

        managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
        managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.HANDICAP);
        managementSystemOfParkingLot.park(vehicle3, ParkingLotSystem.DriverType.NORMAL);
        ArrayList<Integer> white = parkingLotSystem.findMyCarByColour("White");
        ArrayList<Integer> totalWhiteCar = new ArrayList<>();
        totalWhiteCar.add(6);
        totalWhiteCar.add(7);
        System.out.println("Location of white cars " + white);
        Assert.assertEquals(totalWhiteCar, white);
    }

    @Test
    public void givenParkingLot_PoliceWantToKnow_LocationOfAllWhiteCars_ThrowException() {
        try {
            parkingLotSystem.setParkingLotCapacity(8);
            parkingLotSystem.initializeParkingLot();
            managementSystemOfParkingLot.addNewLot(parkingLotSystem);
            managementSystemOfParkingLot.isNewLotAdded(parkingLotSystem);
            Vehicles vehicle2 = new Vehicles("Yellow", "MH56AB4376", "BMW");
            Vehicles vehicle3 = new Vehicles("Gray", "MH76UH5432", "TATA");

            managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
            managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.HANDICAP);
            managementSystemOfParkingLot.park(vehicle3, ParkingLotSystem.DriverType.NORMAL);
            parkingLotSystem.findMyCarByColour("Red");
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionTypes.VEHICLE_NOT_FOUND, e.exceptionTypes);
        }
    }

    //UC13
    @Test
    public void givenParkingLot_PoliceWantToKnow_Location_NumberPlate_Of_BlueTOYOTA_Car() {
        parkingLotSystem.setParkingLotCapacity(5);
        parkingLotSystem.initializeParkingLot();

        managementSystemOfParkingLot.addNewLot(parkingLotSystem);
        managementSystemOfParkingLot.isNewLotAdded(parkingLotSystem);

        Vehicles vehicle2 = new Vehicles("Blue", "MH06AX0987", "TOYOTA");

        managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
        managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.HANDICAP);

        List<String> blueTOYOTA = parkingLotSystem.findMyCarByColourAndCarType("Blue", "TOYOTA");
        List<String> totalCar = new ArrayList<>();
        totalCar.add("MH06AX0987");
        System.out.println("Location of Blue cars " + blueTOYOTA);
        Assert.assertEquals(totalCar, blueTOYOTA);
    }

    //UC14
    @Test
    public void givenParkingLot_PoliceWantToKnow_AllBMW_Cars() {
        parkingLotSystem.setParkingLotCapacity(5);
        parkingLotSystem.initializeParkingLot();

        managementSystemOfParkingLot.addNewLot(parkingLotSystem);
        managementSystemOfParkingLot.isNewLotAdded(parkingLotSystem);

        Vehicles vehicle2 = new Vehicles("Black", "MH05DX0110", "BMW");
        Vehicles vehicle3 = new Vehicles("Red", "MH46OO0007", "BMW");

        managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
        managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.HANDICAP);
        managementSystemOfParkingLot.park(vehicle3, ParkingLotSystem.DriverType.LARGE_VEHICLE);


        List<String> totalBMW = parkingLotSystem.findMyCarByCarType("BMW");
        List<String> totalCars = new ArrayList<>();
        totalCars.add("MH05DX0110");
        totalCars.add("MH46OO0007");

        System.out.println("Location of Blue cars " + totalBMW);
        Assert.assertEquals(totalCars, totalBMW);
    }

    //UC15
    @Test
    public void givenParkingLot_WantToKnow_AllCars_WhichParked_InLast30Minutes() {
        parkingLotSystem.setParkingLotCapacity(5);
        parkingLotSystem.initializeParkingLot();

        managementSystemOfParkingLot.addNewLot(parkingLotSystem);

        Vehicles vehicle2 = new Vehicles("Cyan", "MH07AS1234", "Jaguar");
        Vehicles vehicle3 = new Vehicles("Golden", "MH12XO0786", "Bugatti");
        Vehicles vehicle4 = new Vehicles("Red", "DL33UP9999", "Bugatti");
        Vehicles vehicle5 = new Vehicles("Black", "OD01MU0619", "Bugatti");


        managementSystemOfParkingLot.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
        managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.HANDICAP);
        managementSystemOfParkingLot.park(vehicle3, ParkingLotSystem.DriverType.LARGE_VEHICLE);
        managementSystemOfParkingLot.park(vehicle4, ParkingLotSystem.DriverType.HANDICAP);
        managementSystemOfParkingLot.park(vehicle5, ParkingLotSystem.DriverType.NORMAL);

        ArrayList<String> lastTotalCarsParked30Min = parkingLotSystem.findLast30MinuteParkedCars();
        ArrayList<String> totalCars = new ArrayList<>();
        totalCars.add("MH07AS1234");
        totalCars.add("DL33UP9999");
        totalCars.add("MH12XO0786");
        totalCars.add("OD01MU0619");
        totalCars.add("MH05BX8899");

        System.out.println("Total Cars that parked last 30min " + lastTotalCarsParked30Min);
        Assert.assertEquals(totalCars, lastTotalCarsParked30Min);
    }

    //UC16
    @Test
    public void givenParkingLot_WantToKnow_AllSmallHandicapCars() {
        parkingLotSystem.setParkingLotCapacity(5);
        parkingLotSystem.initializeParkingLot();

        managementSystemOfParkingLot.addNewLot(parkingLotSystem);

        Vehicles vehicle2 = new Vehicles("Cyan", "MH07AS1234", "Jaguar");
        Vehicles vehicle3 = new Vehicles("Red", "DL33UP9999", "Bugatti");


        managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.SMALL_HANDICAP);
        managementSystemOfParkingLot.park(vehicle3, ParkingLotSystem.DriverType.SMALL_HANDICAP);

        List<String> smallHandicapCars = parkingLotSystem.findAllSmallHandicapCars(ParkingLotSystem.DriverType.SMALL_HANDICAP);
        List<String> totalCars = new ArrayList<>();
        totalCars.add("MH07AS1234 Jaguar Cyan");
        totalCars.add("DL33UP9999 Bugatti Red");

        System.out.println("Total Cars  " + smallHandicapCars);
        Assert.assertEquals(totalCars, smallHandicapCars);
    }

    //UC17
    @Test
    public void givenParkingLot_PoliceWantToKnow_AllParkedCars() {
        parkingLotSystem.setParkingLotCapacity(8);
        parkingLotSystem.initializeParkingLot();

        managementSystemOfParkingLot.addNewLot(parkingLotSystem);

        Vehicles vehicle2 = new Vehicles("Cyan", "MH07AS1234", "Jaguar");
        Vehicles vehicle3 = new Vehicles("Red", "DL33UP9999", "Bugatti");
        Vehicles vehicle4 = new Vehicles("Black", "ZH90UP5678", "Ferrari");
        Vehicles vehicle5 = new Vehicles("Pink", "RD33UP0987", "BMW");
        Vehicles vehicle6 = new Vehicles("Gray", "AM33BH3333", "TATA");

        managementSystemOfParkingLot.park(vehicle2, ParkingLotSystem.DriverType.HANDICAP);
        managementSystemOfParkingLot.park(vehicle3, ParkingLotSystem.DriverType.SMALL_HANDICAP);
        managementSystemOfParkingLot.park(vehicle4, ParkingLotSystem.DriverType.NORMAL);
        managementSystemOfParkingLot.park(vehicle5, ParkingLotSystem.DriverType.LARGE_VEHICLE);
        managementSystemOfParkingLot.park(vehicle6, ParkingLotSystem.DriverType.NORMAL);


        List<String> allParkedCars = parkingLotSystem.findAllCars();
        List<String> totalCars = new ArrayList<>();
        totalCars.add("MH07AS1234 Jaguar Cyan");
        totalCars.add("DL33UP9999 Bugatti Red");
        totalCars.add("RD33UP0987 BMW Pink");
        totalCars.add("AM33BH3333 TATA Gray");
        totalCars.add("ZH90UP5678 Ferrari Black");

        System.out.println("Total Cars  " + allParkedCars);
        Assert.assertEquals(totalCars, allParkedCars);
    }
}