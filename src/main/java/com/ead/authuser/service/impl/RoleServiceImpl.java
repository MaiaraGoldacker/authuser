package com.ead.authuser.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ead.authuser.enums.RoleType;
import com.ead.authuser.models.RoleModel;
import com.ead.authuser.repositories.RoleRepository;
import com.ead.authuser.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Optional<RoleModel> findByRoleName(RoleType name) {		
		return roleRepository.findByRoleName(name);
	}
	
	
}
