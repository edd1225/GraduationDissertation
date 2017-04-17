package com.gangsterhyj.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by gangsterhyj on 17-2-17.
 */
@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_NAME = "coupons";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME);
    }
}

