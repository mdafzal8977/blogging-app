package com.afzalSpringboot.blog.controllers;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.afzalSpringboot.blog.config.AppConstants;
import com.afzalSpringboot.blog.payloads.ApiResponse;
import com.afzalSpringboot.blog.payloads.PostDto;
import com.afzalSpringboot.blog.payloads.PostResponse;
import com.afzalSpringboot.blog.services.FileService;
import com.afzalSpringboot.blog.services.PostService;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins="*",allowedHeaders="*")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")//as mentioned in properties file
	private String path;
//	create

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}

	// get by user

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {

		List<PostDto> posts = this.postService.getPostsByUser(userId);
		return new ResponseEntity<>(posts, HttpStatus.OK);

	}
	/*
	//Implementing Pagination
		@GetMapping("/users/{userId}/posts")
		public ResponseEntity<PostResponse> getPostsByUser(
				@PathVariable Integer userId,
				@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
				@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false) Integer pageSize,
				@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false)String sortBy,
				@RequestParam(value="sortingOrder",defaultValue=AppConstants.SORT_DIR,required=false)String sortingOrder
				){
			return new ResponseEntity<>(this.postService.getAllPostsByUser(userId, pageNumber, pageSize,sortBy,sortingOrder),HttpStatus.OK);
			
		}
	*/
	// get by category

	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {

		List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

	}
	/*
	//Implementing pagination
		@GetMapping("/categories/{categoryId}/posts")
		public ResponseEntity<PostResponse> getPostsByCategory(
				@PathVariable Integer categoryId,
				@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false)Integer pageNumber,
				@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false)Integer pageSize,
				@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false)String sortBy,
				@RequestParam(value="sortingOrder",defaultValue=AppConstants.SORT_DIR,required=false)String sortingOrder
				){
			return new ResponseEntity<>(this.postService.getAllPostsByCategory(categoryId, pageNumber, pageSize,sortBy,sortingOrder),HttpStatus.OK);
		}
	*/
	// get all posts
	
		/*@GetMapping("/posts")
		public ResponseEntity<List<PostDto>> getAllPost() {
			return new ResponseEntity<>(this.postService.getAllPosts(), HttpStatus.OK);
		}*/
	//Implementing pagination
		//Page number and page size will come by url as requestparameter
		//default value will contains numbers in string form
		//pageNumber starts from 0

		/*
		@GetMapping("/posts")
		public ResponseEntity<List<PostDto>> getAllPost(
				@RequestParam(value="pageNumber",defaultValue="0",required=false)Integer pageNumber,
				@RequestParam(value="pageSize",defaultValue="5",required=false)Integer pageSize
				) {
			return new ResponseEntity<>(this.postService.getAllPost(pageNumber,pageSize), HttpStatus.OK);
		}
		*/
//---------------------------------------------------------------------------------------------------
	//Adding not only content(i.e List<PostDto>) but also some extra informations such as 
		//pageNumber,PageSize,totalElements,totalPages,isLastPage to the response
		//So we have created another DTO/Payload  class 'PostResponse'
		//Later adding sorting
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}

	// get post details by id

	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {

		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);

	}

	// delete post
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ApiResponse("Post is successfully deleted !!", true);
	}

	// update post

	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {

		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

	}

	// search posts by title
	@GetMapping("/posts/search/{keywords}")
	// search posts by title
	//Here we may take input string from form or request-parameter or through url
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords) {
		List<PostDto> result = this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
	}

	// post image upload

	//Post-image upload
	//Here i have created a separate api for image upload only
		//From front-end if anyone wants to create post then he has to fire/call 2 apis..One of creating
		//post and one of uploading image api
		//I could have written this logic in the api of creating post also
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException {
		//Here instead of throws clause, can do global exception handling of IOException or try-catch
		PostDto postDto = this.postService.getPostById(postId);
		//If exception occurs at the above line,then here ResourceNotFoundException will be thrown,rest of the lines would not get executed
		String fileName = this.fileService.uploadImage(path, image);
		//now uploading this file name in the database(i.e uploading Post entity's image name field)
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

	}

	

    //method to serve files
    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream())   ;

    }

}
