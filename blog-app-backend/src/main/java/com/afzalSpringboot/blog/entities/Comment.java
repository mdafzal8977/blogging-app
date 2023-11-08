package com.afzalSpringboot.blog.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String content;
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;
	//For many comments can belong to one user
	/*@ManyToOne
	@JoinColumn(name="user_id")
	private User user;*/
}
