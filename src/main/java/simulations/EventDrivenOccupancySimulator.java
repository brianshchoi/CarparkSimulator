package simulations;

import carparkmodel.CarPark;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * This is the EVENT-DRIVEN
 */
public class EventDrivenOccupancySimulator implements Runnable {
    private static boolean stop = false;
    private CarPark _carPark;
    private String _bootstrapServer;
    private String _topicName;
    private Calendar _calendar;
    private boolean _showAllSlots;
    private boolean _dated;

    private EventDrivenOccupancySimulator(String bootstrapServer, String topicName, int capacity, boolean showAllSlots) {
        _carPark = new CarPark(1, capacity, GregorianCalendar.getInstance());
        _bootstrapServer = bootstrapServer;
        _topicName = topicName;
        _showAllSlots = showAllSlots;
        _dated = false;
    }

    private EventDrivenOccupancySimulator(String bootstrapServer, String topicName, int capacity, boolean showAllSlots, Calendar calendar) {
        _calendar = calendar;
        _carPark = new CarPark(1, capacity, _calendar);
        _bootstrapServer = bootstrapServer;
        _topicName = topicName;
        _showAllSlots = showAllSlots;
        _dated = true;
    }

    public static void main(String[] args) {
        System.out.println("Occupancy Simulator running");
        if (args.length != 4 && args.length != 10) {
            System.out.println("Invalid Arguments");
            System.out.println("Arg 0: Bootstrap servers e.g. \"localhost:9092\" or \"lpc01-kafka01:9092,lpc01-kafka01:9093,lpc01-kafka02:9092\"");
            System.out.println("Arg 1: Topic name e.g. \"sp-topic\" or \"sp-occupancy-1\"");
            System.out.println("Arg 2: Capacity of Carpark (Up to 100)");
            System.out.println("Arg 3: Option to show ALL records (\"y\" for yes or \"n\" for no)");

            System.out.println("============================");
            System.out.println("OPTIONAL ARGUMENTS FOR DATES");
            System.out.println("============================");
            System.out.println("Arg 4: Starting Day");
            System.out.println("Arg 5: Starting Month (1-12)");
            System.out.println("Arg 6: Starting Year");
            System.out.println("Arg 7: Starting Hour (24h)");
            System.out.println("Arg 8: Starting Minute");
            System.out.println("Arg 9: Starting Second");

            System.exit(0);
        } else if (args.length == 4) {
            // Real time Event Driven
            String bootstrapServer = args[0];
            String topicName = args[1];
            int capacity = Integer.parseInt(args[2]);
            boolean showAllSlots = args[3].equalsIgnoreCase("y");

            if (capacity > 100) {
                capacity = 100;
            } else if (capacity <= 0) {
                capacity = 1;
            }

            Thread t = new Thread(new EventDrivenOccupancySimulator(bootstrapServer, topicName, capacity, showAllSlots), "thread");
            t.start();
        } else {
            // Event driven with past (or future) date
            String bootstrapServer = args[0];
            String topicName = args[1];
            int capacity = Integer.parseInt(args[2]);
            boolean showAllSlots = args[3].equalsIgnoreCase("y");

            int day = Integer.parseInt(args[4]);
            int month = Integer.parseInt(args[5]);
            int year = Integer.parseInt(args[6]);
            int startingHour = Integer.parseInt(args[7]);
            int startingMinute = Integer.parseInt(args[8]);
            int startingSecond = Integer.parseInt(args[9]);

            // Valid argument check
            if (startingHour < 0 | startingHour > 23 | startingMinute < 0 | startingMinute > 59 | startingSecond < 0
                    | startingSecond > 59) {
                throw new IllegalArgumentException("Argument passed has an invalid value");
            }

            if (capacity > 100) {
                capacity = 100;
            } else if (capacity <= 0) {
                capacity = 1;
            }

            Calendar calendar = new GregorianCalendar(
                    year, month - 1, day, startingHour, startingMinute, startingSecond);


            Thread t = new Thread(new EventDrivenOccupancySimulator(bootstrapServer, topicName, capacity, showAllSlots, calendar), "thread");
            t.start();
        }
    }

    public void run() {
        Runtime.getRuntime().addShutdownHook(new Thread("shutdown thread") {
            public void run() {
                synchronized (this) {
                    System.out.println("Occupancy Simulator stopping");
                    stop = true;
                }
            }
        });

        synchronized (this) {
            while (!stop) {
                try {
                    float run = new Random().nextFloat();
                    int rand = new Random().nextInt(5000);

                    int delay;

                    // Random time intervals depending on random value
                    if (run < 0.5) {
                        delay = rand * 2 + 20000;
                        Thread.sleep(delay);
                    } else if (run >= 0.5 && run < 0.7) {
                        delay = rand;
                        Thread.sleep(delay);
                    } else if (run >= 0.7 && run < 0.8) {
                        delay = rand / 5;
                        Thread.sleep(delay);
                    } else if (run >= 0.8 && run < 0.85) {
                        delay = rand / 6;
                        Thread.sleep(delay);
                    } else if (run >= 0.85 && run < 0.9) {
                        delay = rand * 5 + 30000;
                        Thread.sleep(delay);
                    } else if (run >= 0.9 && run < 0.925) {
                        delay = rand / 2;
                        Thread.sleep(delay);
                    } else if (run >= 0.925 && run < 0.95) {
                        delay = rand / 3;
                        Thread.sleep(delay);
                    } else if (run >= 0.95 && run < 0.975) {
                        delay = rand * 8;
                        Thread.sleep(delay);
                    } else {
                        delay = rand * 9;
                        Thread.sleep(delay);
                    }

                    MockOccupancy.randomEventCarPark(_carPark, _bootstrapServer, _topicName, delay, _showAllSlots);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
