/*
 * Original Source: http://www.vogella.com/tutorials/JavaAlgorithmsMergesort/article.html
 * 
 * Contributors:    Christian Sherland
 *                  Ethan Lusterman
 *                  Michael Scibor
 *                  Elli Rappaport
 *
 * Date:    2/6/14
 *
 * MergeSort.java
 *      Modified MergeSort implementation from the source listed above.
 *
 */

package edu.cooper.ece465;

public class MergeSort {
    private Integer[] numbers;
    private Integer[] helper;  
    
    public void sort(Integer[] values) {
        this.numbers = values;
        this.helper = new Integer[values.length];
        mergesort(0, values.length - 1);
    }
    
    private void mergesort(int low, int high) {
        // check if low is smaller then high, if not then end condition
        if (low < high) {
            // Get the index of the element which is in the middle
            int middle = low + (high - low) / 2;
 
            // Sort both halves of the array and merge them 
            mergesort(low, middle);
            mergesort(middle + 1, high);
            merge(low, middle, high);
        }
    }
    
    private void merge(int low, int middle, int high) { 
        // Copy both parts into the helper array
        for (int i = low; i <= high; i++) {
            helper[i] = numbers[i];
        }
 
        int i = low;
        int j = middle + 1;
        int k = low;

        // Copy the smallest values from either the left or the right side back
        // to the original array
        while (i <= middle && j <= high) { 
            if (helper[i] <= helper[j]) {
                numbers[k++] = helper[i++];
            } else {
                numbers[k++] = helper[j++];
            } 
        }
 
        // Copy the rest of the left side of the array into the target array
        while (i <= middle) {
            numbers[k++] = helper[i++];
        } 
    }

    public Integer[] mergeLists(Integer[] list1, Integer[] list2) {
        Integer[] mergedList = new Integer[list1.length + list2.length];

        int i = 0, j = 0, k = 0;
        while ((i < list1.length) && (j < list2.length)) {
                
            if (list1[i] <= list2[j]) {
                mergedList[k++] = list1[i++];
            } else {
                mergedList[k++] = list2[j++];
            }
        }

        while (i < list1.length) {
            mergedList[k++] = list1[i++];
        }
        while (j < list2.length) {
            mergedList[k++] = list2[j++]; 
        }  

        return mergedList;
    }
}
