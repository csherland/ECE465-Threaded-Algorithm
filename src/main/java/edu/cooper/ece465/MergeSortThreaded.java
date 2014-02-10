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
    public static int NPROD;
    public static int NCONS;

    public static void main(String[] args) {

        // Load the project properties
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/threadedMerge.properties"));
        } catch (IOException e) {
            System.out.println("Error: could not locate properties file");
        }

        NPROD = Integer.parseInt((String)properties.get("NPROD"));
        NCONS = Integer.parseInt((String)properties.get("NCONS"));

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
        System.out.println("Serial sort executed in " + duration/(1000000000.0) + " seconds");

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
        List <MergeProducer> producers = new ArrayList<MergeProducer>();
        for (int i=0; i < NPROD; i++) {
            producers.add(new MergeProducer(helper, i));
            producers.get(i).start();
        }

        List <MergeConsumer> consumers = new ArrayList<MergeConsumer>();
        for (int i=0; i < NCONS; i++) {
            consumers.add(new MergeConsumer(helper, i));
            consumers.get(i).start();
        }
    }
}
