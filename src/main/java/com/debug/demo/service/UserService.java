	package com.debug.demo.service;
	
	import org.springframework.security.core.userdetails.UserDetails;
	import org.springframework.security.core.userdetails.UserDetailsService;
	import org.springframework.security.core.userdetails.UsernameNotFoundException;
	import org.springframework.security.crypto.password.PasswordEncoder;
	import org.springframework.stereotype.Service;
	
	import com.debug.demo.DTO.UserRequestDto;
	import com.debug.demo.enums.Role;
	import com.debug.demo.model.User;
	import com.debug.demo.repository.UserRepository;
	import com.debug.demo.security.user.SecurityUser;
	
	import lombok.RequiredArgsConstructor;
	
	@Service
	@RequiredArgsConstructor
	public class UserService implements IUserService ,UserDetailsService{
		
		private final PasswordEncoder passwordEncoder;
		private final UserRepository userRepository;
	
		@Override
		public void createUser(UserRequestDto registerRequest) {
			User newUser = User.builder()
					.username(registerRequest.getUsername())
					.email(registerRequest.getEmail())
					.password(passwordEncoder.encode(registerRequest.getPassword()))
					.role(Role.USER)
					.build();
			userRepository.save(newUser);
			
		}
	
		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			// TODO Auto-generated method stub
			User user = userRepository.findByEmail(username)
						.orElseThrow(() -> new UsernameNotFoundException(username));
			return new SecurityUser(user);
		}
	
	}
