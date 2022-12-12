package com.blog.exceptions;

public class BadCredentialsException extends RuntimeException{
	private String message;
	private boolean success;
	
	public BadCredentialsException(String message, boolean success){
		super("Invalid Username or Password");
	}
}
