package simulations;

import carparkmodel.CarPark;

import java.util.GregorianCalendar;
import java.util.Random;

public class OccupancySimulator implements Runnable{
    private static boolean stop = false;
    private CarPark _carPark;
    private String _bootstrapServer;
    private String _topicName;

    private OccupancySimulator(String bootstrapServer, String topicName, int capacity) {
        _carPark = new CarPark(1, capacity, GregorianCalendar.getInstance());
        _bootstrapServer = bootstrapServer;
        _topicName = topicName;
    }

    public static void main(String[] args) {
        System.out.println("Occupancy Simulator running");
        if (args.length < 2 || args[0].length() < 1 || args[1].length() < 1) {
            System.out.println("Invalid Arguments");
            System.out.println("Arg 1: Bootstrap servers e.g. \"localhost:9092\" or \"lpc01-kafka01:9092,lpc01-kafka01:9093,lpc01-kafka02:9092\"");
            System.out.println("Arg 2: Topic name e.g. \"sp-topic\" or \"sp-occupancy-1\"");

        } else {
            String bootstrapServer = args[0];
            String topicName = args[1];
            int capacity = Integer.parseInt(args[2]);

            Thread t = new Thread(new OccupancySimulator(bootstrapServer, topicName, capacity), "thrsdead");
            t.start();
        }
    }

    public void run() {
        Runtime.getRuntime().addShutdownHook(new Thread("2 thread") {
            public void run () {
                synchronized (this)
                {
                    System.out.println("Occupancy Simulator running");
                    stop = true;
                }
            }
        });
        synchronized (this) {
            while(!stop) {
                try {
                    float run = new Random().nextFloat();
                    int rand = new Random().nextInt(5000);
//
                    if (run < 0.5) {
                        Thread.sleep(rand * 2 + 20000);
                    } else if (run >= 0.5 && run < 0.7) {
                        Thread.sleep(rand);
                    } else if (run >=0.7 && run < 0.8) {
                        Thread.sleep(rand/5);
                    } else if (run >= 0.8 && run < 0.85) {
                        Thread.sleep(rand/6);
                    }
                    else if (run >= 0.85 && run < 0.9) {
                        Thread.sleep(rand * 5 + 30000);
                    } else if (run >= 0.9 && run < 0.925) {
                        Thread.sleep(rand / 2);
                    } else if (run >= 0.925 && run < 0.95) {
                        Thread.sleep(rand / 3);
                    } else if (run >= 0.95 && run < 0.975) {
                        Thread.sleep(rand * 8 );
                    } else if (run >= 0.975 && run < 1) {
                        Thread.sleep(rand * 9);
                    }

//                    Thread.sleep(1000);

                    MockOccupancy.randomEventCarPark(_carPark, _bootstrapServer, _topicName);

//                    Thread.sleep(new Random().nextInt(500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
