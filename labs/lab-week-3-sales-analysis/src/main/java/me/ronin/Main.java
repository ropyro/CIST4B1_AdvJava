package me.ronin;

import me.ronin.utils.DataHelper;

import java.io.IOException;


/**
 * @author roninrichman
 *
 * Sources:
 * LocalDate class help: https://stackoverflow.com/questions/3985392/generate-random-date-of-birth
 * Really interesting video about the JIT and hacky ways to warm up code for benchmarking: https://www.youtube.com/watch?v=7af_QJiLWHI
 *
 */
public class Main {

    public static void main(String[] args) throws IOException {
        DataHelper.generateRecordsCSV(100, "TestFile");
    }
}
