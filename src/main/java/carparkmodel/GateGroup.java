package carparkmodel;

public class GateGroup {

    private String _carParkID;
    private int _capacity;
    private int _occupancy;
    private String[] _sensorMACAddressArray = new String[]
        {
            "ad:db:12:74:31:00", "ad:db:12:74:31:01", "ad:db:12:74:31:02", "ad:db:12:74:31:03"
        };

    public GateGroup(String carParkID) {
        _carParkID = carParkID;
        _capacity = 100;
        _occupancy = 0;
    }

    public int getCapacity() {
        return _capacity;
    }

    public int getOccupancy() {
        return _occupancy;
    }

    public String getCarParkID() {
        return _carParkID;
    }

    public String[] getSensorIndexMap() {
        return _sensorMACAddressArray;
    }

    public void incrementOccupancy() {
        _occupancy++;
    }

    public void decrementOccupancy() {
        _occupancy--;
    }
}
