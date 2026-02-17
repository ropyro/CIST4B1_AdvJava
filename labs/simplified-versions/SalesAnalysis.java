import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author roninrichman
 *
 * Full version of my project utilizing JMH for accurate results can be found:
 * https://github.com/ropyro/CIST4B1_AdvJava/tree/main/labs/lab-week-3-sales-analysis
 *
 * Sources:
 * 1: LocalDate class help: https://stackoverflow.com/questions/3985392/generate-random-date-of-birth
 * 2: Really interesting video about the JIT and hacky ways to warm up code for benchmarking: https://www.youtube.com/watch?v=7af_QJiLWHI
 * 3: Creating simple CSV file with native jdk utilities: https://stackoverflow.com/questions/19027473/writing-a-csv-file-using-buffered-writer-in-java
 * 4: Setting up JMH with maven: https://www.youtube.com/watch?v=mmpzodF8J4Y
 * 5: More info on JMH usage: https://www.youtube.com/watch?v=Bi0E7w1ZFFA&t=1688s
 */
public class SalesAnalysis {

    public static void main(String[] args) throws IOException {
        //This code below warms up the JVM and tells the JIT to take the code seriously
        //From my understanding, JMH (Java microbenchmark harness) uses a "blackhole" to tell the JIT
        //that the data being passed from a method is needed by actually shoving it somewhere.
        // This stops the JIT from "optimizing the code away" see source 2 & 5 above
        long warmUpBlackHole = 0;
        for (int i = 0; i < 1000; i++) {
            long[] results = runTest(1000, true);
            warmUpBlackHole += results[0];
        }
        System.out.println("Warm up complete, this is the bh total (not helpful but needed to be used): " + warmUpBlackHole);

        //Run the tests and save the results to an array
        long[][] results = {
                runTest(100, true),
                runTest(1_000, true),
                runTest(10_000, true),
                runTest(100_000, true)};

        //Print results for all tests for easy reading & comparing in the cli
        printResults(results);
        DataHelper.saveResultsToCSV(results);
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
        long[] results = new long[6];
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

        //Test 4: check for duplicate sale ids
        if (verbose) System.out.println("test: hash map dupe check");
        start = System.nanoTime();
        var dupes = salesAnalyzer.getDuplicatesViaMap();
        end = System.nanoTime();
        if (verbose) System.out.println("# of dupes found: " + dupes.size());
        if (verbose) System.out.println("test completed in: " + (end - start) + " nano seconds \n");
        results[4] = (end - start);

        //Test 5: search for sale by its id
        if (verbose) System.out.println("test: search for sale by id (id to be checked is 1)");
        start = System.nanoTime();
        SaleRecord saleById = salesAnalyzer.getSaleById(100);
        end = System.nanoTime();
        if (verbose) if (saleById != null) System.out.println("Product of sale with id 1 " + saleById.productName());
        if (verbose) System.out.println("test completed in: " + (end - start) + " nano seconds \n");
        results[5] = (end - start);

        return results;
    }

    private static void printResults(long[]... results) {
        for (long[] result : results) {
            System.out.println(
                    result[0] + " records: \n" +
                            "Loading data:  " + result[1] + " " +
                            "Retrieve latest sale:  " + result[2] + " " +
                            "Calculate Total Revenue:  " + result[3] + " " +
                            "Check for duplicates:  " + result[4] + " " +
                            "Search for sale by id:  " + result[5] + "\n");
        }
    }
}

record SaleRecord(int saleId, LocalDate saleDate, double total, String productName) {
}

class SalesAnalyzer {

    private List<SaleRecord> records;
    private File recordFile;

    public SalesAnalyzer(File recordFile) {
        this.records = new ArrayList<>();
        this.recordFile = recordFile;
    }

    //TODO: rewrite using non stream methods to be more clear of the big O complexity
    public void loadData() throws IOException {
        FileReader fr = new FileReader(recordFile);
        BufferedReader reader = new BufferedReader(fr);
        reader.lines().skip(1) //skips the CSV header line
                .forEach(line -> records.add(DataHelper.from(line)));
        reader.close();
        fr.close();
    }

    //O(N)
    public SaleRecord getLatestSale() {
        int currentLatestIndex = 0;
        for (int i = 1; i < records.size(); i++) {
            SaleRecord currentLatest = records.get(currentLatestIndex);
            if (records.get(i).saleDate().isAfter(currentLatest.saleDate())) {
                currentLatestIndex = i;
            }
        }
        return records.get(currentLatestIndex);
    }

    //O(N)
    public double getTotalRevenue() {
        double total = 0;
        for (SaleRecord record : records) {
            total += record.total();
        }
        return total;
    }

