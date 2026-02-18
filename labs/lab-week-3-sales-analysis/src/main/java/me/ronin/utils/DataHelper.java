package me.ronin.utils;

import me.ronin.analysis.SaleRecord;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataHelper {

    public static File generateRecordsCSV(int dataSize, String fileName) throws IOException {
        //Record generation
        List<SaleRecord> recordsList = new ArrayList<>();
        for(int i = 0; i < dataSize; i++){
            //todo: add chance of duplicate id
            recordsList.add(new SaleRecord(
                    i + 1,
                    getRandomDate(),
                    (long) (Math.random()*1000000),
                    getRandomProduct()));
        }

        //Serialization to CSV
        //source: https://stackoverflow.com/questions/19027473/writing-a-csv-file-using-buffered-writer-in-java
        File file = new File(fileName + ".csv");
        if(!file.exists()) file.createNewFile();

        FileWriter fw = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fw);
        writer.write("sale_id,sale_date,amount,product\n");
        for(SaleRecord record : recordsList){
            writer.write(toCSVLine(record));
        }
        writer.close();
        fw.close();

        return file;
    }

    public static File saveResultsToCSV(long[]... results) throws IOException {
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
        return file;
    }


    /**
     * Simple random date generator for dates between 1975 -> 2025
     * Leap years not accounted for so only 365 day options possible
     * @return LocaleDate object of the random date
     */
    public static LocalDate getRandomDate(){
        return LocalDate.ofYearDay((int)(Math.random()*50) + 1975, (int)(Math.random()*364) + 1);
    }

    public static String toCSVLine(SaleRecord record){
        return record.saleId() + "," + record.saleDate().toString() + "," + record.total() + "," + record.productName() + "\n";
    }

    public static SaleRecord from(String csvLine){
        String[] components = csvLine.split(",");
        int saleId = Integer.parseInt(components[0]);
        LocalDate saleDate = LocalDate.parse(components[1]);
        Double total = Double.parseDouble(components[2]);
        return new SaleRecord(saleId, saleDate, total, components[3]);
    }

    public static String getRandomProduct(){
        switch((int)(Math.random()*5)){
            case 5 -> {
                return "Toy";
            }
            case 4 -> {
                return "Gadget";
            }
            case 3 -> {
                return "Computer";
            }
            case 2 -> {
                return "Thingy";
            }
            case 1 -> {
                return "Thing2";
            }
            default -> {
                return "Doohickey";
            }
        }
    }
}
