package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDto;
import com.blog.repositories.RoleRepository;
import com.blog.repositories.UserRepository;
import com.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = dtoToUser(userDto);
//		System.out.println(user.getName());
//		System.out.println(user.getAbout());
		User savedUser = userRepository.save(user);
		return userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser = userRepository.save(user);
		return userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id", userId));
		return userToDto(user);
	}
	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users = userRepository.findAll();
		List<UserDto> userDtos = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id", userId));
		userRepository.delete(user);

	}
	
	private User dtoToUser(UserDto userDto) {
		
		User user = modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
//		
		return user;
	}
	
	public UserDto userToDto(User user) {
		
		UserDto userDto = modelMapper.map(user, UserDto.class);
		return userDto;
	}
	
	public UserDto registerNewUser(UserDto userDto) {
		User user = modelMapper.map(userDto, User.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role role = roleRepository.findById(AppConstants.NORMAL_USER_ROLE_ID).get();
		user.getRoles().add(role);
		userRepository.save(user);
		UserDto createdUserDto = modelMapper.map(user, UserDto.class);
		
		return createdUserDto;
		
	}

}
