package me.ronin.sorts;

public class QuickSort {

    public static void sort(int[] arr, int low, int high) {
        if (arr.length == 1 || arr.length == 0) return;
        if (low < high) {
            int partition = partition(arr, low, high);
            sort(arr, partition + 1, high);
            sort(arr, low, partition - 1);
        }
    }

    public static int partition(int[] arr, int low, int high) {
        //make pivot last element, the high
        int pivot = arr[high];
        //divide pointer one less than low
        int i = low - 1;
        //loop through from low to high
        for (int current = low; current < high; current++) {
            //check if current is <= pivot
            if (arr[current] <= pivot) {
                //increment i
                i++;
                //swap i and current
                int temp = arr[i];
                arr[i] = arr[current];
                arr[current] = temp;
            }
        }

        //swap i and pivot
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i;
    }
}
