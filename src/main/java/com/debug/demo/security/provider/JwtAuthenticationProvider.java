package com.debug.demo.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.debug.demo.security.authentication.JwtAuthenticationToken;
import com.debug.demo.security.jwt.JwtUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
	private final JwtUtils jwtUtils;
	private  final UserDetailsService uds;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String token = ((JwtAuthenticationToken) authentication).getToken();
		String email = jwtUtils.validateAndExtractEmail(token);
		if(email == null) {
			throw new BadCredentialsException("Invalid Jwt Token");
		}
		UserDetails user = uds.loadUserByUsername(email);
		return new UsernamePasswordAuthenticationToken(email,null,user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
