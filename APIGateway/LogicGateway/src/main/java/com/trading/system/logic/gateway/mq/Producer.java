package com.trading.system.logic.gateway.mq;

import org.springframework.amqp.core.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class Producer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);
    private final RabbitTemplate rabbitTemplate;

    public Producer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(byte[] message) throws IOException {
        LOGGER.info("Sending message with a size of {}", message.length);

        Message newMessage = MessageBuilder.withBody(message).build();
        Message result = rabbitTemplate.sendAndReceive(Config.RPC_EXCHANGE, Config.RPC_MESSAGE_QUEUE, newMessage);

        String response = "";
        if (result != null) {
            // To get message sent correlationId
            String correlationId = newMessage.getMessageProperties().getCorrelationId();

            response = new String(result.getBody());
            if (response.equals(correlationId)) {
                LOGGER.info("The correlation id is the same as the message id");
            } else {
                LOGGER.info("The correlation id is not the same as the message id");
            }

            LOGGER.info("For correlation id {}, got a response: {}", correlationId, response);
        } else {
            LOGGER.info("No response received");
        }
    }
}
