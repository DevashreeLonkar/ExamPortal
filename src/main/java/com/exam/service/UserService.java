package com.exam.service;

import java.util.Set;

import com.exam.entity.User;
import com.exam.entity.UserRole;

public interface UserService {

	//creating User
	  User createUser(User user, Set<UserRole> userRoles) throws Exception;
		
	//get user by username
	  public User getUser(String username);
	  
	//delete user by id
	  public void deleteUser(Long userId);
}
