package edu.cooper.ece465;

public class MergeConsumer extends Thread {
    private MergeHelper helper;
    private MergeSort m;
    private int number;

    public MergeConsumer(MergeHelper c, int number) {
        helper = c;
        this.number = number;
        m = new MergeSort();
    }

    public void run() {
        int[] value;

        while (true) {
            value = helper.get();

            if (helper.getDone()) {
                return;
            }
        
            m.sort(value);
        }
    }
}
