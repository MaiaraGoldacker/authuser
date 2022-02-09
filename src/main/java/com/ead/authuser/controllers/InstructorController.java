package com.ead.authuser.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ead.authuser.dtos.InstructorDto;
import com.ead.authuser.enums.RoleType;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.RoleModel;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.service.RoleService;
import com.ead.authuser.service.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/instructors")
public class InstructorController {
	
	@Autowired
	UserService userService;
	
    @Autowired
    RoleService roleService;
	
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/subscription")
	public ResponseEntity<Object> savesubscriptionInstructor(@RequestBody @Valid InstructorDto instructorDto) {
		
		Optional<UserModel> userModelOptional = userService.findById(instructorDto.getUserId());
		
		if (!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found");
		} else {
			RoleModel roleModel = roleService.findByRoleName(RoleType.ROLE_INSTRUCTOR)
	                    .orElseThrow(() -> new RuntimeException("Error: Role is Not Found."));
	        var userModel = userModelOptional.get();
	        userModel.setUserType(UserType.INSTRUCTOR);
	        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
	        userModel.getRoles().add(roleModel);
	        userService.updateUser(userModel);
	        return ResponseEntity.status(HttpStatus.OK).body(userModel);
		}
	}

}
