package com.trading.system.logic.gateway.rest;

import com.trading.system.logic.gateway.data.AggregateBarQuoteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PolygonRestCall {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolygonRestCall.class);
    private final RestTemplate restTemplate;
    private final String polygonApiKey;

    public PolygonRestCall(RestTemplate restTemplate, @Value("${polygon.api.key}") String polygonApiKey) {
        this.restTemplate = restTemplate;
        this.polygonApiKey = polygonApiKey;
    }

    // rest call for aggregate bars from Polygon.io /v2/aggs/ticker/{stocksTicker}/range/{multiplier}/{timespan}/{from}/{to}?apiKey={apiKey}
    public AggregateBarQuoteResponse getAggregateBars(String stocksTicker, String multiplier, String timespan, String from, String to) {
        String urlTemplate = String.format("https://api.polygon.io/v2/aggs/ticker/%s/range/%s/%s/%s/%s?apiKey=%s", stocksTicker, multiplier, timespan, from, to, polygonApiKey);
        LOGGER.info("Calling Polygon.io API with url: {}", urlTemplate);

        return restTemplate.getForObject(urlTemplate, AggregateBarQuoteResponse.class);
    }
}
