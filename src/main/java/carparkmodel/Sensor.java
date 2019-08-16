package carparkmodel;

import java.util.Calendar;

public class Sensor {

    private int _id;
    private float _batteryHealth;
    private NodeStatus _nodeStatus;
    private boolean _isOccupied;
    private Calendar _calendar;

    public Sensor(int id, float battery_health, NodeStatus nodeStatus, boolean isOccupied, Calendar timestamp) {
        _id = id;
        _batteryHealth = battery_health;
        _nodeStatus = nodeStatus;
        _isOccupied = isOccupied;
        _calendar = timestamp;
    }

    public Sensor(int id, float battery_health, NodeStatus nodeStatus, Calendar timestamp) {
        _id = id;
        _batteryHealth = battery_health;
        _nodeStatus = nodeStatus;
        _isOccupied = false;
        _calendar = timestamp;
    }

    public int getId() {
        return _id;
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

    public String getBatteryHealth() {
        return Float.toString(_batteryHealth);
    }

    public String getTimestamp() {
        return _calendar.getTime().toString();
    }

    public String getNodeStatus() {
        return _nodeStatus.toString();
    }
}
