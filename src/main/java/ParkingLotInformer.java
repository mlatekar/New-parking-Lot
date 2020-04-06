import java.util.ArrayList;
import java.util.List;

public class ParkingLotInformer {
    static List<ParkingLotObserver> observersList;

    public ParkingLotInformer() {
        observersList = new ArrayList<>();
    }

    public void notifyParkCapacityFull() {
        for (ParkingLotObserver observers : observersList)
            observers.setCapacityFull();
    }

    public void notifyParkCapacityAvailable() {
        for (ParkingLotObserver lotObserver : observersList)
            lotObserver.isCapacityAvailable();
    }

    public void registerParkingLotObserver(ParkingLotObserver observers) {
        observersList.add(observers);
    }
}
