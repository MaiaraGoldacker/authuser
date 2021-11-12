package com.ead.authuser.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ead.authuser.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, UUID>, 
										JpaSpecificationExecutor<UserModel> {
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);

}
