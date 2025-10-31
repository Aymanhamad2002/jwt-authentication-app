package com.debug.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.debug.demo.DTO.UserRequestDto;
import com.debug.demo.service.IUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private final IUserService userService;
	
	
	@PostMapping("/register")
	private ResponseEntity<?> register(@RequestBody UserRequestDto registerRequest){
		userService.createUser(registerRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping("/hello")
	private String test(Authentication auth) {
		return "Hello " + auth.getName();
		
	}

}
