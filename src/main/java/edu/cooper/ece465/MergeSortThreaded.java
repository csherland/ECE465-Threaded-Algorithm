package edu.cooper.ece465;

import java.util.*;

public class MergeSortThreaded {
    public static final int PROD_SIZE = 100;
    public static int CORES = Runtime.getRuntime().availableProcessors(); 

    public static void main(String[] args) {
        MergeHelper helper = new MergeHelper();
        MergeProducer p1 = new MergeProducer(helper, 1);
        p1.start();

        List <MergeConsumer> consumers = new ArrayList<MergeConsumer>();

        for (int i=1; i < CORES-1; i++) {
            consumers.add(new MergeConsumer(helper, i));
            consumers.get(i-1).start();
        }

        // Not multi core system
        if (CORES == 1) {
            MergeConsumer c = new MergeConsumer(helper, 1);
            c.start();
        }
    }
}
