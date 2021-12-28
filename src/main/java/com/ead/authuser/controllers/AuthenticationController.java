package com.ead.authuser.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.extern.log4j.Log4j2;

@Log4j2 //anotação para uso do log4j2, para não precisar instanciar em cada classe Logger logger = LogManager.getLogger(AuthenticationController.class);
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {
	
	//Logger logger = LoggerFactory.getLogger(AuthenticationController.class); //Logback padrão do Spring. Conseguimos dividir o log por tipos com ele, como logger.info(""),
																			   //logger.error("") entre outros. O Legal é que a cor de cada log sai diferente no console
																			   //e isso ajuda a encontrar Bugs. Mas o Log4j 2 é mais completo, por isso não vamos utilizar
	                                                                           //esse exemplo.

	@Autowired
	UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<Object> registerUser(@RequestBody 
											   @Validated(UserDto.UserView.RegistrationPost.class)
											   @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto){
		log.debug("POST registerUser userDto received {} ", userDto.toString()); //troca {} pelo objeto.ToString()
		if (userService.existsByUsername(userDto.getUsername())) {
			log.warn("Username {} is Already Taken", userDto.getUsername()); 
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is already taken.");	
		}
		
		if (userService.existsByEmail(userDto.getEmail())) {
			log.warn("Email {} is Already Taken", userDto.getEmail());
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is already taken.");	
		}
		
		var userModel = new UserModel();
		BeanUtils.copyProperties(userDto, userModel);
		userModel.setUserStatus(UserStatus.ACTIVE);
		userModel.setUserType(UserType.STUDENT);
		userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
		userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

		userService.save(userModel);
		log.debug("POST registerUser userId received {} ", userModel.getUserId());
		log.info("User saved successfully UserId {}", userModel.getUserId());
		return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
	}

}
