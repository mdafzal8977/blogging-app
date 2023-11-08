package com.afzalSpringboot.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
//@Min(Value=5) and @Max(value=10) annotation not working.
public class UserDto {
	private int id;
	// @NotEmpty also includes @NotNull
	@NotEmpty(message = "Username must not be empty")
	@Size(min = 2, message = "Username must not be less than 2 characters")
	@NotBlank(message = "Username must not be blank")
	// NotBlank is different from NotNull And NotEmpty---> ""-->Empty & " "--->Blank
	private String name;
	@Email(message = "Not a valid email address")
	// @Pattern(regexp = "")
	// @Email only checks that '@' is present and before and after '@' there is a
	// character except '.'
	// If you want something more use regex by using @Pattern(regexp="") annotation
	private String email;
	@NotNull(message = "Password must not be null")
	@NotBlank(message = "Password must not be blank")
	@Size(min = 4, max = 10, message = "Password must conatain a minimum of 4 and maximum of 10 characters")
	private String password;
	@NotEmpty(message = "Username must not be empty")
	@NotBlank(message = "Username must not be blank")
	private String about;
	
	//For one to many relationship between user and comments
	//private List<CommentDto> comments=new ArrayList<>();

	private Set<RoleDto> roles = new HashSet<>();

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

}
