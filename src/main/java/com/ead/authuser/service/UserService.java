package com.ead.authuser.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.ead.authuser.models.UserModel;

public interface UserService {

	List<UserModel> findAll();
	
	Optional<UserModel> findById(UUID userId);
	
	void delete(UserModel userModel);
	
	UserModel save(UserModel userModel);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	
	Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);
	
	 UserModel saveUser(UserModel userModel);
	 
	 void deleteUser(UserModel userModel);
	 
	 UserModel updateUser(UserModel userModel);
	 
	 UserModel updatePassword(UserModel userModel);
	 
}
