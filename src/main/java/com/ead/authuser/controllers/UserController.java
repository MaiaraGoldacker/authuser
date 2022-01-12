package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.service.UserService;
import com.ead.authuser.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600) //permite acesso de todas as origens, pode também ser adcionada a nível de método caso haja alguma restrição
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(SpecificationTemplate.UserSpec spec,
                                                       @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable){
        Page<UserModel> userModelPage = userService.findAll(spec, pageable);
        
        if (!userModelPage.isEmpty()) {
        	for (UserModel user : userModelPage.toList()) {
        		user.add(linkTo(methodOn(UserController.class).getAUser(user.getUserId())).withSelfRel());
        	}
        }

        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }

	
	@GetMapping("/{userId}")
	public ResponseEntity<Object> getAUser(@PathVariable(value = "userId") UUID userId) {
		Optional<UserModel> userModelOptional = userService.findById(userId);
		
		if (!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get()); 
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {
		log.debug("DELETE deleteUser userId received {} ", userId);
		Optional<UserModel> userModelOptional = userService.findById(userId);
		
		if (!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found");
		}
		
		userService.delete(userModelOptional.get());
		log.debug("DELETE deleteUser userId {} ", userId);
		log.info("User deleted successfully User Id {}", userId);
		return ResponseEntity.status(HttpStatus.OK).body("User deleted succesfull");
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
											 @RequestBody 
											 @Validated(UserDto.UserView.UserPut.class)
											 @JsonView(UserDto.UserView.UserPut.class) UserDto userDto) {
		
		log.debug("PUT updateUser userDto received {} ", userDto.toString());
		Optional<UserModel> userModelOptional = userService.findById(userId);
		
		if (!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found");
		}
		
		var userModel = userModelOptional.get();
		userModel.setFullname(userDto.getFullname());
		userModel.setPhoneNumber(userDto.getPhoneNumber());
		userModel.setCpf(userDto.getCpf());
		userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
		
		userService.save(userModel);
		log.debug("PUT updateUser user Id saved {} ", userModel.getUserId());
		log.info("User updated successfully User Id {}", userModel.getUserId());
		return ResponseEntity.status(HttpStatus.OK).body(userModel);
	}
	
	@PutMapping("/{userId}/password")
	public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID userId,
												 @RequestBody 
												 @Validated(UserDto.UserView.PasswordPut.class)
												 @JsonView(UserDto.UserView.PasswordPut.class) UserDto userDto) {
		Optional<UserModel> userModelOptional = userService.findById(userId);
		
		if (!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found");
		}
		
		if (!userModelOptional.get().getPassword().equals(userDto.getOldPassword())) {
			log.warn("Mismatched old password", userDto.getUserId());
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Mismatched old password");
		}
		
		var userModel = userModelOptional.get();
		userModel.setPassword(userDto.getPassword());
		userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
		
		userService.save(userModel);
		return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully.");
	}
	
	@PutMapping("/{userId}/image")
	public ResponseEntity<Object> updateImage(@PathVariable(value = "userId") UUID userId,
			 								  @RequestBody 
			 								  @Validated(UserDto.UserView.ImagePut.class)
			 								  @JsonView(UserDto.UserView.ImagePut.class) UserDto userDto) {
		Optional<UserModel> userModelOptional = userService.findById(userId);
		
		if (!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found");
		}
		
		var userModel = userModelOptional.get();
		userModel.setImageUrl(userDto.getImageUrl());
		userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
		
		userService.save(userModel);
		return ResponseEntity.status(HttpStatus.OK).body(userModel);
	}
}
