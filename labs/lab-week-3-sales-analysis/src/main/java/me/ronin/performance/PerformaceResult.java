package me.ronin.performance;

import java.time.Instant;

public record PerformaceResult(String name, Instant when, long dataSize, long time) { }
