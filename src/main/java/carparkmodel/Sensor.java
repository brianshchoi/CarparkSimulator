package carparkmodel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

    public void setOpposite() {
        _isOccupied = !_isOccupied;
    }

    public String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(_calendar.getTime());
    }

    public void setCurrentTimestamp() {
        _calendar = GregorianCalendar.getInstance();
    }

    public void setDelay(int delay) {
        _calendar.add(Calendar.MILLISECOND, delay);
    }
}
