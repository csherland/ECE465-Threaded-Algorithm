/*
 * Authors: Christian Sherland
 *          Ethan Lusterman
 *          Michael Scibor
 *          Elli Rappaport
 *
 * Date:    2/6/14
 *
 * MergeConsumer.java
 *      Accepts data produced by MergeProducers and
 *      applies merge sort
 *
 */

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
        Integer[] value;

        while (true) {
            value = helper.get();

            if (helper.getDone()) {
                return;
            }
        
            m.sort(value);
        }
    }
}
