package com.blog.services;

import java.util.List;

import com.blog.payloads.CategoryDto;

public interface CategoryService {
	
	//	we don't need to write public and abstract because inside interface, they are bydefault public and abstract
	//create
	CategoryDto createCategory(CategoryDto categoryDto);
	//update
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	//delete
	void deleteCategory(Integer categoryId);
	
	//get
	CategoryDto getCategoryById(Integer categoryId);
	
	//getall
	List<CategoryDto> getAllCategories();
}
