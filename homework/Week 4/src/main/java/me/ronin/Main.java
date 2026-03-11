package me.ronin;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final int TRIALS = 10; // Run each size multiple times

    public static void main(String[] args) throws IOException {
        // JVM Warm-up phase
        System.out.println("Warming up JVM...");
        for (int i = 0; i < 1000; i++) {
            int[] warmup = generateRandomArray(1000);
            MergeSort.sort(warmup);
        }
        System.gc();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Warm-up complete. Starting benchmarks...\n");

        // Run tests
        List<long[]> results = new ArrayList<>();
        for (int length = 10; length <= 100_000; length += 100) {
            long[] result = test(length);
            results.add(result);
            System.out.println("Length: " + result[0] + " | Median Time: " + result[1] + " ns");
        }

        // Save results to CSV
        long[][] resultsArray = results.toArray(new long[0][]);
        saveResultsToCSV(resultsArray);
        System.out.println("Results saved to results.csv");
    }

    public static long[] test(int length) {
        long[] times = new long[TRIALS];

        for (int t = 0; t < TRIALS; t++) {
            int[] arr = generateRandomArray(length);

            // Force GC before each trial to reduce mid-sort pauses
            System.gc();

            long before = System.nanoTime();
            MergeSort.sort(arr);
            long after = System.nanoTime();
            times[t] = after - before;
        }

        // Use the MEDIAN to ignore GC spike outliers
        Arrays.sort(times);
        long median = times[TRIALS / 2];

        return new long[]{length, median};
    }

    public static int[] generateRandomArray(int length) {
        int[] arr = new int[length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 100);
        }
        return arr;
    }

    public static void saveResultsToCSV(long[][] results) throws IOException {
        File file = new File("results.csv");
        if (!file.exists()) file.createNewFile();

        FileWriter fw = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fw);
        writer.write("array_length,time_to_sort\n");
        for (long[] result : results) {
            writer.write(result[0] + "," + result[1] + "\n");
        }
        writer.close();
        fw.close();
    }
}