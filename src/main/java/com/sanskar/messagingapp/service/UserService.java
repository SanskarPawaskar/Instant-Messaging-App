package com.sanskar.messagingapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sanskar.messagingapp.exception.UserException;
import com.sanskar.messagingapp.model.User;
import com.sanskar.messagingapp.request.UpdateUserRequest;

@Service
public interface UserService {

	public User findUserById(Integer id) throws UserException;
	
	public User findUserProfile(String jwt) throws UserException;
	
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException;
	
	List<User> searchUser(String query);
	
}
