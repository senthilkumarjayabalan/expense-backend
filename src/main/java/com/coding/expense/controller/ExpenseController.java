package com.coding.expense.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coding.expense.model.Expense;
import com.coding.expense.service.ExpenseService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000"  ,exposedHeaders = "Authorization" ,allowedHeaders = "Authorization" )
@RequestMapping("api/v1/expense" )
public class ExpenseController {
	
	@Autowired
	public ExpenseService expenseService;
	
	@PostMapping
	public void addExpense(@RequestBody Expense expense) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		expense.setUserEmail(((User) authentication.getPrincipal()).getUsername());
		expenseService.addExpense(expense);
	}
	
	@GetMapping
	public List<Expense> getExpenses() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return expenseService.getExpensesByUserEmail(((User) authentication.getPrincipal()).getUsername());
	}

	@DeleteMapping(path = "/{id}")
	public void deleteExpense(@PathVariable(name="id") Long id) {
	 expenseService.deteteExpense(id);
	}
	
}
