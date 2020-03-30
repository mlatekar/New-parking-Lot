
public interface ParkingLotObserver {
    void setCapacityFull();

    boolean isCapacityFull();


    boolean setCarParkingTime(int minute);
}
