package com.codedecode.microservice.apiGateway.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.codedecode.microservice.apiGateway.entity.UserInfo;
import com.codedecode.microservice.apiGateway.repository.UserInfoRepository;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

		Optional<UserInfo> user = userInfoRepository.findByName(username);
		return user.map(UserInfoToUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found exception..."));

		// UserInfoToUserDetails UserInfoToUserDetails =new
		// UserInfoToUserDetails(user.get());
		// return UserInfoToUserDetails;
	}

}
