package carparkmodel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CarPark {

    private int _carParkID;
    private int _capacity;
    private int _occupancy;
    private Sensor[] _sensors;
    private Calendar _calendar;

    // TODO: Add other sensors

    public CarPark(int carParkID, int capacity) {
        _carParkID = carParkID;
        _capacity = capacity;
        _occupancy = 0;
        _calendar = new GregorianCalendar();
        _sensors = new Sensor[capacity];

        for (int i = 0; i < capacity; i++) {
            _sensors[i] = new Sensor(i, 100, NodeStatus.ONLINE, _calendar);
        }
    }

    public CarPark(int carParkID, int capacity, Calendar calendar) {
        _carParkID = carParkID;
        _capacity = capacity;
        _occupancy = 0;
        _calendar = calendar;
        _sensors = new Sensor[capacity];

        for (int i = 0; i < capacity; i++) {
            _sensors[i] = new Sensor(i, 100, NodeStatus.ONLINE, _calendar);
        }
    }

    public int getCarParkID() {
        return _carParkID;
    }

    public int getCapacity() {
        return _capacity;
    }

    public int getOccupancy() {
        _occupancy = 0;

        for (Sensor sensor: _sensors) {
            _occupancy = sensor.getIsOccupied() ? _occupancy + 1 : _occupancy;
        }

        return _occupancy;
    }

    public Sensor getSensor(int index) {
        if (index >= 0 && index < _capacity) {
            return _sensors[index];
        } else {
            throw new IllegalArgumentException("Index out of bounds");
        }
    }

    public String getTimestampAsString() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(_calendar.getTime());
    }

    public void addMinutes(int minutes) {
        _calendar.add(Calendar.MINUTE, minutes);
    }

    public void setTimestamp(Date date) {
        _calendar.setTime(date);
    }

    public Sensor[] getSensors() {
        return _sensors;
    }
}
