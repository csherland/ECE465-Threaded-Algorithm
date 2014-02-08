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
    private ArrayList<Integer[]> toMerge = new ArrayList<Integer[]>();
    private Integer[] sorted;
    private int expectedSize;

    /*
     * Tells consumer if they should act as
     * sorters or mergers
     */
    private boolean availableUnsorted = false;
    private boolean availableUnmerged = false;
    private boolean doneMerging = false;
    private boolean doneSorting = false;

    public void setExpectedSize(int value) {
        expectedSize = value;
    }

    public synchronized void setDoneSorting(boolean value) {
        doneSorting = value;
    }

    public synchronized boolean getDoneSorting() {
        return doneSorting;
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
            if (doneSorting) {
                return null;
            }
            
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
    public synchronized ArrayList<Integer[]> getUnmerged() {
        while (availableUnmerged == false) {
            try {
                wait();
            } catch (InterruptedException e) { }
            
            if (doneMerging) {
                return null;
            }
        }
      
        ArrayList<Integer[]> value = new ArrayList<Integer[]>(toMerge.subList(0,2));
        toMerge.remove(0);
        toMerge.remove(0);

        if (toMerge.size() < 2) {
            availableUnmerged = false;
        }

        return value;
    }

    public synchronized void putUnmerged(Integer[] value) {

        System.out.println(value.length);
        System.out.println(toSort.length);

        if (value.length == expectedSize) {
            doneMerging = true;
            sorted = value;
            notifyAll();
        } else {
            toMerge.add(value);
        
            if (toMerge.size() >= 2) {
                availableUnmerged = true;
                notifyAll();
            }
        }
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
