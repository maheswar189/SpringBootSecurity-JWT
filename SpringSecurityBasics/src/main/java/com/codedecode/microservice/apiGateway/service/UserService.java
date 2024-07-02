package com.codedecode.microservice.apiGateway.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codedecode.microservice.apiGateway.entity.Employee;
import com.codedecode.microservice.apiGateway.entity.UserInfo;
import com.codedecode.microservice.apiGateway.repository.EmployeeRepository;
import com.codedecode.microservice.apiGateway.repository.UserInfoRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public UserInfo saveUser(UserInfo user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		UserInfo save = userInfoRepository.save(user);

		return save;
	}
	
	@Cacheable("userList")
	public List<UserInfo> getUserDetails() {
		
		return userInfoRepository.customFindAll();
	}
	
	 @CacheEvict(value = "empList", allEntries = true)
	@CachePut(cacheNames = "empList", key = "#employee.empId")
	public Employee saveEmployee(Employee employee) {
		System.out.println("Calling save employee::"+employee);
		Employee save = employeeRepository.save(employee);
		return save;
	}
	
	 @Transactional
	@CacheEvict(value = "empList", allEntries = true)
	@CachePut(cacheNames = "empList", key = "#employee.empId")
	public Employee updateEmployee(Employee employee) {
		System.out.println("Calling update employee::"+employee);
		
		Optional<Employee> optionalEmployeeInfo = employeeRepository.findById(employee.getEmpId());

        if (optionalEmployeeInfo.isPresent()) {
        	Employee existingUserInfo = optionalEmployeeInfo.get();
            existingUserInfo.setName(employee.getName());
            existingUserInfo.setDesignation(employee.getDesignation());
            existingUserInfo.setLocation(employee.getLocation());
            // set other fields as needed

            return employeeRepository.save(existingUserInfo);
        } else {
            throw new RuntimeException("User not found with id: " + employee.getEmpId());
        }
	}
	

	@CacheEvict(value = "empList", allEntries = true)
	public void deleteEmployee(Integer empid) {
		System.out.println("Calling delete employee ID:"+empid);
		employeeRepository.deleteById(empid);
	}
	
	@Cacheable("empList")
	public List<Employee> getEmployeeList() {
		System.out.println("Calling  employee List");
		return employeeRepository.findAll();
	}
	
	 @Cacheable(cacheNames = "empList", key = "#empId")
	public Employee fetchEmployeeById(Integer empId) {
		System.out.println("Calling  fetch employee by id:"+empId);
		return employeeRepository.findById(empId).get();
	}
}
