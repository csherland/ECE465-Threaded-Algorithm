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
import java.util.*;

public class MergeConsumer extends Thread {
    private MergeHelper helper;
    private MergeSort m;
    private int number;

    public MergeConsumer(MergeHelper help, int number) {
        helper = help;
        this.number = number;
        m = new MergeSort();
    }

    public void run() {
        Integer[] value = {};
        ArrayList<Integer[]> toMerge;

        while (true) {
            value = helper.getUnsorted();

            if (helper.getDoneSorting()) {
                break;
            }
        
            m.sort(value);
            helper.putUnmerged(value);
        }
        
        while (true) {
            toMerge = helper.getUnmerged();

            if (helper.getDoneMerging()) {
                break;
            }
       
            helper.putUnmerged(m.mergeLists(toMerge.get(0), toMerge.get(1)));
        }
    }
}
