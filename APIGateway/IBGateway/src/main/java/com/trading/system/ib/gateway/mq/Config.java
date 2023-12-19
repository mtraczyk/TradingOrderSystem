package com.trading.system.ib.gateway.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    public static final String RPC_MESSAGE_QUEUE = "IBGatewayQueue";
    public static final String RPC_REPLY_MESSAGE_QUEUE = "RpcReplyIBGatewayQueue";
    public static final String RPC_EXCHANGE = "IBGatewayRpcExchange";

    /** *
     * Configure the Send Message Queue
     */
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
    public TopicExchange topicExchange() {
        return new TopicExchange(RPC_EXCHANGE);
    }

    /** *
     * Queuing and Switch Link Request
     */
    @Bean
    public Binding msgBinding() {
        return BindingBuilder.bind(msgQueue())
                .to(topicExchange())
                .with(RPC_MESSAGE_QUEUE);
    }

    /** *
     * Back to Queue and Switch Link
     */
    @Bean
    public Binding replyBinding() {
        return BindingBuilder.bind(replyQueue())
                .to(topicExchange())
                .with(RPC_REPLY_MESSAGE_QUEUE);
    }
}
