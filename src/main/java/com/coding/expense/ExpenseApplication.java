package com.coding.expense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.coding.expense.repository.ExpenseRepository;

@SpringBootApplication
@ComponentScan(basePackages = {"com.coding.expense.controller","com.coding.expense.model","com.coding.expense.repository","com.coding.expense.service"})
@EnableMongoRepositories(basePackageClasses = ExpenseRepository.class)
public class ExpenseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseApplication.class, args);
	}

}
