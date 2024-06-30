package com.codedecode.microservice.apiGateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codedecode.microservice.apiGateway.entity.UserInfo;
import com.codedecode.microservice.apiGateway.repository.UserInfoRepository;

@Service
public class UserService {

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserInfo saveUser( UserInfo user)
	{
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		UserInfo save = userInfoRepository.save(user);
	
		return save;
	}
}
