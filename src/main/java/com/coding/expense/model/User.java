package com.coding.expense.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Document(value = "user")
@Data  
@AllArgsConstructor
public class User {
	
	@Id
	Long id;
	
	String userName;
	
	String email;
	
	String password;

}
