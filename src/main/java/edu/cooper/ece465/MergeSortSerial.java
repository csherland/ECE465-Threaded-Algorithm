package edu.cooper.ece465;

import java.util.*;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.lang.*;

public class MergeSortSerial {

    public static void main(String[] args) {

        List <Integer> myIntegers = new ArrayList<Integer>();

        // Read input
        BufferedReader br = null;
        try {
            String fromFile;
            br = new BufferedReader(new FileReader("test.txt"));
            
            while ((fromFile = br.readLine()) != null) {
                int toAdd = Integer.parseInt(fromFile);
                myIntegers.add(toAdd);
            }

        } catch (IOException e) {
            System.out.println("File not found.");
        }

        // Sort the list
        MergeSort m = new MergeSort();
        Integer[] myIntArray = myIntegers.toArray(new Integer[myIntegers.size()]);

        int[] myInts = new int[myIntArray.length];
        for (int i = 0; i < myIntArray.length; ++i) {
            myInts[i] = myIntArray[i].intValue();
        }
        m.sort(myInts);

        // Write the output
        BufferedWriter outputWriter = null; 
        try {
            outputWriter = new BufferedWriter(new FileWriter("out.txt"));
            for (int i = 0; i < myInts.length; i++) {
                outputWriter.write(Integer.toString(myInts[i]));
                outputWriter.newLine();
            }
            
            outputWriter.flush();  
            outputWriter.close();
        
        } catch (IOException e) {
            System.out.println("Error writing file");
        }
        
    }
}
