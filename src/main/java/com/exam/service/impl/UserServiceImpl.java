package com.exam.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exam.entity.UserRole;
import com.exam.entity.Users;
import com.exam.repository.RoleRepository;
import com.exam.repository.UserRepository;
import com.exam.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//creating user
	@Override
	public Users createUser(Users user, Set<UserRole> userRoles) throws Exception {
		
		Users local= this.userRepository.findByUsername(user.getUsername());
		if(local !=null) {
			System.out.println("User already there !!");
			throw new Exception("User already present !!");
		}
		else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			//create user
			for(UserRole ur: userRoles) {
				roleRepository.save(ur.getRole());
			}
			user.getUserRoles().addAll(userRoles);
			local= this.userRepository.save(user);
		}
		return local;
	}

	//getting user by username
	@Override
	public Users getUser(String username) {
		return this.userRepository.findByUsername(username);
	}
	
	//deleting user by id
	@Override
	public void deleteUser(Long userId) {
		
		 this.userRepository.deleteById(userId);
	}

}
