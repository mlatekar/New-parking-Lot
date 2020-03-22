import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private int currentCapacity;
    private int actualSize;
    private List vehicles;
    private List<ParkingLoyObserver> observers;
    private AirportSecurity security;

    public ParkingLot(int size) {
        this.observers=new ArrayList<>();
        this.vehicles=new ArrayList();
        this.actualSize = size;
    }

    public void registerParkingLotObserver(ParkingLoyObserver observer) {
        this.observers.add(observer);
    }
  /*  public void registrationOfSecurity(AirportSecurity airportSecurity) {
        this.security=airportSecurity;
    }*/
    public void setSizeCapacity(int parkingSize) {
        this.actualSize = parkingSize;
    }

    public void park(Object vehicle) throws ParkingLotException {
        if (isVehicleParked(vehicle))
            throw new ParkingLotException("Already parked");
        if (this.vehicles.size() == this.actualSize) {
            for(ParkingLoyObserver observer:observers) {
                observer.sizeFulled();
            }
            throw new ParkingLotException("Parking Fulled");
        }
          this.vehicles.add(vehicle);

    }

    public boolean isVehicleParked(Object vehicle) {
        if (this.vehicles.contains(vehicle))
            return true;
        return false;
    }

    public boolean unPark(Object vehicle) {
        if (vehicle == null)
            return false;
        if (this.vehicles.contains(vehicle)) {
            this.vehicles.remove(vehicle);
            for(ParkingLoyObserver observer:observers) {
                observer.sizeAvailable();
            }
            return true;
        }
        return false;
    }
 }
