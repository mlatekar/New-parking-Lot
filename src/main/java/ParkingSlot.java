import java.util.Date;

public class ParkingSlot {

    Vehicles vehicle;
    Date parkedTime;
    private Vehicles numberPlate;

    public ParkingSlot(Vehicles vehicle) {
        this.vehicle = vehicle;
        this.parkedTime = new Date();
    }

    public Vehicles vehiclesData() {
        return vehicle;
    }

    public Vehicles numberPlate() {
        return numberPlate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlot that = (ParkingSlot) o;
        return vehicle.equals(that.vehicle);
    }
}
