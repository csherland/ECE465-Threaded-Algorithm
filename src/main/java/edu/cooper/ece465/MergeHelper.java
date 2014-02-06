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

public class MergeHelper {
    private Integer[] contents;
    private boolean available = false;

    private int producers = 0;
    private boolean done = false;

    public synchronized void registerProducer() {
        producers++;
    }

    public synchronized void deregisterProducer() {
        producers--;
        notifyAll();
    }

    public synchronized boolean getDone() {
        return done;
    }

    public synchronized Integer[] get() {
        while (available == false) {
            if (producers == 0) {
                done = true;
                break;
            }

            try {
                wait();
            } catch (InterruptedException e) { }
        }
        available = false;
        notifyAll();
        return contents;
    }

    public synchronized void put(Integer[] value) {
        while (available == true) {
            try {
                wait();
            } catch (InterruptedException e) { }

        }
        contents = value;
        available = true;
        notifyAll();
    }

}
