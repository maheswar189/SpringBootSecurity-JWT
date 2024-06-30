package com.codedecode.microservice.apiGateway.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codedecode.microservice.apiGateway.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

	Optional<UserInfo> findByName(String username);

}
