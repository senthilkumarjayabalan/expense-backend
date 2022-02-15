package com.coding.expense.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coding.expense.model.User;
import com.coding.expense.service.UserService;

@RestController
@RequestMapping(value = "api/v1/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	
	@Autowired
	public UserService userService;
	
	@PostMapping(value = "/signup")
	public ResponseEntity<User> signUp(@RequestBody User user) {
		User response = userService.signUp(user);
		System.out.println(response);
		if (response != null) {
			return ResponseEntity.ok().body(response);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	@PostMapping(value = "/signin")
	public ResponseEntity<User>  signIn(@RequestBody User user) {
		User response =  userService.signIn(user.getEmail());
		if (response != null) {
			return ResponseEntity.ok().body(response);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

}
