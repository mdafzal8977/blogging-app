package com.afzalSpringboot.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afzalSpringboot.blog.entities.Comment;

public interface CommentRepo  extends JpaRepository<Comment	, Integer> {

}
