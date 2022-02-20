package com.coding.expense.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coding.expense.configuration.WebSecurityConfig;
import com.coding.expense.model.User;
import com.coding.expense.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "api/v1/user")
//@CrossOrigin(origins = "http://localhost:3000" ,exposedHeaders = "Authorization" ,allowedHeaders = "Authorization")
@Slf4j
public class UserController {

	public static final String SECRET_KEY = "secret key";

	@Autowired
	public UserService userService;

	@Autowired
	public AuthenticationManager authenticationManager;

	@PostMapping(value = "/signup")
	public ResponseEntity<User> signUp(@RequestBody User user) {
		User response = userService.signUp(user);
		System.out.println(response);
		if (response != null) {

			String token = Jwts.builder().setSubject(response.getEmail())
					.setExpiration(new Date(System.currentTimeMillis() + 100 * 60 * 1000))
					.signWith(SignatureAlgorithm.HS512, UserController.SECRET_KEY).claim("role", "admin").compact();

			return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token)
					.body(com.coding.expense.model.User.getUser(response.getEmail()));

		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@PostMapping(value = "/signin")
	public ResponseEntity<User> signIn(@RequestBody User user) {
		log.info("Sign In called ....");

		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

			String token = Jwts.builder()
					.setSubject(((org.springframework.security.core.userdetails.User) authentication.getPrincipal())
							.getUsername())
					.setExpiration(new Date(System.currentTimeMillis() + 100 * 60 * 1000))
					.signWith(SignatureAlgorithm.HS512, UserController.SECRET_KEY).claim("role", "admin").compact();

			return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token)
					.body(com.coding.expense.model.User.getUser(
							((org.springframework.security.core.userdetails.User) authentication.getPrincipal())
									.getUsername()));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

	}

}
