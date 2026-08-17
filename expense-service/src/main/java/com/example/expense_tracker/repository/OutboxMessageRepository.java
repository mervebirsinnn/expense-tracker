package com.example.expense_tracker.repository;

import com.example.expense_tracker.entity.OutboxMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxMessageRepository extends JpaRepository<OutboxMessage, Long> {
    //Henüz işlenmemiş (processed=false) mesajları listeleyen metot
    List<OutboxMessage> findByProcessedFalse();
    Slice<OutboxMessage> findByProcessedFalseOrderByCreatedAtAsc(Pageable pageable);

}
