package com.example.expense_tracker.config;

import org.springframework.amqp.core.Queue; // <-- BURASI Kesinlikle bu paket olmalı!
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    public static final String EXPENSE_QUEUE = "expense.created.queue";

    @Bean
    public Queue expenseQueue() {
        return new Queue(EXPENSE_QUEUE,true);
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
