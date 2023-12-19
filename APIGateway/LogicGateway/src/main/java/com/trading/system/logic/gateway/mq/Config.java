package com.trading.system.logic.gateway.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    public static final String RPC_MESSAGE_QUEUE = "IBGatewayQueue";
    public static final String RPC_REPLY_MESSAGE_QUEUE = "RpcReplyIBGatewayQueue";
    public static final String RPC_EXCHANGE = "IBGatewayRpcExchange";

    /** *
     * Set sending RPCQueue message
     Configure the Send Message Queue*/
    @Bean
    public Queue msgQueue() {
        return new Queue(RPC_MESSAGE_QUEUE);
    }

    /** *
     * Return Queue Configuration
     */
    @Bean
    public Queue replyQueue() {
        return new Queue(RPC_REPLY_MESSAGE_QUEUE);
    }

    /** *
     * Switch setting
     */
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(RPC_EXCHANGE);
    }

    /** *
     * Queuing and Switch Link Request
     */
    @Bean
    public Binding msgBinding() {
        return BindingBuilder.bind(msgQueue()).to(exchange()).with(RPC_MESSAGE_QUEUE);
    }

    /** *
     * Back to Queue and Switch Link
     */
    @Bean
    public Binding replyBinding() {
        return BindingBuilder.bind(replyQueue()).to(exchange()).with(RPC_REPLY_MESSAGE_QUEUE);
    }

    /** *
     * Use RabbitTemplate Send and receive messages
     * And set callback queue address
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setReplyAddress(RPC_REPLY_MESSAGE_QUEUE);
        template.setReplyTimeout(60000);

        return template;
    }

    /** *
     * Configure listener for return queue
     */
    @Bean
    public SimpleMessageListenerContainer replyContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RPC_REPLY_MESSAGE_QUEUE);
        container.setMessageListener(rabbitTemplate(connectionFactory));

        return container;
    }
}
