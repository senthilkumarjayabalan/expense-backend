package com.coding.expense.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.coding.expense.model.Expense;
import com.coding.expense.model.User;
import com.coding.expense.repository.ExpenseRepository;
import com.coding.expense.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	public UserRepository userRepository;
	
	public User signUp(User user) {
		User response = userRepository.save(user);
		return response;
	}
	
	public User signIn(String email) {
		return userRepository.findByEmail(email);
	}

}
