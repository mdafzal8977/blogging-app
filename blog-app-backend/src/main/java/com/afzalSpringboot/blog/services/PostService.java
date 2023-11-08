package com.afzalSpringboot.blog.services;

import java.util.List;

import com.afzalSpringboot.blog.payloads.PostDto;
import com.afzalSpringboot.blog.payloads.PostResponse;

public interface PostService {

	// create

	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	// update

	PostDto updatePost(PostDto postDto, Integer postId);

	// delete

	void deletePost(Integer postId);

	// Get All posts
	// List<PostDto> getAllPost();
	// After Implementing pagination in getllPosts
	// List<PostResponse> getAllPost(Integer pageNumber,Integer pageSize);
	// Adding not only content(i.e List<PostDto>) but also some extra
	// informations such as
	// pageNumber,PageSize,totalElements,totalPages,isLastPage to the response
	// So we have created another Dto/Payload class 'PostResponse'
	// PostResponsePage getAllPosts(Integer pageNumber, Integer pageSize);
	// Also implementing sorting in pagination

	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	// get single post

	PostDto getPostById(Integer postId);

	// Get All Posts by category
	// List<PostResponse> getPostsByCategory(Integer CategoryId);
	// After implementing pagination in getPostsByCategory
	// PostResponse getAllPostsByCategory(Integer categoryId,Integer
	// pageNumber,Integer pageSize,String sortBy,String sortingOrder);

	List<PostDto> getPostsByCategory(Integer categoryId);

	// get all posts by user
	// Get All Posts by User
	// List<PostResponse> getPostsByUser(Integer UserId);
	// After Implementing pagination in getPostsByUser
	// PostResponse getAllPostsByUser(Integer postId,Integer pageNumber,Integer
	// pageSize,String sortBy,String sortingOrder);

	List<PostDto> getPostsByUser(Integer userId);

	// search posts
	List<PostDto> searchPosts(String keyword);

}
