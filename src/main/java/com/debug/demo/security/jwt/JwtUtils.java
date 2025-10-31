package com.debug.demo.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	private static final String SECRET_KEY= "askdfsdkadfkasdffwowoww@#423AAAA";
	private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	
	public String generateToken(String username, long expiryMinutes) {
		return Jwts
				.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiryMinutes * 60 * 1000))
				.signWith(key,SignatureAlgorithm.HS256)
				.compact();
	}

	public String validateAndExtractEmail(String token) {
		// TODO Auto-generated method stub
		try {
			return Jwts.parser()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody()	
			.getSubject();

		}catch(JwtException e) {
			return null;
		}
	}
}
