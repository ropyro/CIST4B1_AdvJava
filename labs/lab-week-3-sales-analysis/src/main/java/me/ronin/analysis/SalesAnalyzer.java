package me.ronin.analysis;

import me.ronin.performance.PerformanceManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesAnalyzer {

    private List<SaleRecord> records;
    private PerformanceManager performanceManager;

    public SalesAnalyzer(File recordFile, PerformanceManager performanceManager){
        this.records = new ArrayList<>();
        this.performanceManager = performanceManager;
    }

    //O(N)
    public SaleRecord getLatestSale(){
        int currentLatestIndex = 0;
        for(int i = 1; i < records.size(); i++){
            SaleRecord currentLatest = records.get(currentLatestIndex);
            if(records.get(i).saleDate().isBefore(currentLatest.saleDate())){
                currentLatestIndex = i;
            }
        }
        return records.get(currentLatestIndex);
    }

    //O(N)
    public long getTotalRevenue(){
        long total = 0;
        for(SaleRecord record : records){
            total += record.total();
        }
        return total;
    }

    /**
     * O(n^2) nested loops, maybe optimizable with a map?
     * @return List<SaleRecord> of duplicate records or null if no duplicates</SaleRecord>
     */
    public List<SaleRecord> getDuplicateSaleIds(){
        List<SaleRecord> dupes = new ArrayList<>();
        for(int i = 0; i < records.size(); i++){
            for(int j = 0; j < records.size(); j++){
                SaleRecord iRecord = records.get(i);
                SaleRecord jRecord = records.get(j);
                if(iRecord.saleId() == jRecord.saleId()){
                    if(!dupes.contains(iRecord)) dupes.add(iRecord);
                    if(!dupes.contains(jRecord)) dupes.add(jRecord);
                }
            }
        }
        return dupes;
    }

    //i love lambdas & stream api 🥰
    public SaleRecord getSaleById(int id){
        for(SaleRecord record : records){
            if(record.saleId() == id) return record;
        }
        return null;
       //Elegant stream version: return records.stream().filter(r -> r.saleId() == id).findFirst().orElse(null);
    }
}
