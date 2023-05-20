package com.trading.system.logic.gateway.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// polygon Aggregate bars data model
@JsonIgnoreProperties(ignoreUnknown = true)
public record AggregateBarQuoteResponse(String ticker, String status, int queryCount, int resultsCount, boolean adjusted, AggregatesBarQuote[] results) { }
