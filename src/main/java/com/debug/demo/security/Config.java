package com.debug.demo.security;

import java.util.List;

import javax.swing.JPasswordField;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.debug.demo.security.filter.JwtAuthenticationFilter;
import com.debug.demo.security.filter.JwtRefreshFilter;
import com.debug.demo.security.filter.JwtValidationFilter;
import com.debug.demo.security.jwt.JwtUtils;
import com.debug.demo.security.provider.JwtAuthenticationProvider;

import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Config {
	private final JwtUtils jwtUtils;
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	
	


	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider =new  DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
		
	}
	@Bean JwtAuthenticationProvider jwtAuthenticationProvider() {
		JwtAuthenticationProvider provider = new JwtAuthenticationProvider(jwtUtils, userDetailsService);
		return provider;
	}
	@Bean
	AuthenticationManager authenticationManager() {
		return new ProviderManager(List.of(daoAuthenticationProvider(),jwtAuthenticationProvider()));
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(), jwtUtils);
		JwtValidationFilter jwtValidationFilter = new JwtValidationFilter(authenticationManager());
		JwtRefreshFilter jwtRefreshFilter = new JwtRefreshFilter(authenticationManager(),jwtUtils);
		http.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(request-> request.requestMatchers("/hello").authenticated().anyRequest().permitAll())
		.sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
		http.addFilterAfter(jwtValidationFilter, JwtAuthenticationFilter.class);
		http.addFilterBefore(jwtRefreshFilter, JwtValidationFilter.class);
		return http.build();
	}

}
