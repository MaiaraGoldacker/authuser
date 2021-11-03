package com.ead.authuser.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ead.authuser.validation.UsernameConstraint;

public class UsernameConstraintImpl implements ConstraintValidator<UsernameConstraint, String>{

	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		if (username == null || username.trim().isEmpty() || username.contains(" ")) {
			return false;
		}
		return true;
	}

}
