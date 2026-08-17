package com.example.budget_service.listener;

import com.example.budget_service.Entity.Budget;
import com.example.budget_service.Entity.ProcessedEvent;
import com.example.budget_service.event.ExpenseCreatedEvent;
import com.example.budget_service.repository.BudgetRepository;
import com.example.budget_service.repository.ProcessedEventRepository;
import com.example.budget_service.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BudgetEventListener {
     private final BudgetRepository budgetRepository;
     private final ProcessedEventRepository processedEventRepository;
     private final BudgetService budgetService;
     private final ObjectMapper objectMapper;

     @RabbitListener(queuesToDeclare = @Queue(name="expense.created.queue", durable="true"))
     @Transactional
     public void handleExpenseCreated(String payloadMessage) {
         try {
             // Gelen String payload'u güvenli bir şekilde nesneye çeviriyoruz
             ExpenseCreatedEvent event = objectMapper.readValue(payloadMessage, ExpenseCreatedEvent.class);

             // 1. Idempotency Kontrolü
             if (processedEventRepository.existsById(event.getEventId())) {
                 return;
             }

             // 2. Bütçe güncelleme işlemi
             budgetService.updateBudgetAfterExpense(event.getUserId(), event.getAmount());

             // 3. İşlendi olarak işaretle
             ProcessedEvent processedEvent = new ProcessedEvent();
             processedEvent.setEventId(event.getEventId());
             processedEventRepository.save(processedEvent);

         } catch (Exception e) {
             System.err.println("Mesaj işlenirken dönüştürme hatası: " + e.getMessage());
             throw new RuntimeException(e);
         }
     }


}
