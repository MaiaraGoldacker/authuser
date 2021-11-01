package com.ead.authuser.dtos;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

	private UUID userId;
	
	private String username;
	
	private String email;

	private String password;
	
	private String oldPassword;

	private String fullname;

	private String phoneNumber;

	private String cpf;

	private String imageUrl;
	
}
