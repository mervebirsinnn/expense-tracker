package com.example.budget_service.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name="budgets")
@Data
public class Budget {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private BigDecimal totalBudget;
    private BigDecimal remainingBudget;



}
