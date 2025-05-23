package com.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.exam.entity.Users;
import com.exam.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		
//		User user= userRepository.findByUsername(username);
//		if(user ==  null) {
//			System.out.println("User not found !!");
//			throw new UsernameNotFoundException("No User found");
//		}
//		
//		return user;
//	}

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found");

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities()) // uses your custom getAuthorities()
                .accountLocked(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .disabled(!user.isEnabled())
                .build();
    }
}
