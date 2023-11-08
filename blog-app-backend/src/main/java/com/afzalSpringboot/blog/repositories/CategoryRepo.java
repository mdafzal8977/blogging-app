package com.afzalSpringboot.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afzalSpringboot.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
