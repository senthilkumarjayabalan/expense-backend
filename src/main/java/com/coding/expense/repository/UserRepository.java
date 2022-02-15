package com.coding.expense.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.coding.expense.model.Expense;
import com.coding.expense.model.User;

public interface UserRepository extends MongoRepository<User, Long> {
	
	User findByEmail(String email);
}
