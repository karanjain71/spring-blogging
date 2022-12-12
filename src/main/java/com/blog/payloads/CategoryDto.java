package com.blog.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

	private int id;
	
	@NotEmpty(message = "The category title should not be empty")
	@Size(min=3,message = "The category title should be more than 3 chars")
	private String categoryTitle;
	
	@NotEmpty(message = "The category description should not be empty")
	@Size(min=3, message = "The category description should be more than 3 chars")
	private String categoryDescription;
}
