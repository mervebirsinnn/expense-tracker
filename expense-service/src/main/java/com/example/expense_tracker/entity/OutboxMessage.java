package com.example.expense_tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="OutboxMessage")
@Getter
@Setter
public class OutboxMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventId;
    private String eventType;

    private String payload;
    private boolean processed = false;
    private LocalDateTime createdAt = LocalDateTime.now();
}
