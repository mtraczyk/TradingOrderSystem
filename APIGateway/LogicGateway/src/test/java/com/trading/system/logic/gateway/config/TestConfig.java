package com.trading.system.logic.gateway.config;

import com.trading.system.logic.gateway.mq.Config;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class TestConfig {

    @MockBean
    private Config config;

    @MockBean
    private RabbitTemplate rabbitTemplate;
}
