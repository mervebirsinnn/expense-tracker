package com.example.budget_service.repository;

import com.example.budget_service.Entity.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, String> {
}
