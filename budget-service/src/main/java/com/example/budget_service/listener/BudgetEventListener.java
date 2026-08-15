package com.example.budget_service.listener;

import com.example.budget_service.Entity.Budget;
import com.example.budget_service.event.ExpenseCreatedEvent;
import com.example.budget_service.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.Queue;
@Service
@RequiredArgsConstructor
public class BudgetEventListener {
     private final BudgetRepository repository;

     @RabbitListener(queuesToDeclare = @Queue(name="expense.created.queue", durable="true"))
    public void handleExpenseCreated(ExpenseCreatedEvent event) {
         System.out.println("Mesaj alındı kullanıcı id :"+ event.getUserId()+", Tutar:"+event);

         Budget budget = repository.findByUserId(event.getUserId()).orElseThrow(()->new RuntimeException("Bütçe bulunamadi!"));
         System.out.println("Bulunan bütçenin DB ID'si: " + budget.getId());
         budget.setRemainingBudget(budget.getRemainingBudget().subtract(event.getAmount()));
         repository.save(budget);


         System.out.println("Bütçe güncellendi! Yeni kalan: "+ budget.getRemainingBudget());
     }

}
