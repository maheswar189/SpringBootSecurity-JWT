package com.codedecode.microservice.apiGateway.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codedecode.microservice.apiGateway.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

	Optional<UserInfo> findByName(String username);

	// Custom query for findAll
	@Query("SELECT u FROM UserInfo u")
	List<UserInfo> customFindAll();
}
