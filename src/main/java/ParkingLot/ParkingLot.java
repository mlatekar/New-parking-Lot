package ParkingLot;

public class ParkingLot {
    private Object vehicle;

    public ParkingLot() {
    }

    public boolean park(Object vehicle) {
        if(this.vehicle!=null)
            return false;
            this.vehicle=vehicle;
        return true;
    }
}
