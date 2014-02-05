package edu.cooper.ece465;

public class MergeProducer extends Thread {
    private MergeHelper cubbyhole;
    private int number;

    public MergeProducer(MergeHelper c, int number) {
        cubbyhole = c;
        this.number = number;
    }

    public void run() {
        cubbyhole.registerProducer();
        for (int i = 0; i < 100; i++) {
            cubbyhole.put(i);
            System.out.println("Producer #" + this.number
                               + " put: " + i);
            try {
                sleep((int)(Math.random() * 100));
            } catch (InterruptedException e) { }

        }
        cubbyhole.deregisterProducer();
    }
}
