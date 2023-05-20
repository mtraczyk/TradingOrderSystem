package com.trading.system.logic.gateway.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.system.logic.gateway.data.AggregateBarQuoteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

@Component
public class PolygonRestCall {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolygonRestCall.class);
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String polygonApiKey;

    public PolygonRestCall(RestTemplate restTemplate, @Value("${polygon.api.key}") String polygonApiKey) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
        this.polygonApiKey = polygonApiKey;
    }

    // rest call for aggregate bars from Polygon.io /v2/aggs/ticker/{stocksTicker}/range/{multiplier}/{timespan}/{from}/{to}?apiKey={apiKey}
    public AggregateBarQuoteResponse getAggregateBars(String stocksTicker, String multiplier, String timespan, String from, String to) {
        String urlTemplate = String.format("https://api.polygon.io/v2/aggs/ticker/%s/range/%s/%s/%s/%s?apiKey=%s", stocksTicker, multiplier, timespan, from, to, polygonApiKey);

        return restTemplate.execute(urlTemplate, HttpMethod.GET, null, response -> {
            if (response.getStatusCode() == HttpStatus.OK) {
                return objectMapper.readValue(response.getBody(), AggregateBarQuoteResponse.class);
            } else {
                LOGGER.error("Error in PolygonRestCall.getAggregateBars: " + response.getStatusCode());
                return null;
            }
        });
    }
}