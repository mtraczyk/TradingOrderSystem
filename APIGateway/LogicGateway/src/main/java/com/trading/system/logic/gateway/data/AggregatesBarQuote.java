package com.trading.system.logic.gateway.data;

// example {"c": 75.0875, "h": 75.15, "l": 73.7975, "n": 1, "o": 74.06, "t": 1577941200000, "v": 135647456, "vw": 74.6099 }
public record AggregatesBarQuote(double c, double h, double l, int n, double o, long t, long v, double vw) { }
