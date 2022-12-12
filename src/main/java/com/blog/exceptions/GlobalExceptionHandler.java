package com.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.payloads.ApiResponse;


//it will scan all the controllers and check for all the exceptions 
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		String msg = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(msg,false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> argumentNotValidException(MethodArgumentNotValidException ex){
		System.out.println(ex+ "here");
		String msg = ex.getMessage();
		Map<String,String> exceptionMessage= new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error->{
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			exceptionMessage.put(fieldName, message);
		});
		return new ResponseEntity<Map<String,String>>(exceptionMessage, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(EmailNotFoundException.class)
	public ResponseEntity<ApiResponse> emailNotFoundException(EmailNotFoundException ex){
		String msg = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(msg,false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiResponse> badCredentialsException(BadCredentialsException ex){
		String msg = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(msg,false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		
	}
	
}
