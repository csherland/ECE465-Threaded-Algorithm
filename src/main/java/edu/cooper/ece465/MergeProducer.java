package edu.cooper.ece465;

public class MergeProducer extends Thread {
    private MergeHole cubbyhole;
    private int number;

    public MergeProducer(MergeHole c, int number) {
        cubbyhole = c;
        this.number = number;
    }

    public void run() {
        cubbyhole.registerProducer();
        for (int i = 0; i < ProducerConsumerTest.PROD_SIZE; i++) {
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
