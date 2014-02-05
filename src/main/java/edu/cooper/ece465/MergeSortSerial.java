package edu.cooper.ece465;

import java.util.*;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.lang.*;

public class MergeSortSerial {

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        List <Integer> myIntegers = new ArrayList<Integer>();
        String fromFile;
        int toAdd;
        BufferedReader br = null;

        // read unsorted integer list file
        try {
            br = new BufferedReader(new FileReader("test.txt"));
            while ( !(fromFile = br.readline()).equals(null)) {
                toAdd = Integer.parseInt(fromFile);
                myIntegers.add(toAdd);
            }

        } catch (IOException e) {
            System.out.println("File not found.");
        }

        // sort list
        MergeSort m = new MergeSort();
        int[] myIntArray = myIntegers.toArray(new int[myIntegers.size()]);
        m.sort(myIntArray);

        // write sorted list file
        try {
            File file = new File("out.txt");
            FileOutputStream fos = new FileOutputStream(file);

            if (!file.exists()) {
                file.createNewFile();
            }

            ByteBuffer byteBuffer = ByteBuffer.allocate(myIntArray.length * 4);
            IntBuffer intBuffer = byteBuffer.asIntBuffer();
            intBuffer.put(myIntegers);

            byte[] array = byteBuffer.array();

            fos.write(array);

            fos.flush();
            fos.close();
        } catch (IOException e) {
            System.out.println("Error");
        }

        long endTime = System.nanoTime();
        long duration = endTime - startTime;
    }
}
