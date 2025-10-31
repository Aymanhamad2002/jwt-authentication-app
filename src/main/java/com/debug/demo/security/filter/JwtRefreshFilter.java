package com.debug.demo.security.filter;

import java.io.IOException;
import java.net.CookieHandler;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.filter.OncePerRequestFilter;

import com.debug.demo.security.authentication.JwtAuthenticationToken;
import com.debug.demo.security.jwt.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtRefreshFilter extends OncePerRequestFilter{
	private AuthenticationManager authenticationManager;
	private JwtUtils jwtUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request
			, HttpServletResponse response
			,FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(!request.getServletPath().equals("/refresh-token")) {
			filterChain.doFilter(request, response);
			return;
		}
		String token = getTokenFromCookie(request);
		if(token == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return ; 
		}
		JwtAuthenticationToken authToken = new JwtAuthenticationToken(token);
		Authentication auth = authenticationManager.authenticate(authToken);
		if(auth.isAuthenticated()) {
			String newToken = jwtUtils.generateToken(auth.getName(), 15);
			response.setHeader("Authorization", "Bearer " + token);
			return ;
		}
		
	}

	private String getTokenFromCookie(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Cookie []cookies = request.getCookies();
		if(cookies == null) {
			return null;
		}
		for(Cookie c : cookies) {
			if(c.getName().equals("refreshToken")) {
				return c.getValue();
			}
		}
		return null;
		
	}

}
