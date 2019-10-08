package simulations;

import carparkmodel.GateGroup;
import csv.JSONWriter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class GateSimulator implements Runnable {
    private static boolean stop = false;
    private String _bootstrapServer;
    private String _topicName;
    private GateGroup _gates;

    private GateSimulator(String bootstrapServer, String topicName) {
        _gates = new GateGroup("1");
        _bootstrapServer = bootstrapServer;
        _topicName = topicName;
    }

    public static void main(String[] args) {
        System.out.println("GateGroup Simulator running");
        if (args.length < 2 || args[0].length() < 1 || args[1].length() < 1) {
            System.out.println("Invalid Arguments");
            System.out.println("Arg 1: Bootstrap servers e.g. \"localhost:9092\" or \"lpc01-kafka01:9092,lpc01-kafka01:9093,lpc01-kafka02:9092\"");
            System.out.println("Arg 2: Topic name e.g. \"sp-topic\" or \"sp-occupancy-1\"");

        } else {
            String bootstrapServer = args[0];
            String topicName = args[1];

            Thread t = new Thread(new GateSimulator(bootstrapServer, topicName), "thread");
            t.start();
        }
    }

    public void run() {
        Runtime.getRuntime().addShutdownHook(new Thread("shutdown thread") {
            public void run() {
                synchronized (this) {
                    System.out.println("Event driven Simulator stopping");
                    stop = true;
                }
            }
        });
        synchronized (this) {
            while (!stop) {
                try {
                    float run = new Random().nextFloat();
                    int rand = new Random().nextInt(5000);
//
                    if (run < 0.5) {
                        Thread.sleep(rand * 2 + 20000);
                    } else if (run >= 0.5 && run < 0.7) {
                        Thread.sleep(rand);
                    } else if (run >= 0.7 && run < 0.8) {
                        Thread.sleep(rand / 5);
                    } else if (run >= 0.8 && run < 0.85) {
                        Thread.sleep(rand / 6);
                    } else if (run >= 0.85 && run < 0.9) {
                        Thread.sleep(rand * 5 + 30000);
                    } else if (run >= 0.9 && run < 0.925) {
                        Thread.sleep(rand / 2);
                    } else if (run >= 0.925 && run < 0.95) {
                        Thread.sleep(rand / 3);
                    } else if (run >= 0.95 && run < 0.975) {
                        Thread.sleep(rand * 8);
                    } else if (run >= 0.975 && run < 1) {
                        Thread.sleep(rand * 9);
                    }

                    randomGateEvent(_gates, _bootstrapServer, _topicName);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void randomGateEvent(GateGroup gates, String bootstrapServer, String topicName) {
        JSONWriter writer = new JSONWriter("Carpark");

        int index = new Random().nextInt(gates.getSensorIndexMap().length);
        int gateEntryEvent;

        if (gates.getOccupancy() == 0) {
            gateEntryEvent = 1;
            gates.incrementOccupancy();
        } else if (gates.getOccupancy() == gates.getCapacity()) {
            gateEntryEvent = 0;
            gates.decrementOccupancy();
        } else {
            if (new Random().nextInt(100) < 65) {
                gateEntryEvent = 1;
                gates.incrementOccupancy();
            } else {
                gateEntryEvent = 0;
                gates.decrementOccupancy();
            }
        }

        Date calendar = GregorianCalendar.getInstance().getTime();
        String calendarString = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(calendar);

        String[] eventFields = new String[]{
                calendarString, gates.getSensorIndexMap()[index], Integer.toString(gateEntryEvent)
        };

        writer.toKafkaProducer(eventFields, bootstrapServer, topicName);
        System.out.println(gates.getOccupancy());
    }
}

