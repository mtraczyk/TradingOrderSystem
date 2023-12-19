package com.trading.system.ib.gateway.mq;

import com.trading.system.data.OrderInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import utils.Utils;

@Component
public class Consumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);
    private final RabbitTemplate rabbitTemplate;

    public Consumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = Config.RPC_MESSAGE_QUEUE)
    public void process(Message message) {
        OrderInstruction orderInstruction = Utils.deserializeUsingGson(message.getBody(), OrderInstruction.class);
        LOGGER.info("Server received the following order instruction: {}", orderInstruction);

        //This is the message to be returned by the server
        Message build = MessageBuilder
                .withBody(message.getMessageProperties().getCorrelationId().getBytes())
                .build();
        rabbitTemplate.sendAndReceive(Config.RPC_EXCHANGE, Config.RPC_REPLY_MESSAGE_QUEUE, build);
    }
}
