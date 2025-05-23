package com.exam.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.entity.Role;
import com.exam.entity.UserRole;
import com.exam.entity.Users;
import com.exam.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;
	
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/")
	public Users createUser(@RequestBody Users user) throws Exception {
		
		Set<UserRole> roles= new HashSet<>();
		
		Role role= new Role();
		role.setRoleId(45L);
		role.setRoleName("NORMAL");
		
		UserRole userRole= new UserRole();
		userRole.setRole(role);
		userRole.setUser(user);
		
		roles.add(userRole);
		
		
		return this.userService.createUser(user, roles);
		
	}
	
	@GetMapping("/{username}")
	public Users getUser(@PathVariable("username") String username) {
		return this.userService.getUser(username);
	}
	
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable("userId") Long userId) {
		this.userService.deleteUser(userId);
	}
	
	// ✅ NEW: get current user by token
		@GetMapping("/current-user")
		public Users getCurrentUser(Principal principal) {
			return this.userService.getUser(principal.getName());
		}
}
