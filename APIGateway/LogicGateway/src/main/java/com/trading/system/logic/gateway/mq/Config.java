package com.trading.system.logic.gateway.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class Config {

    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
    private final String rabbitMqUrl;

    public Config(@Value("${rabbitmq.url.host}") String rabbitMqUrl) {
        this.rabbitMqUrl = rabbitMqUrl;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(rabbitMqUrl);

        return connectionFactory;
    }

    @Bean
    public Connection connection(ConnectionFactory connectionFactory) throws InterruptedException {
        Connection connection = null;
        boolean isConnected = false;

        while (!isConnected) {
            try {
                connection = connectionFactory.newConnection();
                isConnected = connection.isOpen();
            } catch (Exception e) {
                LOGGER.info("RabbitMQ is not available yet");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException interruptedException) {
                    LOGGER.error("Error while sleeping", interruptedException);
                    throw interruptedException;
                }

            }
        }

        return connection;
    }

    @Bean
    public Channel channel(Connection connection) throws IOException {
        Channel channel;

        try {
            channel = connection.createChannel();
        } catch (Exception e) {
            LOGGER.error("Error while creating channel", e);
            throw e;
        }

        channel.queueDeclare("IBGatewayQueue", false, false, false, null);
        return channel;
    }

}