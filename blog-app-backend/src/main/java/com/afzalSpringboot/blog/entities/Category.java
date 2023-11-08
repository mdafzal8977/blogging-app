package com.afzalSpringboot.blog.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;

	@Column(name = "title", length = 100, nullable = false)
	private String categoryTitle;

	@Column(name = "description")
	private String categoryDescription;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	// CascadeType.ALL--->So that if the parent is removed then all its child also
	// gets removed and
	// if the parent is added then child should not be saved separately rather saved  automatically
	// FetchType.Lazy is used so that when you fetch Parent then child is not
	// fetched
	private List<Post> posts = new ArrayList<>();

}
