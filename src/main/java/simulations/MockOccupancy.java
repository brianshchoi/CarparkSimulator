package simulations;

import carparkmodel.CarPark;

import java.util.Random;

public class MockOccupancy {
    public static CarPark randomiseCarPark(CarPark cp, int runNumber, int totalRuns) {
        Random random = new Random();

        for (int i = 0; i < cp.getCapacity(); i++) {
            if (random.nextFloat() < pseudoRandomProbability(runNumber, totalRuns)) {
                cp.getSensor(i).setIsOccupied(true);
            } else {
                cp.getSensor(i).setIsOccupied(false);
            }
        }

        return cp;
    }

    public static float pseudoRandomProbability(int runNumber, int totalRuns) {
        if (runNumber <= totalRuns/2) {
            return (0.2f + 0.8f * runNumber/totalRuns);
        } else {
            return (1f - 0.4f * runNumber/totalRuns);
        }
    }
}
