package com.coding.expense.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(value = "expense")
public class Expense {
	
	@Id
	private Long id;
	
	private Date expenseDate=new Date();
	
	private String text;
	
	private Long amount;
	
}
