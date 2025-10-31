package com.debug.demo.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.debug.demo.security.authentication.JwtAuthenticationToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtValidationFilter  extends OncePerRequestFilter{
	private AuthenticationManager authenticationManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request
			, HttpServletResponse response
			, FilterChain filterChain)
			throws ServletException, IOException {
		String token = getTokenFromHeader(request);
		if(token != null) {
			JwtAuthenticationToken authToken = new JwtAuthenticationToken(token);
			Authentication auth = authenticationManager.authenticate(authToken);
			if(auth.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(auth);
				
			}
			filterChain.doFilter(request, response);
		}
		
		
		
	}

	private String getTokenFromHeader(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String authorizationHeader = request.getHeader("Authorization");
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
			return authorizationHeader.substring(7);
		}
		return null;
	}
	

}
