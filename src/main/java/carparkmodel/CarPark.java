package carparkmodel;

import java.text.SimpleDateFormat;
import java.util.*;


public class CarPark {

    private int _carParkID;
    private int _capacity;
    private HashMap<String, Sensor> _sensors;
    private Calendar _calendar;

    private String[] _sensorMACAddressArray = new String[]{
            "7d:db:12:74:30:00", "7d:db:12:74:30:01", "7d:db:12:74:30:02", "7d:db:12:74:30:03", "7d:db:12:74:30:04",
            "7d:db:12:74:30:05", "7d:db:12:74:30:06", "7d:db:12:74:30:07", "7d:db:12:74:30:08", "7d:db:12:74:30:09",
            "7d:db:12:74:30:10", "7d:db:12:74:30:11", "7d:db:12:74:30:12", "7d:db:12:74:30:13", "7d:db:12:74:30:14",
            "7d:db:12:74:30:15", "7d:db:12:74:30:16", "7d:db:12:74:30:17", "7d:db:12:74:30:18", "7d:db:12:74:30:19",
            "7d:db:12:74:30:20", "7d:db:12:74:30:21", "7d:db:12:74:30:22", "7d:db:12:74:30:23", "7d:db:12:74:30:24",
            "7d:db:12:74:30:25", "7d:db:12:74:30:26", "7d:db:12:74:30:27", "7d:db:12:74:30:28", "7d:db:12:74:30:29",
            "7d:db:12:74:30:30", "7d:db:12:74:30:31", "7d:db:12:74:30:32", "7d:db:12:74:30:33", "7d:db:12:74:30:34",
            "7d:db:12:74:30:35", "7d:db:12:74:30:36", "7d:db:12:74:30:37", "7d:db:12:74:30:38", "7d:db:12:74:30:39",
            "7d:db:12:74:30:40", "7d:db:12:74:30:41", "7d:db:12:74:30:42", "7d:db:12:74:30:43", "7d:db:12:74:30:44",
            "7d:db:12:74:30:45", "7d:db:12:74:30:46", "7d:db:12:74:30:47", "7d:db:12:74:30:48", "7d:db:12:74:30:49",
            "7d:db:12:74:30:50", "7d:db:12:74:30:51", "7d:db:12:74:30:52", "7d:db:12:74:30:53", "7d:db:12:74:30:54",
            "7d:db:12:74:30:55", "7d:db:12:74:30:56", "7d:db:12:74:30:57", "7d:db:12:74:30:58", "7d:db:12:74:30:59",
            "7d:db:12:74:30:60", "7d:db:12:74:30:61", "7d:db:12:74:30:62", "7d:db:12:74:30:63", "7d:db:12:74:30:64",
            "7d:db:12:74:30:65", "7d:db:12:74:30:66", "7d:db:12:74:30:67", "7d:db:12:74:30:68", "7d:db:12:74:30:69",
            "7d:db:12:74:30:70", "7d:db:12:74:30:71", "7d:db:12:74:30:72", "7d:db:12:74:30:73", "7d:db:12:74:30:74",
            "7d:db:12:74:30:75", "7d:db:12:74:30:76", "7d:db:12:74:30:77", "7d:db:12:74:30:78", "7d:db:12:74:30:79",
            "7d:db:12:74:30:80", "7d:db:12:74:30:81", "7d:db:12:74:30:82", "7d:db:12:74:30:83", "7d:db:12:74:30:84",
            "7d:db:12:74:30:85", "7d:db:12:74:30:86", "7d:db:12:74:30:87", "7d:db:12:74:30:88", "7d:db:12:74:30:89",
            "7d:db:12:74:30:90", "7d:db:12:74:30:91", "7d:db:12:74:30:92", "7d:db:12:74:30:93", "7d:db:12:74:30:94",
            "7d:db:12:74:30:95", "7d:db:12:74:30:96", "7d:db:12:74:30:97", "7d:db:12:74:30:98", "7d:db:12:74:30:99",
    };

    public CarPark(int carParkID, int capacity, Calendar calendar) {
        _carParkID = carParkID;
        _capacity = capacity;
        _calendar = calendar;
        _sensors = new HashMap<>();

        initCarparkNodes(capacity);
    }

    public String[] getSensorIndexMap() {
        return _sensorMACAddressArray;
    }

    public HashMap<String, Sensor> getSensors() {
        return _sensors;
    }

    public int getCarParkID() {
        return _carParkID;
    }

    public int getCapacity() {
        return _capacity;
    }

    public Sensor getSensor(String key) {
        return _sensors.get(key);
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

    private void initCarparkNodes(int capacity) {
        _sensorMACAddressArray = Arrays.copyOfRange(_sensorMACAddressArray, 0, capacity);

        for (int i = 0; i < capacity; i++) {
            Sensor s = new Sensor(_sensorMACAddressArray[i], _calendar);
            _sensors.put(_sensorMACAddressArray[i], s);
        }
    }
}
