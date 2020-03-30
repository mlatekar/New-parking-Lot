public class ParkingLotOwner implements ParkingLotObserver {
    private boolean isFullCapacity;
    private int parkingTime;

    @Override
    public void setCapacityFull() {
        isFullCapacity = true;
    }

    @Override
    public boolean isCapacityFull() {
        return isFullCapacity;
    }

    @Override
    public boolean setCarParkingTime(int minute) {
        return false;
    }
}
