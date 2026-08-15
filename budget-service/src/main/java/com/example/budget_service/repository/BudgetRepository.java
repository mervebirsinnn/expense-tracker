package com.example.budget_service.repository;

import com.example.budget_service.Entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget,Long> {

    Optional<Budget> findByUserId(Long userId);
}
