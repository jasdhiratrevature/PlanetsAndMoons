package com.revature.service;

import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.repository.UserDao;

public class UserService {

	private UserDao dao;

	public UserService(UserDao dao){
		this.dao = dao;
	}

	public User authenticate(UsernamePasswordAuthentication loginRequestData) {
		// TODO: implement
		// return dao.getUserByUsername(loginRequestData.getUsername());
		User user = dao.getUserByUsername(loginRequestData.getUsername());
    
		// Check if the user exists and the provided password matches
		if (user != null && user.getPassword().equals(loginRequestData.getPassword())) {
			return user;
		}
		
		// If user doesn't exist or password doesn't match, return null
		return null;
	}

	public User register(User registerRequestData) {
		// check that username and password are within acceptable length
		if (registerRequestData.getUsername().length() <= 30 && registerRequestData.getPassword().length() <= 30){
			// if the lengths are acceptable check that the username is unique
			if(dao.getUserByUsername(registerRequestData.getUsername()) == null){
				// if the username is unique go ahead and actually create the new user
				UsernamePasswordAuthentication validUserData = new UsernamePasswordAuthentication();
				validUserData.setUsername(registerRequestData.getUsername());
				validUserData.setPassword(registerRequestData.getPassword());
				// make sure to return the new user's data
				return dao.createUser(validUserData);
			}
		}
		// if the user data does not meet business or software requirements return a null object
		return null;
	}
}
