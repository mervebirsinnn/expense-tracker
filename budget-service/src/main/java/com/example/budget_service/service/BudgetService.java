package com.example.budget_service.service;

import com.example.budget_service.Entity.Budget;
import com.example.budget_service.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    @Transactional
    public void updateBudgetAfterExpense(Long userId, BigDecimal amount) {
        Budget budget = budgetRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Bütçe bulunamadı!User ID: " + userId));

        System.out.println("Bulunan bütçenin DB ID'si: " + budget.getId());

        budget.setRemainingBudget(budget.getRemainingBudget().subtract(amount));
        budgetRepository.save(budget);
    }
}
