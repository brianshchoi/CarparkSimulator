package carparkmodel;

import java.text.SimpleDateFormat;
import java.util.*;

import static util.MACAddress.generateRandomMacAddress;

public class CarPark {

    private int _carParkID;
    private int _capacity;
    private HashMap<String, Sensor> _sensors;
    private HashMap<Integer, String> _sensorMACAddressMap;

//    private Sensor[] _sensors;
    private Calendar _calendar;
//
//    public CarPark(int carParkID, int capacity) {
//        _carParkID = carParkID;
//        _capacity = capacity;
//        _calendar = new GregorianCalendar();
//        _sensors = new HashMap<String, Sensor>();
//
//        for (int i = 0; i < capacity; i++) {
//            Sensor s = new Sensor(generateRandomMacAddress(), _calendar);
//            _sensors.put(s.getMacAddress(), s);
//        }
//    }

    public CarPark(int carParkID, int capacity, Calendar calendar) {
        _carParkID = carParkID;
        _capacity = capacity;
        _calendar = calendar;
        _sensorMACAddressMap = new HashMap<Integer, String>();
        _sensors = new HashMap<String, Sensor>();

        initCarparkNodes(capacity);
    }

    public HashMap<Integer, String> getSensorIndexMap() {
        return _sensorMACAddressMap;
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
        for (int i = 0; i < capacity; i++) {
            String macAddress = generateRandomMacAddress();

            while(_sensorMACAddressMap.containsValue(macAddress)) {
                macAddress = generateRandomMacAddress();
            }

            _sensorMACAddressMap.put(i, macAddress);
            Sensor s = new Sensor(macAddress, _calendar);
            _sensors.put(macAddress, s);
        }
    }
}
