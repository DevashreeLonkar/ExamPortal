package com.exam.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.exam.service.impl.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		
//		final String requestTokenHeader= request.getHeader("Authorization");
//		System.out.println(requestTokenHeader);
//		String username= null;
//		String jwtToken= null;
//		
//		if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
//			
//			jwtToken= requestTokenHeader.substring(7);
//			
//			try {
//				
//				username= this.jwtUtil.extractUsername(jwtToken);
//			} 
//			catch (ExpiredJwtException e) {
//				
//				e.printStackTrace();
//				System.out.println("JWT Token has been expired");
//			}
//			catch (Exception e) {
//			
//				e.printStackTrace();
//				System.out.println("error");
//			}
//			
//		}
//		else {
//			System.out.println("Invalid Token, Not start with Bearer string");
//		}
//		
//		//Validate token
//		if(username !=null && SecurityContextHolder.getContext().getAuthentication()== null) {
//			final UserDetails userDetails= this.userDetailsService.loadUserByUsername(username);
//			if(this.jwtUtil.validateToken(jwtToken, userDetails))
//			{
//				UsernamePasswordAuthenticationToken usernamePasswordAuthentication= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//				
//				usernamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				
//				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);
//			}
//		}
//		else {
//			System.out.println("Token is Invalid");
//		}
//		
//		filterChain.doFilter(request, response);
//	}
	@Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(token);
            } catch (ExpiredJwtException e) {
                System.out.println("Token expired");
            } catch (Exception e) {
                System.out.println("Token error");
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
