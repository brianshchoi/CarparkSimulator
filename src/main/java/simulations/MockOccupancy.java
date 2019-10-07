package simulations;

import carparkmodel.CarPark;
import carparkmodel.Sensor;
import csv.JSONWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class MockOccupancy {
    public static CarPark randomiseCarPark(CarPark carPark, int runNumber, int totalRuns) {
        Random random = new Random();
//        HashMap<Integer, String> indexMap = carPark.getSensorIndexMap();
        String[] indexMap = carPark.getSensorIndexMap();
        HashMap<String, Sensor> sensors = carPark.getSensors();

        for (int i = 0; i < carPark.getCapacity(); i++) {
            // Randomise occupancy
            if (random.nextFloat() < pseudoRandomProbability(runNumber, totalRuns)) {
                sensors.get(indexMap[i]).setIsOccupied(true);
            } else {
                sensors.get(indexMap[i]).setIsOccupied(false);
            }
        }

        return carPark;
    }

    static void randomEventCarPark(CarPark carPark, String bootstrapServer, String topicName) {
        JSONWriter writer = new JSONWriter("Carpark");
//        HashMap<Integer, String> indexMap = carPark.getSensorIndexMap();
        String[] indexMap = carPark.getSensorIndexMap();
        HashMap<String, Sensor> sensors = carPark.getSensors();

        Sensor randomSensor = sensors.get(
                indexMap[
                        new Random().nextInt(sensors.size())]);

        randomSensor.setOpposite();
        randomSensor.setCurrentTimestamp();

        writer.toKafkaProducer(randomSensor, bootstrapServer, topicName);
//        writer.toJson(randomSensor);
    }

    public static float pseudoRandomProbability(int runNumber, int totalRuns) {
        if (runNumber <= totalRuns/2) {
            return (0.2f + 0.8f * runNumber/totalRuns);
        } else {
            return (1f - 0.4f * runNumber/totalRuns);
        }
    }
}
