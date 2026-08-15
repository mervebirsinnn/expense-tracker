package com.example.expense_tracker.mapper;

import com.example.expense_tracker.dto.ExpenseRequestDTO;
import com.example.expense_tracker.dto.ExpenseResponseDTO;
import com.example.expense_tracker.entity.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    @Mapping(target = "categoryName", source = "category.name")
    ExpenseResponseDTO toResponseDTO(Expense expense);

    @Mapping(target = "category", ignore = true) // Serviste manuel set edeceğim
    @Mapping(target = "user", ignore = true)     // Serviste manuel set et
    Expense toEntity(ExpenseRequestDTO requestDTO);
}