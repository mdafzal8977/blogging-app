package com.afzalSpringboot.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//null is also considered as blank
//If @NotNull is not mentioned then @NotBlank will come to play
public class PostDto {

	private Integer postId;
	// @NotEmpty also includes @NotNull
	@NotEmpty(message = "Title should not be empty and null")
	@NotBlank(message = "Title should not be blank")
	@Size(max = 30, message = "Title must not be greater than 30")
	private String title;
	@NotNull(message = "Title must not be null") // Emptiness covered in @Size
	@NotBlank(message = "Content must not be blank and null")
	@Size(min = 10, message = "Content must not be less than 10 characters")
	private String content;
	private String imageName;
	private Date addedDate;
	
	//Here in the below two statements by mistake i had taken Category and User in place of their DTOs due to which recusion had taken place . 
	private CategoryDto category;

	private UserDto user;
	//This is for getting comments....i am not making separate method and api for getting comments of a post
		//We will make apis only of create and delete comments
		//Besides if we want to implement pagination,then we will have to make a separate api of get for comments

	private Set<CommentDto> comments = new HashSet<>();

}
