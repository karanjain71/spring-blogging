package com.blog.services;

import java.util.List;

import com.blog.payloads.CommentDto;

public interface CommentService {
	
	List<CommentDto> getCommentsByPost(Integer postId);
	CommentDto createComment(CommentDto commentDto, Integer postId);
	void deleteComment(Integer commentId);
	
	
}
