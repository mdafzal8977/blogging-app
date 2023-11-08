package com.afzalSpringboot.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private Integer categoryId;
	@NotEmpty(message="CategoryTitle must not be empty and null")//NotEmpty Also includes NotNull
	@NotBlank(message="categoryTitle must not be blank")
	private String categoryTitle;
	@NotNull(message="categoryDescription must not be null")//Emptiness covered in @Size
	@NotBlank(message="categoryDescription must not be blank")
	@Size(min=10,message="CategoryDescription must not be less than 10 characters")
	private String categoryDescription;

}
