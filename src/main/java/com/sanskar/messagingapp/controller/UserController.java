package com.sanskar.messagingapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.sanskar.messagingapp.exception.UserException;
import com.sanskar.messagingapp.model.User;
import com.sanskar.messagingapp.request.UpdateUserRequest;
import com.sanskar.messagingapp.response.ApiResponse;
import com.sanskar.messagingapp.service.UserService;

@RestController("/api/user")
public class UserController {

	public UserService userService;
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}


	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException{
		
		User user = userService.findUserProfile(token);
		
		return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
			
		
	}
	
	@GetMapping("/{query}")
	public ResponseEntity<List<User>> searchUserHandler(@PathVariable("query") String query){
		List<User> users = userService.searchUser(query);
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserRequest req,@RequestHeader("Authorization") String token ) throws UserException{
		User user = userService.findUserProfile(token);
		userService.updateUser(user.getId(), req);
		ApiResponse response = new ApiResponse("user updated successfully", true);
		return new ResponseEntity<ApiResponse>(response,HttpStatus.ACCEPTED);
		
	}
}
