package com.example.expense_tracker.service;
import com.example.expense_tracker.config.RabbitMQConfig;
import com.example.expense_tracker.dto.ExpenseRequestDTO;
import com.example.expense_tracker.dto.ExpenseResponseDTO;
import com.example.expense_tracker.entity.Category;
import com.example.expense_tracker.entity.Expense;
import com.example.expense_tracker.entity.User;
import com.example.expense_tracker.mapper.ExpenseMapper;
import com.example.expense_tracker.repository.CategoryRepository;
import com.example.expense_tracker.repository.ExpenseRepository;
import com.example.expense_tracker.repository.UserRepository;
import com.example.expensetracker.common.event.ExpenseCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ExpenseMapper expenseMapper;
    private final RabbitTemplate rabbitTemplate;

    public ExpenseResponseDTO createExpense(String username, ExpenseRequestDTO requestDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı"));

        Expense expense = expenseMapper.toEntity(requestDTO);
        expense.setUser(user);
        expense.setCategory(category);

        Expense savedExpense = expenseRepository.save(expense);


        ExpenseCreatedEvent event = new ExpenseCreatedEvent(user.getId(), expense.getAmount());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXPENSE_QUEUE, event);

        return expenseMapper.toResponseDTO(savedExpense);
    }

    public List<ExpenseResponseDTO> getMyExpenses(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        return expenseRepository.findByUserId(user.getId()).stream()
                .map(expenseMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}