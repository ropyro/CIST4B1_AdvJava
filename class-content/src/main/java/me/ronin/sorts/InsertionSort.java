package me.ronin.sorts;

public class InsertionSort {

    public static void sort(int[] arr) {
        // Start from the second element (index 1) as the first is assumed sorted
        for (int i = 1; i < arr.length; i++) {
            // The current element to be inserted into the sorted sub-array
            int key = arr[i];
            // Index of the last element in the sorted sub-array
            int j = i - 1;

            // Move elements of the sorted sub-array that are greater than the key
            // one position to the right to make space
            while (j >= 0 && key < arr[j]) {
                arr[j + 1] = arr[j];
                j--;
            }

            // Place the key in its correct position
            arr[j + 1] = key;
        }
    }
}
