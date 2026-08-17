package com.example.budget_service.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "budgets", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id"})
})
@Data
public class Budget {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false, unique = true) // unique = true eklendi
    private Long userId;
    private BigDecimal totalBudget;
    private BigDecimal remainingBudget;



}
