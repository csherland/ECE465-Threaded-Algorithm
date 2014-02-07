/*
 * Authors: Christian Sherland
 *          Ethan Lusterman
 *          Michael Scibor
 *          Elli Rappaport
 *
 * Date:    2/6/14
 *
 * MergeHelper.java
 *      Facilitates communication between the MergeSort producers
 *      and consumers.
 *
 *
 */

package edu.cooper.ece465;
import java.util.*;

public class MergeHelper {
    
    /*
     * Helper variables to contain data between
     * various phases of the algorithm
     */
    private Integer[] toSort;
    private List<Integer[]> toMerge;
    private Integer[] sorted;
    private int expectedSize;

    /*
     * Tells consumer if they should act as
     * sorters or mergers
     */
    private boolean availableUnsorted = false;
    private boolean availableUnmerged = false;
    private boolean doneMerging = false;

    public synchronized boolean getAvailableUnsorted() {
        return availableUnsorted;
    }

    public synchronized boolean getAvailableUnmerged() {
        return availableUnmerged;
    }

    public synchronized boolean getDoneMerging() {
        return doneMerging;
    }

    /*
     * Deals with the intial sorting phase. 
     * Data is broken into chunks and sorted by consumers
     */
    public synchronized Integer[] getUnsorted() {
        while (availableUnsorted == false) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        
        availableUnsorted = false;
        notifyAll();
        return toSort;
    }

    public synchronized void putUnsorted(Integer[] value) {
        while (availableUnsorted == true) {
            try {
                wait();
            } catch (InterruptedException e) { }

        }
        
        toSort = value;
        availableUnsorted = true;
        notifyAll();
    }

    /*
     * Deals with the merging phase of the algorithm
     * Sorted lists are merged together and returned to the producer to be written
     */
    public synchronized List<Integer[]> getUnmerged() {
        while (availableUnmerged == false) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        
        availableUnsorted = false;
        notifyAll();
        return toMerge;
    }

    public synchronized void putMerged(Integer[] value) {
        
        if (value.length == expectedSize) {
            doneMerging = true;
            notifyAll();
            return;
        }
        
        while (availableUnsorted == true) {
            try {
                wait();
            } catch (InterruptedException e) { }

        }
        
        availableUnsorted = true;
        notifyAll();
    }
    
    /*
     * Allow the producer to retrive the sorted list when 
     * the algorithm is complete
     */
    public synchronized Integer[] getSorted() {
        
        while (!doneMerging) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }

        notifyAll();
        return sorted;
    }
}
