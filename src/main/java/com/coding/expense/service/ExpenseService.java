package com.coding.expense.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coding.expense.model.Expense;
import com.coding.expense.repository.ExpenseRepository;

@Service
public class ExpenseService {
	
	@Autowired
	public ExpenseRepository expenseRepository;
	
	public void addExpense(Expense expense) {
		expense.setExpenseDate(new Date());
		expenseRepository.save(expense);
	}
	
	public List<Expense> getExpenses(){
		return expenseRepository.findAll();
	}
	
	public void deteteExpense(Long id) {
		 expenseRepository.deleteById(id);
	}

}