    /**
     * O(n^3) nested loops + contains method requires loop, maybe optimizable with a map?
     * The arraylist class contains(obj o) method calls -> indexOf(o) which calls -> indexOfRange(o, start, fin)
     * which utilizes a for loop to check if the object is contained within the range, the range being the entire array
     * as specified by the contains method
     *
     * @return List<SaleRecord> of duplicate records or null if no duplicates
     */
    public List<SaleRecord> getDuplicatesViaNestedLoops() {
        //List to store duplicates
        List<SaleRecord> dupes = new ArrayList<>();
        //Loop through all records
        for (int i = 0; i < records.size(); i++) {
            //Loop through all records for each record in list
            for (int j = 0; j < records.size(); j++) {
                //skip if inner loop record and outer loop record are the same
                if (i == j) continue;
                //Variables to hold the current records being compared
                SaleRecord iRecord = records.get(i);
                SaleRecord jRecord = records.get(j);
                //Check if inner loop record & outer loop record have the same ID
                if (iRecord.saleId() == jRecord.saleId()) {
                    //Add both records to dupes list if not already added
                    if (!dupes.contains(iRecord)) dupes.add(iRecord);
                    if (!dupes.contains(jRecord)) dupes.add(jRecord);
                }
            }
        }
        return dupes;
    }

    /**
     * O(N) one pass using a map to keep track of seen ids, which has lookups that are O(1)
     * Adding the original record being compared may make time complexity O(n^2) since the contains
     * method requires O(N) time making total complexity N*N. This original could just be ignored to
     * decrease time complexity
     *
     * @return List<SaleRecord> of duplicate records or null if no duplicates
     */
    public List<SaleRecord> getDuplicatesViaMap() {
        // Sale Id, Record; Keeps track of already seen sale records
        Map<Integer, SaleRecord> seen = new HashMap<>();
        //Keeps track of records found to have duplicate ids
        List<SaleRecord> dupes = new ArrayList<>();
        //loops through all records
        for (SaleRecord record : records) {
            //Checks if we have already seen a record with this id
            if (seen.containsKey(record.saleId())) {
                //Adds current record if id already seen (meaning it is a dupe)
                dupes.add(record);
                //Gets the original record added to the map
                SaleRecord original = seen.get(record.saleId());
                //If original is not already in the dupes list, then add it
                if (!dupes.contains(original)) dupes.add(original);
            } else {
                //Records with new sale ids we havent seen yet get added to the seen map
                seen.put(record.saleId(), record);
            }
        }
        return dupes;
    }

    /**
     * O(n) one single for loop to check for the first record with specified id
     *
     * @param id
     * @return returns desired record or null if one does not exist with the given id
     */
    public SaleRecord getSaleById(int id) {
        for (SaleRecord record : records) {
            if (record.saleId() == id) return record;
        }
        return null;
    }
}

class DataHelper {

    public static File generateRecordsCSV(int dataSize, String fileName) throws IOException {
        //Record generation
        List<SaleRecord> recordsList = new ArrayList<>();
        for (int i = 0; i < dataSize; i++) {
            //todo: add chance of duplicate id
            recordsList.add(new SaleRecord(
                    i + 1,
                    getRandomDate(),
                    (Math.random() * 1000000),
                    getRandomProduct()));
        }

        //Serialization to CSV
        //source: https://stackoverflow.com/questions/19027473/writing-a-csv-file-using-buffered-writer-in-java
        File file = new File(fileName + ".csv");
        if (!file.exists()) file.createNewFile();

        FileWriter fw = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fw);
        writer.write("sale_id,sale_date,amount,product\n");
        for (SaleRecord record : recordsList) {
            writer.write(toCSVLine(record));
        }
        writer.close();
        fw.close();

        return file;
    }

    public static void saveResultsToCSV(long[]... results) throws IOException {
        File file = new File("results.csv");
        if (!file.exists()) file.createNewFile();

        FileWriter fw = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fw);
        writer.write("record_count,load_data,retrieve_latest,calculate_total_rev,check_dupes,search_by_id\n");
        for (long[] result : results) {
            writer.write(result[0] + "," +
                    result[1] + "," +
                    result[2] + "," +
                    result[3] + "," +
                    result[4] + "," +
                    result[5] + "\n");
        }
        writer.close();
        fw.close();
    }


    /**
     * Simple random date generator for dates between 1975 -> 2025
     * Leap years not accounted for so only 365 day options possible
     *
     * @return LocaleDate object of the random date
     */
    public static LocalDate getRandomDate() {
        return LocalDate.ofYearDay((int) (Math.random() * 50) + 1975, (int) (Math.random() * 364) + 1);
    }

    public static String toCSVLine(SaleRecord record) {
        return record.saleId() + "," + record.saleDate().toString() + "," + record.total() + "," + record.productName() + "\n";
    }

    public static SaleRecord from(String csvLine) {
        String[] components = csvLine.split(",");
        int saleId = Integer.parseInt(components[0]);
        LocalDate saleDate = LocalDate.parse(components[1]);
        Double total = Double.parseDouble(components[2]);
        return new SaleRecord(saleId, saleDate, total, components[3]);
    }

    public static String getRandomProduct() {
        String[] products = {"Toy", "Gadget", "Computer", "Thingy", "Thingy2", "Doohickey"};
        return products[(int) (Math.random() * 6)];
    }
}
