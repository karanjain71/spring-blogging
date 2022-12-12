package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.JwtAuthRequest;
import com.blog.payloads.JwtAuthResponse;
import com.blog.payloads.UserDto;
import com.blog.security.JwtTokenHelper;
import com.blog.services.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<?> createToken(@RequestBody JwtAuthRequest request){
		System.out.println(request.getEmail() + " " + request.getPassword());
		try {
			Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                request.getEmail(),
	                request.getPassword()));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
//			this.authenticate(request.getEmail(), request.getPassword());
			String token = jwtTokenHelper.generateToken(authentication);
			JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
			jwtAuthResponse.setToken(token);
			return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.OK);
		}
		catch(BadCredentialsException e) {
			ApiResponse apiResponse = new ApiResponse("Invalid Username or password", false);
			return new ResponseEntity<>(apiResponse , HttpStatus.UNAUTHORIZED);
		}
		
		
	}

	private void authenticate(String email, String password) {
		
		try {			
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
			authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		}
		catch(DisabledException e) {
			System.out.println("The user is disabled currently");
		}
		
	}
	@PostMapping("/register")
	private ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		UserDto registeredUser = userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
	}
	
	@GetMapping("/checkingAuth")
	private ResponseEntity<String> checkingAuth(){
		
		System.out.println("If I am coming here then it is impossible to not come out");
		return new ResponseEntity<String>("Hello Buddy",HttpStatus.OK);
	}

}
