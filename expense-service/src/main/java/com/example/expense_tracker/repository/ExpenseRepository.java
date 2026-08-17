package com.example.expense_tracker.repository;

import com.example.expense_tracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId); // Kullanıcıya ait harcamalar
}