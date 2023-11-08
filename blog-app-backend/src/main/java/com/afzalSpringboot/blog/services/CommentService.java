package com.afzalSpringboot.blog.services;

import com.afzalSpringboot.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, Integer postId);

	void deleteComment(Integer commentId);
	// Not creating getComments because already we can get from PostResponseDto
	// i.e from Posts we can get all comments of the post....
	// See PostResponse

}
