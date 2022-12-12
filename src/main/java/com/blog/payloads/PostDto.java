package com.blog.payloads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDto {

	private int id;
	
	@NotEmpty
	@Size(min=3, message = "The title length should be more than 3 characters")
	private String title;
	@NotEmpty
	@Size(min=3, message = "The title content should be more than 3 characters")
	private String content;
	private String imageName="default.png";
	private Date addedDate = new Date();
	private UserDto user;
	private CategoryDto category;
	private List<CommentDto> comments = new ArrayList<>();
}
