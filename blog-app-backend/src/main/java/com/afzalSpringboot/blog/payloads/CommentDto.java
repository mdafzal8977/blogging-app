package com.afzalSpringboot.blog.payloads;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
//@NoArgsConstructor
//Default constructor will be provided by default
public class CommentDto {
	
	private int id;

	private String content;

}
