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
    public Connection connection(ConnectionFactory connectionFactory) throws IOException, TimeoutException {
        return connectionFactory.newConnection();
    }

    @Bean
    public Channel channel(Connection connection) throws IOException, TimeoutException {
        try (Channel channel = connection.createChannel()) {
            channel.queueDeclare("IBGatewayQueue", false, false, false, null);

            return channel;
        } catch (Exception e) {
            e.printStackTrace();

            throw e;
        }
    }

}
