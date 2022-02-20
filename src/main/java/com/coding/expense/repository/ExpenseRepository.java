package com.coding.expense.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.coding.expense.model.Expense;

public interface ExpenseRepository extends MongoRepository<Expense, Long> {
	
	public void deleteById(Long id);
	
	public List<Expense> findByUserEmail(String email);

}