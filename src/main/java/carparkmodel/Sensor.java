package carparkmodel;

import java.util.Calendar;

public class Sensor {

    private String _macAddress;
    private boolean _isOccupied;
    private Calendar _calendar;

    public Sensor(String macAddress, boolean isOccupied, Calendar timestamp) {
        _macAddress = macAddress;
        _calendar = timestamp;
        _isOccupied = isOccupied;
    }

    public Sensor(String macAddress, Calendar timestamp) {
        _macAddress = macAddress;
        _calendar = timestamp;
        _isOccupied = false;
    }

    public String getMacAddress() {
        return _macAddress;
    }

    public boolean getIsOccupied() {
        return _isOccupied;
    }

    public String getIsOccupiedAsString() {
        return (_isOccupied ? "1": "0");
    }

    public void setIsOccupied(boolean isOccupied) {
        _isOccupied = isOccupied;
    }

    public String getTimestamp() {
        return _calendar.getTime().toString();
    }

}
