package com.ead.authuser.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ead.authuser.models.UserModel;

public interface UserService {

	List<UserModel> findAll();
	
	Optional<UserModel> findById(UUID userId);
	
	void delete(UserModel userModel);
	
	void save(UserModel userModel);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
}
