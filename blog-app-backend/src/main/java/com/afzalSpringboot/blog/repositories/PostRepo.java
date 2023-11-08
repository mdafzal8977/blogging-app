package com.afzalSpringboot.blog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.afzalSpringboot.blog.entities.Category;
import com.afzalSpringboot.blog.entities.Post;
import com.afzalSpringboot.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	//Not available in repository--->
		//List<Post> findByUser(Integer userId)
		List<Post> findByUser(User user);
		//This method(first one below)is not available-->
		//List<Post> findByCategory(Integer categoryId);
		List<Post> findByCategory(Category category);
		 
		//Modifying above methods for pagination
		Page<Post> findAllByCategory(Category category,Pageable page);
		Page<Post> findByUserId(Integer userId,Pageable page);
		
		//For searching,because by default we don't get any method for searching.This method is also available but we have to mention here then only it would be available
		//List<Post> findByContentContaining(String content);//For searching content wise
		/*List<Post> findByTitleContaining(String title);*/ 
		
		
		// Using JPQL query through @Query annotation, will work same as above method for searching
		 //This is purely a custom method
		
		//Custom Query method
		
		@Query("select p from Post p where p.title like :key")//'key' is basically a parameter for dynamically passing value
		List<Post> searchByTitle(@Param("key") String title );
		
		
	

}
