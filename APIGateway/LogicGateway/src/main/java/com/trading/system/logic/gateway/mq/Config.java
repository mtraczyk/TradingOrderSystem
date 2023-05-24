package com.trading.system.logic.gateway.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class Config {

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        return connectionFactory;
    }

    @Bean
    public Connection connection(ConnectionFactory connectionFactory) throws IOException, TimeoutException {
        return connectionFactory.newConnection();
    }

    @Bean
    public Channel channel(Connection connection) throws IOException {
        Channel channel = connection.createChannel();
        channel.queueDeclare("IBGatewayQueue", false, false, false, null);
        return channel;
    }

}
