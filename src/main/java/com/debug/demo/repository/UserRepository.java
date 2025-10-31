package com.debug.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.debug.demo.model.User;

public interface UserRepository extends JpaRepository<User,Long> {

	Optional<User> findByEmail(String email);

}
