package simulations;

import carparkmodel.CarPark;
import carparkmodel.Sensor;

import java.util.HashMap;
import java.util.Random;

public class MockOccupancy {
    public static CarPark randomiseCarPark(CarPark carPark, int runNumber, int totalRuns) {
        Random random = new Random();
        HashMap<Integer, String> indexMap = carPark.getSensorIndexMap();
        HashMap<String, Sensor> sensors = carPark.getSensors();

        for (int i = 0; i < carPark.getCapacity(); i++) {
            // Randomise occupancy
            if (random.nextFloat() < pseudoRandomProbability(runNumber, totalRuns)) {
                sensors.get(indexMap.get(i)).setIsOccupied(true);
            } else {
                sensors.get(indexMap.get(i)).setIsOccupied(false);
            }
        }

        return carPark;
    }

    public static float pseudoRandomProbability(int runNumber, int totalRuns) {
        if (runNumber <= totalRuns/2) {
            return (0.2f + 0.8f * runNumber/totalRuns);
        } else {
            return (1f - 0.4f * runNumber/totalRuns);
        }
    }
}
