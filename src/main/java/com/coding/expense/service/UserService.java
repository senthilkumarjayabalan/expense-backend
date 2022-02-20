package com.coding.expense.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.coding.expense.model.Expense;
import com.coding.expense.model.User;
import com.coding.expense.repository.ExpenseRepository;
import com.coding.expense.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	public UserRepository userRepository;
	
	public User signUp(User user) {
		String encodePassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(encodePassword);
		User response = userRepository.save(user);
		return response;
	}
	
	public User signIn(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User  user = userRepository.findByEmail(email);
		if(user == null) {
			System.out.println("User Not found");
			throw new UsernameNotFoundException("User Not found");
		}else {
			System.out.println("User found");
		}
		
		List<SimpleGrantedAuthority> authorities  = new ArrayList<SimpleGrantedAuthority>();
		SimpleGrantedAuthority admin =  new SimpleGrantedAuthority("admin");
		authorities.add(admin);
		
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

}
