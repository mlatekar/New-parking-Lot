public class ParkingLotOwner implements ParkingLotObserver {
    private boolean isFullCapacity;
    private int parkingTime;

    @Override
    public void setCapacityFull() {
        isFullCapacity = true;
    }

    @Override
    public boolean isCapacityAvailable() {
        return isFullCapacity;
    }

    @Override
    public boolean isParkingLotFull() {
        return isFullCapacity;
    }
}
