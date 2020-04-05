public class ParkingLotException extends RuntimeException {

    String exceptionMessage;

    public enum ExceptionTypes {
        PARKING_LOT_FULL, VEHICLE_NOT_FOUND, THIS_COLOUR_OF_VEHICLES_NOT_FOUND, VEHICLE_ALREADY_PARKED,
        PARKING_LOT_IS_EMPTY, VEHICLE_TYPE_NOT_FOUND;
    }

    public ExceptionTypes exceptionTypes;

    public ParkingLotException(String message, ExceptionTypes types) {
        this.exceptionMessage = message;
        this.exceptionTypes = types;
    }
}
