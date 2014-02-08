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
        
        
        // Read input
        try {

            String currentInput;
            List<Integer> input = new ArrayList<Integer>();
            BufferedReader br = new BufferedReader(new FileReader("test.txt"));
            
            while ((currentInput = br.readLine()) != null) { 
                input.add(Integer.parseInt(currentInput)); 
            }
            helper.setExpectedSize(input.size());

            int sizeOfSplit = input.size()/(MergeSortThreaded.CORES-1);

            /*
             * divide the list up for each consumer
             */
            int i;
            for (i = 0; (i + sizeOfSplit) < input.size(); i += sizeOfSplit) {
                helper.putUnsorted(input.subList(i, i+sizeOfSplit).toArray(new Integer[sizeOfSplit])); 
            } 
            helper.putUnsorted(input.subList(i, input.size()).toArray(new Integer[input.size()-i])); 

        } catch (IOException e) {
                System.out.println("File not found.");
        }
        helper.setDoneSorting(true);

        // Wait for the sorted list
        Integer[] sorted = helper.getSorted();
       
        // Write the output
        try {
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter("outThreaded.txt"));
            
            for (int i = 0; i < sorted.length; i++) {
                outputWriter.write(Integer.toString(sorted[i]));
                outputWriter.newLine();
            }

            outputWriter.flush(); 
            outputWriter.close();

        } catch (IOException e) {
            System.out.println("Error: write error");
        }
    }
}
