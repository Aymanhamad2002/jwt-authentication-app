package com.debug.demo.security.filter;

import java.io.IOException;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import com.debug.demo.DTO.LoginRequestDto;
import com.debug.demo.security.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private AuthenticationManager authenticationManager;
	private JwtUtils jwtUtils;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request
			, HttpServletResponse response
			, FilterChain filterChain)
			throws ServletException, IOException {
		if(!request.getServletPath().equals("/login")) {
			filterChain.doFilter(request, response);
			return ;
			
		}
		ObjectMapper mapper = new ObjectMapper();
		LoginRequestDto loginRequest = mapper.readValue(request.getInputStream(), LoginRequestDto.class);
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
															loginRequest.getPassword());
		Authentication auth  = authenticationManager.authenticate(authToken);
		if(auth.isAuthenticated()) {
			
			String token = jwtUtils.generateToken(auth.getName(),15);
			response.setHeader("Authorization", "Bearer " + token);
			String refreshToken = jwtUtils.generateToken(auth.getName(), 7*24*60);
			Cookie refreshCookie = new Cookie("refreshToken",refreshToken);
			refreshCookie.setHttpOnly(true);
			refreshCookie.setSecure(true);
			refreshCookie.setPath("/refresh-token");
			refreshCookie.setMaxAge(7*24*60*60);
			response.addCookie(refreshCookie);
			
		}
		
		
	}

}
