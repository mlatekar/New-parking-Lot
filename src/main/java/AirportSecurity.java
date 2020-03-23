public class AirportSecurity implements ParkingLotObserver {
    private boolean sizeFull;

    @Override
    public boolean sizeFulled() {
        sizeFull = true;
        return false;
    }

    @Override
    public void sizeAvailable() {
        sizeFull = false;
    }

    public boolean isCapacityFulled() {
        return this.isCapacityFulled();
    }
}
