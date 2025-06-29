package ru.hogwarts.school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.Expense;
import ru.hogwarts.school.ExpensesByCategory;
import ru.hogwarts.school.repository.ExpenseRepository;

import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public void printExpensesByCategory() {
        List<ExpensesByCategory> result = expenseRepository.getExpensesByCategory();

        for (ExpensesByCategory item : result) {
            System.out.println("Category: " + item.getCategory()
                    + ", Amount: " + item.getAmount());
        }
    }
    public Page<Expense> getExpensesPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return expenseRepository.findAll(pageable);
    }
}
