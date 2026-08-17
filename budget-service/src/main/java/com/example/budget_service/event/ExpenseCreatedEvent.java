package com.example.budget_service.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ExpenseCreatedEvent {
    private String eventId;
    private Long userId;
    private BigDecimal amount;
    private LocalDateTime date;

    @JsonCreator
    public ExpenseCreatedEvent(
            @JsonProperty("eventId") String eventId,
            @JsonProperty("userId") Long userId,
            @JsonProperty("amount") BigDecimal amount,
            @JsonProperty("date") LocalDateTime date) {
        this.eventId = eventId;
        this.userId = userId;
        this.amount = amount;
        this.date = date;
    }

    public ExpenseCreatedEvent() {
    }
}