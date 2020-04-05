public class Vehicles {
    public String carsType;
    private String carsNumberPlate;
    public String carColour;

    public Vehicles(String carsColour, String carsNumberPlate, String carsType) {
        this.carColour = carsColour;
        this.carsNumberPlate = carsNumberPlate;
        this.carsType = carsType;
    }

    public String searchingCarColour() {
        return carColour;
    }

    public String carsNumberPlate() {
        return carsNumberPlate;
    }
}
