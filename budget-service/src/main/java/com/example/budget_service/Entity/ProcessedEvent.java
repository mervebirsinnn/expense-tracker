package com.example.budget_service.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="processed_event")
@Getter
@Setter
public class ProcessedEvent {
    @Id
    private String eventId;
    private LocalDateTime processedAt = LocalDateTime.now();



}
