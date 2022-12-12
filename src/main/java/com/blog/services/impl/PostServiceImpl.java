package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repositories.CategoryRepository;
import com.blog.repositories.PostRepository;
import com.blog.repositories.UserRepository;
import com.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		System.out.println(userId + " userId " + categoryId + " categoryId ");
		
		User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		System.out.println(category.getCategoryTitle() + " title");
		System.out.println(user.getName());
		Post post = modelMapper.map(postDto, Post.class);
		post.setCategory(category);
		post.setUser(user);
		System.out.println(post.getUser().getName() + " this is post");
		Post createdPost = postRepository.save(post);
		return modelMapper.map(createdPost,PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		return modelMapper.map(postRepository.save(post), PostDto.class) ;
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));
		postRepository.delete(post);
		
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		Sort sort = Sort.by(sortBy).ascending();
		if(sortDir.equals("desc")) {
			sort = Sort.by(sortBy).descending();
		}
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = postRepository.findAll(p);
		List<Post> allPosts = pagePost.getContent();
		List<PostDto> postDtos = allPosts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements((int)pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		List<Post> posts = postRepository.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		List<Post> posts = postRepository.findByUser(user);
		List<PostDto> postDtos = posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = postRepository.findByTitleContaining(keyword);
		List<PostDto> postDto = posts.stream().map(post-> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}

}
