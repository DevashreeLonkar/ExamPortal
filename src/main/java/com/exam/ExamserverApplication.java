package com.exam;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.exam.entity.Role;
import com.exam.entity.UserRole;
import com.exam.entity.Users;
import com.exam.helper.UserFoundException;
import com.exam.service.UserService;

@SpringBootApplication
public class ExamserverApplication implements CommandLineRunner{

	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(ExamserverApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		try {
		System.out.println("Starting Code");
//		
//		Users user= new Users();
//		
//		user.setFirstName("Devashree");
//		user.setLastName("Lonkar");
//		user.setUsername("devashree07");
//		user.setPassword("abc");
//		user.setEmail("abc@gmail.com");
//		
//		Role role1= new Role();
//		role1.setRoleId(44L);
//		role1.setRoleName("ADMIN");
//		
//		Set<UserRole> userRoleSet= new HashSet<>();
//		UserRole userRole= new UserRole();
//		userRole.setRole(role1);
//		userRole.setUser(user);
//		
//		userRoleSet.add(userRole);
//		
//		Users user1= this.userService.createUser(user, userRoleSet);
//		System.out.println(user1.getUsername());
//	}
//	catch(UserFoundException e){
//		e.printStackTrace();
//	 }
	}
}
