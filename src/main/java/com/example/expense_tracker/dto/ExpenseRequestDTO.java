package com.example.expense_tracker.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseRequestDTO {
    @NotNull(message = "Tutar boş olamaz")
    private BigDecimal amount;
    private String description;
    @NotNull(message = "Tarih boş olamaz")
    private LocalDate date;
    @NotNull(message = "Kategori ID boş olamaz")
    private Long categoryId;
}