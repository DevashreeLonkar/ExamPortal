package com.exam.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.config.JwtUtil;
import com.exam.entity.JwtRequest;
import com.exam.entity.JwtResponse;
import com.exam.model.AuthRequest;
import com.exam.model.AuthResponse;
import com.exam.service.impl.UserDetailsServiceImpl;

//import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@CrossOrigin("*")
public class AuthenticateController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	// Generate Token

//	@PostMapping
//	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
//		try {
//	
//			authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
//	
//		} catch (UsernameNotFoundException e) {
//			e.printStackTrace();
//			throw new Exception("User not found");
//	
//		}
//
//		// UserDetails userDetails=
//		// this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
//		UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
//
//		String token = jwtUtil.generateToken(userDetails);
//		return ResponseEntity.ok(new JwtResponse(token));
//
//	}
//
//	private void authenticate(String username, String password) throws Exception {
//
//		try {
//			authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//
//		} catch (DisabledException e) {
//			System.out.println("User is Disabled" + e.getMessage());
//		}
//
//		catch (BadCredentialsException e) {
//			throw new Exception("Invalid Credentials" + e.getMessage());
//		}
//	}
	 @PostMapping("/generate-token")
	    public ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) {
	        try {
	            Authentication authentication = authManager.authenticate(
	                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
	        } catch (BadCredentialsException e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	        }

	        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
	        String token = jwtUtil.generateToken(userDetails);
	        return ResponseEntity.ok(new AuthResponse(token));
	    }
	 
	 //returns details of current user
	 @GetMapping("/current-user")
	 public User getCurrentUser(Principal principal) {
		 return ((User) this.userDetailsService.loadUserByUsername(principal.getName()));
		 
	 }
}
