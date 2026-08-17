package com.example.expense_tracker.publisher;

import com.example.expense_tracker.config.RabbitMQConfig;
import com.example.expense_tracker.entity.Expense;
import com.example.expense_tracker.entity.OutboxMessage;
import com.example.expense_tracker.repository.OutboxMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
// YANLIŞ importları silin ve bunları ekleyin:
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxPublisher {
    private final OutboxMessageRepository outboxMessageRepository;
    private final RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelay = 50000)
    @Transactional
    public void publishEvents() {
       // List<OutboxMessage> unprocessedMessages = outboxMessageRepository.findByProcessedFalse();
        Slice<OutboxMessage> pendingMessages = outboxMessageRepository.findByProcessedFalseOrderByCreatedAtAsc(PageRequest.of(0, 100));
        for (OutboxMessage outboxMessage : pendingMessages) {
            try {
                rabbitTemplate.convertAndSend(RabbitMQConfig.EXPENSE_EXCHANGE, RabbitMQConfig.EXPENSE_ROUTING_KEY, outboxMessage.getPayload());
                outboxMessage.setProcessed(true);
                outboxMessageRepository.save(outboxMessage);

            } catch (Exception e) {
                System.err.println("Outbox mesajı gönderilemedi: " + e.getMessage());
            }
        }
    }

}
