package com.example.expense_tracker.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue; // <-- BURASI Kesinlikle bu paket olmalı!
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    public static final String EXPENSE_QUEUE = "expense.created.queue";
    public static final String EXPENSE_EXCHANGE = "expense.exchage";
    public static final String EXPENSE_ROUTING_KEY = "expense.created.key";

    @Bean
    public Queue expenseQueue() {
        return new Queue(EXPENSE_QUEUE,true);
    }

    @Bean
    public TopicExchange expenseExchange() {
        return new TopicExchange(EXPENSE_EXCHANGE);

    }
    @Bean
    public Binding expenseBinding(Queue expenseQueue, TopicExchange expenseExchange) {
        return BindingBuilder.bind(expenseQueue).to(expenseExchange).with(EXPENSE_ROUTING_KEY);
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
