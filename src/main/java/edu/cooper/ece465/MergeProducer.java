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
        
        List<Integer> myIntegers = new ArrayList<Integer>();
        
        // Read input
        BufferedReader br = null;
        try {
            String fromFile;
            int numDivisions = 0;
            br = new BufferedReader(new FileReader("test.txt"));

            // Determine how many times to split the list
            while (Math.pow(2,numDivisions) < ((MergeSortThreaded.CORES)-1)) {
                if (Math.pow(2,numDivisions) != MergeSortThreaded.CORES-1) {
                    numDivisions++;
                }
            } 

            while ((fromFile = br.readLine()) != null) { 
                int toAdd = Integer.parseInt(fromFile);
                myIntegers.add(toAdd); 
            }

            int sizeOfSplit = myIntegers.size()/numDivisions;

            // TODO: Make sure last elements of array are included
            List<Integer[]> splitArray = new ArrayList<Integer[]>();
            for (int i = 0; i < myIntegers.size(); i += sizeOfSplit) {
                splitArray.add(myIntegers.subList(i, i+sizeOfSplit).toArray(new Integer[sizeOfSplit])); 
            }

            for (Integer[] subArray:splitArray) {
                int[] myInts = new int[subArray.length];
                for (int i = 0; i < subArray.length; ++i) {
                    myInts[i] = subArray[i].intValue();
                }
                
                helper.put(myInts);
            }
        } catch (IOException e) {
                System.out.println("File not found.");
        }
        
        helper.deregisterProducer();
    }
}
