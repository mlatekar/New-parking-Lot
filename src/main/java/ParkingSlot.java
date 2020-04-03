import java.util.Date;

public class ParkingSlot {

    String colour;
    Object vehicle;
    Date parkedTime;

    public ParkingSlot(Object vehicle,String colour) {
        this.vehicle = vehicle;
        this.colour=colour;
        this.parkedTime = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlot that = (ParkingSlot) o;
        return vehicle.equals(that.vehicle);
    }
}
