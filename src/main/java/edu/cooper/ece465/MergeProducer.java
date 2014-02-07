/*
 * Authors: Christian Sherland
 *          Ethan Lusterman
 *          Michael Scibor
 *          Elli Rappaport
 *
 * Date:    2/6/14
 *
 * MergeProducer.java
 *      Responsible for producing data to be sorted by consumers
 *
 */

package edu.cooper.ece465;

import java.lang.Math;
import java.util.*;
import java.io.*;

public class MergeProducer extends Thread {
    private MergeHelper helper;
    private int number;

    public MergeProducer(MergeHelper c, int number) {
        helper = c;
        this.number = number;
    }

    public void run() {
        helper.registerProducer();
        
        
        // Read input
        try {

            // Determine how many times to split the list
            int numDivisions = 0;

            // Read in the data
            String fromFile;
            List<Integer> myIntegers = new ArrayList<Integer>();
            BufferedReader br = new BufferedReader(new FileReader("test.txt"));
            
            while ((fromFile = br.readLine()) != null) { 
                int toAdd = Integer.parseInt(fromFile);
                myIntegers.add(toAdd); 
            }

            int sizeOfSplit = myIntegers.size()/numDivisions;

            // TODO: Make sure last elements of array are included
            for (int i = 0; i < myIntegers.size(); i += sizeOfSplit) {
                helper.put(myIntegers.subList(i, i+sizeOfSplit).toArray(new Integer[sizeOfSplit])); 
            }

        } catch (IOException e) {
                System.out.println("File not found.");
        }
        
        helper.deregisterProducer();
    }
}
