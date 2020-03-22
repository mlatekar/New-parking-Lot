public class ParkingLot {
    private int currentCapacity;
    private int actualSize;
    private Object vehicle;
    private ParkingLotOwner owner;

    public ParkingLot(int size) {
        this.currentCapacity=0;
        this.actualSize=size;

    }

    public void park(Object vehicle)throws ParkingLotException {
        if(this.currentCapacity==this.actualSize){
            owner.sizeFulled();
            throw new ParkingLotException("Parking Fulled");}
        this.currentCapacity++;
        this.vehicle=vehicle;
    }
    public boolean isVehicleParked(Object vehicle) {
        if (this.vehicle.equals(vehicle))
            return true;
        return false;
    }
    public boolean unPark(Object vehicle) {
        if (vehicle==null)
            return false;
        if (this.vehicle.equals(vehicle)) {
            this.vehicle = null;
            return true;
        }return false;
    }


    public boolean ownerRegistration(ParkingLotOwner owner) {
        this.owner=owner;
        return true;
    }
}
