package com.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDto;
import com.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createdUserDto = userService.createUser(userDto);
		return new ResponseEntity<>(createdUserDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable int userId){
		UserDto updatedUser = userService.updateUser(userDto, userId);
		return new ResponseEntity<>(updatedUser, HttpStatus.ACCEPTED);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> userDtos = userService.getAllUsers();
		return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") int userId){
		userService.deleteUser(userId);
		return new ResponseEntity<>(new ApiResponse("Deleted Successfully",true),HttpStatus.OK);
		
	}
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") int userId){
		UserDto userDto = userService.getUserById(userId);
		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);	
	}
	
	
	
	
	
	
}
