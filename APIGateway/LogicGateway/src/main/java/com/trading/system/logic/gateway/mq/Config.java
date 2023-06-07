package com.trading.system.logic.gateway.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class Config {

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
            try (Connection conn = connectionFactory.newConnection()) {
                isConnected = conn.isOpen();
                connection = conn;
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
    public Channel channel(Connection connection) throws IOException, TimeoutException {
        Channel channel;

        try (Channel auxChannel = connection.createChannel()) {
            channel = auxChannel;
        } catch (Exception e) {
            LOGGER.error("Error while creating channel", e);
            throw e;
        }

        channel.queueDeclare("IBGatewayQueue", false, false, false, null);
        return channel;
    }

}