package com.sanskar.messagingapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping("/")
	public ResponseEntity<String> homeGet(){
		return new ResponseEntity<>("Welcome to our api", HttpStatus.OK);
	}

}
