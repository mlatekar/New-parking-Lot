public class AirportSecurity implements ParkingLotObserver {
    private boolean isFullCapacity;

    @Override
    public void setCapacityFull() {
        isFullCapacity = true;
    }

    @Override
    public boolean isCapacityAvailable() {
        return this.isFullCapacity;
    }

    @Override
    public boolean isParkingLotFull() {
        return isFullCapacity;
    }
}
