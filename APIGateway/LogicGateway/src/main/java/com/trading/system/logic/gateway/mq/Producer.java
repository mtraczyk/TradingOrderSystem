package com.trading.system.logic.gateway.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class Producer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    private final Channel channel;
    private final Connection connection;

    public Producer(Channel channel, Connection connection) {
        this.channel = channel;
        this.connection = connection;
    }

    public void send(byte[] message, String queueName) throws IOException {
        LOGGER.info("Sending message to queue with a size of {}: {}", queueName, message.length);

        channel.basicPublish("", queueName, null, message);
    }

    @PreDestroy
    public void close() throws IOException, TimeoutException {
        LOGGER.info("Closing producer");

        channel.close();
        connection.close();
    }
}
