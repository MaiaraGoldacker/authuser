package com.ead.authuser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ead.authuser.repositories.UserCourseRepository;
import com.ead.authuser.service.UserCourseService;

@Service
public class UserCourseServiceImpl implements UserCourseService {

	@Autowired
	UserCourseRepository userCourseRepository; 
}
