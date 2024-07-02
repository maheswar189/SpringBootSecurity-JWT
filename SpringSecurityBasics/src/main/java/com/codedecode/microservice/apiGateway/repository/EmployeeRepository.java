package com.codedecode.microservice.apiGateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codedecode.microservice.apiGateway.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	
}
