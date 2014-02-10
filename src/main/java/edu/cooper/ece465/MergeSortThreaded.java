/*
 * Authors: Christian Sherland
 *          Ethan Lusterman
 *          Michael Scibor
 *          Elli Rappaport
 *
 * Date:    2/6/14
 *
 * MergeSortThreaded.java
 *      Takes a list of integer inputs from test.txt and applies
 *      the merge sort algorithm to the data using a mutli-threaded
 *      mergesort algorithm
 *  
 *      The sorted result is written to outThreaded.txt
 *      
 *
 */

package edu.cooper.ece465;
import java.util.*;
import java.io.*;

public class MergeSortThreaded {
    public static final int PROD_SIZE = 100;
    public static int CORES = Runtime.getRuntime().availableProcessors(); 

    public static void main(String[] args) {
        
        /*
         * SERIAL APPROACH
         */
        // Read input
        List<Integer> inputSerial = new ArrayList<Integer>();
        try {
            String currentInput;
            BufferedReader br = new BufferedReader(new FileReader("test.txt"));
         
            while ((currentInput = br.readLine()) != null) {
                inputSerial.add(Integer.parseInt(currentInput));
            }

        } catch (IOException e) {
            System.out.println("Error: input file not found");
        }

        // Sort the input
        Integer[] inputSerialArray = inputSerial.toArray(new Integer[inputSerial.size()]);
        MergeSort m = new MergeSort();
        long startTime = System.nanoTime();
        m.sort(inputSerialArray);
        
        // Determine how long the search took
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Serial search executed in " + duration + " nanoseconds");

        // Write the output to file
        try {
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter("outSerial.txt"));
            
            for (int i = 0; i < inputSerialArray.length; i++) {
                outputWriter.write(Integer.toString(inputSerialArray[i]));
                outputWriter.newLine();
            }

            outputWriter.flush(); 
            outputWriter.close();

        } catch (IOException e) {
            System.out.println("Error: write error");
        }


        /*
         *  THREADED APPROACH
         */
        MergeHelper helper = new MergeHelper();
        
        // Single producer to read input and write output
        MergeProducer p1 = new MergeProducer(helper, 1);
        p1.start();

        // Consumers based upon number of cores
        List <MergeConsumer> consumers = new ArrayList<MergeConsumer>();
        for (int i=1; i < CORES; i++) {
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
