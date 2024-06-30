package com.codedecode.microservice.apiGateway.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codedecode.microservice.apiGateway.dto.AuthRequest;
import com.codedecode.microservice.apiGateway.entity.UserInfo;
import com.codedecode.microservice.apiGateway.service.JwtService;
import com.codedecode.microservice.apiGateway.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.models.annotations.OpenAPI30;

@CrossOrigin("/")
@RestController
@RequestMapping("/products")
@OpenAPI30
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@PostMapping("/saveUser")
	public ResponseEntity<UserInfo> saveUser(@RequestBody UserInfo user )
	{
		System.out.println("Saving the user...");
		UserInfo saveUser = userService.saveUser(user);
		return new ResponseEntity<UserInfo>(saveUser, HttpStatus.OK);
	}
	
	@Operation(summary = "Welcoming the user")
	@ApiResponse(description = "User welcomed.",responseCode = "200")
	@GetMapping("/welcome")
	public String getName()
	{
		return "Hellow world";
	}
	
	@Operation(summary = "This API returns the product list.")
	@ApiResponse(description = "Successfully returned the product list.",responseCode = "200")
	@GetMapping("/productList")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<String> getListOfProducts()
	{
		List<String> list =new ArrayList();
		list.add("Product1");
		list.add("Product2");
		list.add("Product3");
		list.add("Product4");
		return list;
	}
	
	
	@Operation(summary = "This API returns the list of product id's.")
	@ApiResponse(description = "Successfully returned the product id list.",responseCode = "200")
	@GetMapping("/productidList")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public List<String> getListOfProductsIds()
	{
		List<String> list =new ArrayList();
		list.add("Product111");
		list.add("Product222");
		list.add("Product333");
		list.add("Product444");
		return list;
	}
	
	@Operation(summary = "This API performs the authentication.")
	@ApiResponses(value = {@ApiResponse(description = "Successfully authenticated the user.",responseCode = "200"),
			@ApiResponse(description = "Failed to authenticate the user.",responseCode = "400")})
	@PostMapping("/authenticate")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest)
	{
		Authentication authenticate = authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if(authenticate.isAuthenticated())
		{
			return jwtService.generateToken(authRequest.getUsername());
		}else
		{
			throw new UsernameNotFoundException("Invalid user exception..!");
		}
		
	}
	
	

}
