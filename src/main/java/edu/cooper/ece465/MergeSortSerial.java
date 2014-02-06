/*
 * Authors: Christian Sherland
 *          Ethan Lusterman
 *          Michael Scibor
 *          Elli Rappaport
 *
 * Date:    2/6/14
 *
 * MergeSortSerial.java
 *      Takes a list of integer inputs from test.txt and applies
 *      the merge sort algorithm to the data
 *  
 *      The sorted result is written to outSerial.txt
 *
 */

package edu.cooper.ece465;

import java.util.*;
import java.io.*;
import java.lang.*;

public class MergeSortSerial {

    public static void main(String[] args) {

        List<Integer> input = new ArrayList<Integer>();

        // Read input
        try {
            String currentInput;
            BufferedReader br = new BufferedReader(new FileReader("test.txt"));
         
            while ((currentInput = br.readLine()) != null) {
                input.add(Integer.parseInt(currentInput));
            }

        } catch (IOException e) {
            System.out.println("Error: input file not found");
        }

        // Sort the input
        Integer[] inputArray = input.toArray(new Integer[input.size()]);
        MergeSort m = new MergeSort();
        m.sort(inputArray);

        // Write the output to file
        try {
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter("outSerial2.txt"));
            
            for (int i = 0; i < inputArray.length; i++) {
                outputWriter.write(Integer.toString(inputArray[i]));
                outputWriter.newLine();
            }

            outputWriter.flush(); 
            outputWriter.close();

        } catch (IOException e) {
            System.out.println("Error: write error");
        }
 
    }
}
