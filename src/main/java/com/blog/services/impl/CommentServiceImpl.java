package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CommentDto;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.PostRepository;
import com.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<CommentDto> getCommentsByPost(Integer postId) {
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		return post.getComments().stream().map(comment->modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
	}

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		System.out.println(commentDto.getContent()+ " commentDto");
		Comment comment = modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		CommentDto createdCommentDto = modelMapper.map(commentRepository.save(comment), CommentDto.class);
		return createdCommentDto;
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId));

		commentRepository.delete(comment);
		
	}

}
