package me.ronin.analysis;

import java.time.LocalDate;

public record SaleRecord(int saleId, LocalDate saleDate, double total, String productName) {
}
