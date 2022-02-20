package com.coding.expense.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "user")
@Data  
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	Long id;
	
	String userName;
	
	String email;
	
	String password;
	
	public static User getUser(String userEmail) {
		User user = new User();
		user.setEmail(userEmail);
		
		return user;
	}

}
