package com.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailNotFoundException extends RuntimeException{
	private String resourceName;
	private String fieldName;
	private String emailValue;
	
	public EmailNotFoundException(String resourceName, String fieldName, String emailValue) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, emailValue));
	}
	
}
