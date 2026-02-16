package me.ronin.analysis;

import java.time.LocalDate;

public record SaleRecord(int saleId, LocalDate saleDate, long total, String productName) {
}
