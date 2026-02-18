package me.ronin;

import me.ronin.analysis.SaleRecord;
import me.ronin.analysis.SalesAnalyzer;
import me.ronin.utils.DataHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * @author roninrichman
 * <p>
 * Full version of my project can be found here:
 * https://github.com/ropyro/CIST4B1_AdvJava/tree/main/labs/lab-week-3-sales-analysis
 * <p>
 * Sources:
 * 1: LocalDate class help: https://stackoverflow.com/questions/3985392/generate-random-date-of-birth
 * 2: Really interesting video about the JIT and hacky ways to warm up code for benchmarking: https://www.youtube.com/watch?v=7af_QJiLWHI
 * 3: Creating simple CSV file with native jdk utilities: https://stackoverflow.com/questions/19027473/writing-a-csv-file-using-buffered-writer-in-java
 * 4: Setting up JMH with maven: https://www.youtube.com/watch?v=mmpzodF8J4Y
 * 5: More info on JMH usage: https://www.youtube.com/watch?v=Bi0E7w1ZFFA&t=1688s
 */
public class Main {

    public static void main(String[] args) throws IOException {
        //This code below warms up the JVM and tells the JIT to take the code seriously
        //From my understanding, JMH (Java microbenchmark harness) uses a "blackhole" to tell the JIT
        //that the data being passed from a method is needed by actually shoving it somewhere.
        //The warmUpBlackHole variable is my attempt to emulate this behavior.
        //This stops the JIT from "optimizing the code away" see source 2 & 5 above.
        long warmUpBlackHole = 0;
        for (int i = 0; i < 1000; i++) {
            long[] results = runTest(1000, true);
            warmUpBlackHole += results[0];
        }
        System.out.println("Warm up complete, this is the bh total (not helpful but needed to be used): " + warmUpBlackHole);

        //Run the tests and save the results to a 2d array
        long[][] results = runTests(true,
                100, 500, 1_000, 1_500, 2500, 5000, 8500, 10_000, 25_000, 50_000, 75_000, 100_000);

        //Save the results to a CSV for graph generation
        DataHelper.saveResultsToCSV(results);
        //Print results for all tests for easy reading & comparing in the cli
        printResults(results);

    }

    private static long[][] runTests(boolean verbosity, int... recordCounts) throws IOException {
        long[][] results = new long[recordCounts.length][];
        for (int i = 0; i < recordCounts.length; i++) {
            results[i] = runTest(recordCounts[i], verbosity);
        }
        return results;
    }

    private static long[] runTest(int recordCount, boolean verbose) throws IOException {
        System.out.println("Running tests on: " + recordCount + " sale records");
        //Generate CSV file
        File dataFile = DataHelper.generateRecordsCSV(recordCount, "SalesRecords");
        //intialize the analyzer
        SalesAnalyzer salesAnalyzer = new SalesAnalyzer(dataFile);

        //Start and end time tracking variables
        long start = 0;
        long end = 0;
        long[] results = new long[7];
        results[0] = recordCount;

        //Test 1: loading data from csv
        if (verbose) System.out.println("test: loading the data from CSV");
        start = System.nanoTime();
        salesAnalyzer.loadData();
        end = System.nanoTime();
        if (verbose) System.out.println("test completed in: " + (end - start) + " nano seconds \n");
        results[1] = (end - start);

        //Test 2: retrieving latest sale
        if (verbose) System.out.println("test: retrieve latest sale");
        start = System.nanoTime();
        SaleRecord latestSale = salesAnalyzer.getLatestSale();
        end = System.nanoTime();
        if (verbose) System.out.println("Latest sale date: " + latestSale.saleDate());
        if (verbose) System.out.println("test completed in: " + (end - start) + " nano seconds \n");
        results[2] = (end - start);

        //Test 3: calculating total revenue
        if (verbose) System.out.println("test: calculate total revenue");
        start = System.nanoTime();
        double total = salesAnalyzer.getTotalRevenue();
        end = System.nanoTime();
        System.out.println("Total revenue: " + total);
        if (verbose) System.out.println("test completed in: " + (end - start) + " nano seconds \n");
        results[3] = (end - start);

        //Test 4: check for duplicate sale ids (nested loops)
        //skip for datasets larger than 50000
        results[4] = 0;
        if (recordCount <= 100_000) {
            if (verbose) System.out.println("test: nested loop dupe check");
            start = System.nanoTime();
            var dupes = salesAnalyzer.getDuplicatesViaNestedLoops();
            end = System.nanoTime();
            if (verbose) System.out.println("# of dupes found: " + dupes.size());
            if (verbose) System.out.println("test completed in: " + (end - start) + " nano seconds \n");
            results[4] = (end - start);
        }

        //Test 5: check for duplicate sale ids (map + loop)
        if (verbose) System.out.println("test: hash map dupe check");
        start = System.nanoTime();
        var dupes2 = salesAnalyzer.getDuplicatesViaMap();
        end = System.nanoTime();
        if (verbose) System.out.println("# of dupes found: " + dupes2.size());
        if (verbose) System.out.println("test completed in: " + (end - start) + " nano seconds \n");
        results[5] = (end - start);

        //Test 6: search for sale by its id
        if (verbose) System.out.println("test: search for sale by id (id to be checked is 1)");
        start = System.nanoTime();
        SaleRecord saleById = salesAnalyzer.getSaleById(100);
        end = System.nanoTime();
        if (verbose) if (saleById != null) System.out.println("Product of sale with id 1 " + saleById.productName());
        if (verbose) System.out.println("test completed in: " + (end - start) + " nano seconds \n");
        results[6] = (end - start);

        return results;
    }

    private static void printResults(long[]... results) {
        for (long[] result : results) {
            System.out.println(
                    result[0] + " records: \n" +
                            "Loading data:  " + result[1] + " " +
                            "Retrieve latest sale:  " + result[2] + " " +
                            "Calculate Total Revenue:  " + result[3] + " " +
                            "Check for duplicates (nested loops):  " + result[4] + " " +
                            "Check for duplicates (map + loop):  " + result[5] + " " +
                            "Search for sale by id:  " + result[6] + "\n");
        }
    }
}
