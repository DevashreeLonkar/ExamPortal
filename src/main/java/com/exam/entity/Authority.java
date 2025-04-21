package com.exam.entity;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Authority implements GrantedAuthority{
	
	private String authority;
	

	@Override
	public String getAuthority() {
		
		return this.authority;
	}

}
