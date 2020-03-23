import java.util.Date;

public class ParkingLotOwner implements ParkingLotObserver {
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


    public boolean vehicleParked() {
        sizeFull=true;
        return false;
    }



}
