package com.exam.service;

import java.util.Set;
import com.exam.entity.UserRole;
import com.exam.entity.Users;

public interface UserService {

	//creating User
	  Users createUser(Users user, Set<UserRole> userRoles) throws Exception;
		
	//get user by username
	  public Users getUser(String username);
	  
	//delete user by id
	  public void deleteUser(Long userId);
}
