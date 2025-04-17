package com.exam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "roles")
public class Role {

	@Id
	private Long roleId;
	private String roleName;
	
	//This is role entity
}
