package simulations;

import carparkmodel.CarPark;

import java.util.GregorianCalendar;
import java.util.Random;

public class RandomSimulator implements Runnable{
    private static boolean stop = false;
    private CarPark _carPark;
    private String _bootstrapServer;
    private String _topicName;

    private RandomSimulator(String bootstrapServer, String topicName, int capacity) {
        _carPark = new CarPark(1, capacity, GregorianCalendar.getInstance());
        _bootstrapServer = bootstrapServer;
        _topicName = topicName;
    }

    public static void main(String[] args) {
        System.out.println("Event driven Simulator running");
        if (args.length < 3 || args[0].length() < 1 || args[1].length() < 1
                || args[2].length() < 1) {
            System.out.println("Invalid Arguments");
            System.out.println("Arg 1: Bootstrap servers e.g. \"localhost:9092\" or \"lpc01-kafka01:9092,lpc01-kafka01:9093,lpc01-kafka02:9092\"");
            System.out.println("Arg 2: Topic name e.g. \"sp-topic\" or \"sp-occupancy-1\"");
            System.out.println("Arg 3: Carpark Occupancy");

        } else {
            String bootstrapServer = args[0];
            String topicName = args[1];
            int capacity = Integer.parseInt(args[2]);

            Thread t = new Thread(new RandomSimulator(bootstrapServer, topicName, capacity), "thread");
            t.start();
        }
    }

    public void run() {
        Runtime.getRuntime().addShutdownHook(new Thread("shutdown thread") {
            public void run () {
                synchronized (this)
                {
                    System.out.println("Event driven Simulator stopping");
                    stop = true;
                }
            }
        });
        synchronized (this) {
            while(!stop) {
                try {
                    MockOccupancy.randomEventCarPark(_carPark, _bootstrapServer, _topicName);
                    Thread.sleep(new Random().nextInt(15000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
