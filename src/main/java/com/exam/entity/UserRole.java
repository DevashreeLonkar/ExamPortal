package com.exam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserRole {
	
	@Id
	private Long userRole;
	
	//user
	private User user;
}