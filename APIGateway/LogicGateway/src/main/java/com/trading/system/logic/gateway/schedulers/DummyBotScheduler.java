package com.trading.system.logic.gateway.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class DummyBotScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DummyBotScheduler.class);
    @Scheduled(cron = "*/10 * * * * *")
    public void scheduleDummyBotLogic() {
        long now = System.currentTimeMillis() / 1000;
        LOGGER.info("Schedule dummy bot logic using cron jobs - " + now);


    }
}
