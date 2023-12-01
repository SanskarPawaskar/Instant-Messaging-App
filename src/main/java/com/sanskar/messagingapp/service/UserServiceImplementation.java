package com.sanskar.messagingapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sanskar.messagingapp.config.TokenProvider;
import com.sanskar.messagingapp.exception.UserException;
import com.sanskar.messagingapp.model.User;
import com.sanskar.messagingapp.repository.UserRepository;
import com.sanskar.messagingapp.request.UpdateUserRequest;

@Service
public class UserServiceImplementation implements UserService{
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public TokenProvider tokenProvider;
	

	@Override
	public User findUserById(Integer id) throws UserException {
		Optional<User> user = userRepository.findById(id);
		if(user != null) {
			return user.get();
		}
		throw new UserException("User not found with id "+id);
	}

	@Override
	public User findUserProfile(String jwt) throws UserException {
		String email = tokenProvider.getEmailFromToken(jwt);
		if(email==null) {
			throw new BadCredentialsException("received invalid token");
		}
		User user = userRepository.findByEmail(email);
		if(user == null) {
			throw new UserException("user not found with email: "+email);
		}
		return user;
	}

	@Override
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
		User user = findUserById(userId);
		if(user.getFull_name()!=null) {
			user.setFull_name(req.getFull_name());
		}
		if(user.getProfile_picture()!=null) {
			user.setProfile_picture(req.getProfile_picture());
		}
		return userRepository.save(user);
	}

	@Override
	public List<User> searchUser(String query) {
		List<User> users = userRepository.searchUser(query);
		return users;
	}

}
