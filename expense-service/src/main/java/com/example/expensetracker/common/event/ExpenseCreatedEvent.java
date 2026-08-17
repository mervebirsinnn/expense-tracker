package com.example.expensetracker.common.event;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter

public class ExpenseCreatedEvent {
    private String eventId;
    private Long userId;
    private BigDecimal amount;
    private LocalDateTime date;

    public ExpenseCreatedEvent(){

    }
    public ExpenseCreatedEvent(String eventId, Long userId, BigDecimal amount){
        this.eventId= eventId;

    }
}



