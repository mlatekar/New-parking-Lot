public class AirportSecurity implements ParkingLotObserver {
    private boolean isFullCapacity;

    @Override
    public void setCapacityFull() {
        isFullCapacity = true;
    }

    @Override
    public boolean isCapacityFull() {
        return this.isFullCapacity;
    }

    @Override
    public boolean setCarParkingTime(int minute) {
        return false;
    }


}
