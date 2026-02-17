package me.ronin.analysis;

import me.ronin.utils.DataHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesAnalyzer {

    private List<SaleRecord> records;
    private File recordFile;

    public SalesAnalyzer(File recordFile){
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
    public SaleRecord getLatestSale(){
        int currentLatestIndex = 0;
        for(int i = 1; i < records.size(); i++){
            SaleRecord currentLatest = records.get(currentLatestIndex);
            if(records.get(i).saleDate().isAfter(currentLatest.saleDate())){
                currentLatestIndex = i;
            }
        }
        return records.get(currentLatestIndex);
    }

    //O(N)
    public double getTotalRevenue(){
        double total = 0;
        for(SaleRecord record : records){
            total += record.total();
        }
        return total;
    }

    /**
     * O(n^3) nested loops + contains method requires loop, maybe optimizable with a map?
     * The arraylist class contains(obj o) method calls -> indexOf(o) which calls -> indexOfRange(o, start, fin)
     * which utilizes a for loop to check if the object is contained within the range, the range being the entire array
     * as specified by the contains method
     * @return List<SaleRecord> of duplicate records or null if no duplicates
     */
    public List<SaleRecord> getDuplicatesViaNestedLoops(){
        //List to store duplicates
        List<SaleRecord> dupes = new ArrayList<>();
        //Loop through all records
        for(int i = 0; i < records.size(); i++){
            //Loop through all records for each record in list
            for(int j = 0; j < records.size(); j++){
                //skip if inner loop record and outer loop record are the same
                if(i == j) continue;
                //Variables to hold the current records being compared
                SaleRecord iRecord = records.get(i);
                SaleRecord jRecord = records.get(j);
                //Check if inner loop record & outer loop record have the same ID
                if(iRecord.saleId() == jRecord.saleId()){
                    //Add both records to dupes list if not already added
                    if(!dupes.contains(iRecord)) dupes.add(iRecord);
                    if(!dupes.contains(jRecord)) dupes.add(jRecord);
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
     * @return List<SaleRecord> of duplicate records or null if no duplicates
     */
    public List<SaleRecord> getDuplicatesViaMap(){
        // Sale Id, Record; Keeps track of already seen sale records
        Map<Integer, SaleRecord> seen = new HashMap<>();
        //Keeps track of records found to have duplicate ids
        List<SaleRecord> dupes = new ArrayList<>();
        //loops through all records
        for(SaleRecord record : records){
            //Checks if we have already seen a record with this id
            if(seen.containsKey(record.saleId())){
                //Adds current record if id already seen (meaning it is a dupe)
                dupes.add(record);
                //Gets the original record added to the map
                SaleRecord original = seen.get(record.saleId());
                //If original is not already in the dupes list, then add it
                if(!dupes.contains(original)) dupes.add(original);
            }else{
                //Records with new sale ids we havent seen yet get added to the seen map
                seen.put(record.saleId(), record);
            }
        }
        return dupes;
    }

    /**
     * O(n) one single for loop to check for the first record with specified id
     * @param id
     * @return returns desired record or null if one does not exist with the given id
     */
    public SaleRecord getSaleById(int id){
        for(SaleRecord record : records){
            if(record.saleId() == id) return record;
        }
        return null;
    }

    /**
     * Guess is O(n)? Stream api to accomplish the work of a for loop instead with functional methods
     * @param id
     * @return returns desired record or null if one does not exist with the given id
     */
    public SaleRecord getSaleByIdStream(int id){
        return records.stream().filter(r -> r.saleId() == id).findFirst().orElse(null);
    }

    public List<SaleRecord> getRecords() {
        return records;
    }
}
