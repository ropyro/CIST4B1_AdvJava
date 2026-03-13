package me.ronin;

import me.ronin.week5.Patient;
import me.ronin.week6.BasicHashTable;

import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
//        PriorityQueue<Patient> pq = new PriorityQueue<>();
//        Scanner scanner = new Scanner(System.in);
//        for(int i = 0; i < 3; i++){
//            System.out.println("Enter patient " + i + "'s name: ");
//            String name = scanner.next();
//
//            System.out.println("Enter patient " + i + "'s priority: ");
//            int priority = Integer.parseInt(scanner.next());
//            pq.offer(new Patient(name, priority));
//        }
//
//        System.out.println("\ntriage order: ");
//        for(int i = 0; i < 3; i++){
//            Patient curr = pq.poll();
//            System.out.println("#" + i + " " + curr.name);
//        }

        var table = new BasicHashTable(10);
        table.put("test", "DELETED");
    }

















    public static int countOccurances(String input, char toFind) {
        //base case if string is empty there is chars to compare
        if (input.length() == 0) return 0;
        //Check if the first char is the one we are looking at
        if (input.charAt(0) == toFind) {
            //recursive case: Return 1 for a found char + check for next occurance in the substring after the first char
            return 1 + countOccurances(input.substring(1), toFind);
        }
        //recursive case: If first char wasnt a match just check next substring
        return countOccurances(input.substring(1), toFind);
    }

    //hey -> yeh
    // 0h 1e 2y
    // length() = 3
    // currentIndex = 0;
    // 0y 1h 2e
    // length() = 3
    // currentIndex = 1;
    // 0y 1e 2h
    // length() = 3
    // currentIndex = 2;
    public static String reverseString(String input, int currentIndex) {
        if (currentIndex == input.length() - 1 || input.length() == 0) return input;
        String lastChar = input.substring(input.length() - 1);
        String pre = input.substring(0, currentIndex);
        String after = input.substring(currentIndex, input.length() - 1);
        return reverseString(pre + lastChar + after, ++currentIndex);
    }

    public static String reverseString(String input) {
        if (input.length() == 0) return input;
        String lastChar = input.substring(input.length() - 1);
        return lastChar + reverseString(input.substring(0, input.length() - 1));
    }

    public static int[] getRandomArray(int size) {
        int[] randomArr = new int[size];
        for (int i = 0; i < size; i++) {
            randomArr[i] = (int) (Math.random() * 100);
        }
        return randomArr;
    }

    public static void printArr(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println("");
    }

    public static void writeVertical(int number) {
        if (number < 0) {
            System.out.print("-");
            number *= -1;
        }

        if (number < 10) {
            System.out.println(number);
            return;
        }

        writeVertical(number / 10);
        System.out.println(number % 10);
    }

    public static void quickSort(int[] arr, int low, int high) {
        if (arr.length == 1 || arr.length == 0) return;
        if (low < high) {
            int partition = partition(arr, low, high);
            quickSort(arr, partition + 1, high);
            quickSort(arr, low, partition - 1);
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

    public static void mergeSort(int[] arr) {
        if (arr.length <= 1) return;

        //split
        //get the middle with integer division
        int mid = arr.length / 2;
        //create arry left from 0 to mid
        int[] left = new int[mid];
        //create arry right from mid to length
        int[] right = new int[arr.length - mid];
        //fill arrays
        for (int i = 0; i < arr.length; i++) {
            if (i < mid) {
                left[i] = arr[i];
            } else {
                right[i - mid] = arr[i];
            }
        }

        //recursive case
        mergeSort(left);
        mergeSort(right);

        //conquer
        int l = 0, r = 0;
        while (l < left.length || r < right.length) {
            if (l == left.length && r != right.length) {
                arr[l + r] = right[r];
                r++;
                continue;
            }
            if (r == right.length && l != left.length) {
                arr[l + r] = left[l];
                l++;
                continue;
            }
            if (left[l] < right[r]) {
                arr[r + l] = left[l];
                l++;
            } else {
                arr[r + l] = right[r];
                r++;
            }
        }
    }

    public static void insertionSort(int[] arr) {
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