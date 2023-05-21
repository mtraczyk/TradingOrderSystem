package com.trading.system.logic.gateway.schedulers;

import com.trading.system.logic.gateway.data.AggregatesBarQuote;
import com.trading.system.logic.gateway.rest.PolygonRestCall;
import com.trading.system.logic.gateway.script.invokers.ScriptInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Configuration
@EnableScheduling
public class DummyBotScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DummyBotScheduler.class);

    private final PolygonRestCall polygonRestCall;
    private final int windowSize;

    public DummyBotScheduler(PolygonRestCall polygonRestCall, @Value("${bots.dummy.windowSize}") int windowSize) {
        this.polygonRestCall = polygonRestCall;
        this.windowSize = windowSize;
    }

    // cron job invoked every 20 seconds
    @Scheduled(cron = "*/20 * * * * *")
    public void scheduleDummyBotLogic() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LOGGER.info("Schedule dummy bot logic using cron jobs {}", now);

        // polygon call for a Vanguard s&p500 ETF
        AggregatesBarQuote[] aggregatesBarQuotes = polygonRestCall.getAggregateBars("VOO", "1", "day",
                now.minusDays(windowSize).format(dtf), now.format(dtf)).results();

        String restCallResult = Arrays.toString(aggregatesBarQuotes);
        LOGGER.info("Polygon API call result: {}", restCallResult);

        /* invoke a python script to do the logic
         python script will return a list of buy/sell signals
         for each signal, call the order gateway to place the order */
        try {
            ScriptInvoker.invokeBot("APIGateway/LogicGateway/BotsLogic/dummy-bot.py");
        } catch (Exception e) {
            LOGGER.error("Error invoking dummy bot logic", e);
        }
    }
}
