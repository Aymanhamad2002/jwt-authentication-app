package com.debug.demo.security.authentication;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

@Getter
public class JwtAuthenticationToken  extends AbstractAuthenticationToken{
	private String token ;
	

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return token;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	public JwtAuthenticationToken( String token) {
		super(null);
		this.token = token;
		setAuthenticated(false);
	}

}
