import java.time.LocalDateTime;
import java.util.Date;

public class ParkingSlot {

    Vehicles vehicle;
    Date parkedTime;
    private Vehicles numberPlate;
    private LocalDateTime timeWhenCarIsParkedInSlot;

    public ParkingSlot(Vehicles vehicle) {
        this.vehicle = vehicle;
        this.parkedTime = new Date();
        this.timeWhenCarIsParkedInSlot = LocalDateTime.now();

    }

    /*public int getParkedTime() {
        int last30Min = parkedTime.getTime() - LocalDateTime.now().getMinute() <= 30;
        return last30Min;
    }*/

    public Vehicles vehiclesData() {
        return vehicle;
    }

    public LocalDateTime timeWhenCarIsParked() {
        return timeWhenCarIsParkedInSlot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlot that = (ParkingSlot) o;
        return vehicle.equals(that.vehicle);
    }
}
