package edu.cooper.ece465;

public class MergeConsumer extends Thread {
    private MergeHelper cubbyhole;
    private int number;

    public MergeConsumer(MergeHelper c, int number) {
        cubbyhole = c;
        this.number = number;
    }

    public void run() {
        int value = 0;

        while (true) {
            value = cubbyhole.get();

            if (cubbyhole.getDone()) {
                return;
            }

            System.out.println("Consumer #" + this.number
                               + " got: " + value);
        }
    }
}
