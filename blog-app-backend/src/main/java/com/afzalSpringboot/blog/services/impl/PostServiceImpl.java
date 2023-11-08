package com.afzalSpringboot.blog.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.afzalSpringboot.blog.entities.Category;
import com.afzalSpringboot.blog.entities.Post;
import com.afzalSpringboot.blog.entities.User;
import com.afzalSpringboot.blog.exceptions.ResourceNotFoundException;
import com.afzalSpringboot.blog.payloads.PostDto;
import com.afzalSpringboot.blog.payloads.PostResponse;
import com.afzalSpringboot.blog.repositories.CategoryRepo;
import com.afzalSpringboot.blog.repositories.PostRepo;
import com.afzalSpringboot.blog.repositories.UserRepo;
import com.afzalSpringboot.blog.services.PostService;
//There is also an inbuilt class named 'User' in 'org.apache.catelina.User' package. So be careful while
//importing package

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "User id", userId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id ", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = this.postRepo.save(post);

		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "post id", postId));

		Category category = this.categoryRepo.findById(postDto.getCategory().getCategoryId()).get();

		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		// For image update we can make a separate service
		// For updating category we can pass here for update
		// So for both the above cases, we would have to add some fields in
		// PostDto
		post.setCategory(category);

		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);

	}

	@Override
	public void deletePost(Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "post id", postId));

		this.postRepo.delete(post);

		/*
		 * if(this.postRepo.findById(postId).isEmpty()) throw new
		 * ResourceNotFoundException("Post","Post_Id", postId);
		 * this.postRepo.deleteById(postId);
		 */

	}

	@Override
	/*
	  public List<PostDto> getAllPost() {
		List<Post> allPosts1=postRepo.findAll();
		List<PostDto> allPosts2=new ArrayList<>();
		Iterator<Post> itr=allPosts1.iterator();
		while(itr.hasNext()) {
			PostDto postDto=modelMapper.map(itr.next(),PostDto.class);
			allPosts2.add(postDto);
		}
		return allPosts2;
	}*/
//-----------------------------------------------------------------------------------------		
	//Pageable is an interface present in 'org.springframework.data.domain.Pageable' package
	//public abstract class AbstractPageRequest implements Pageable, Serializable {--}
	//public class PageRequest extends AbstractPageRequest {----}
	
	/*public List<PostDto> getAllPost(Integer pageNumber,Integer pageSize){
		Pageable p=PageRequest.of(pageNumber, pageSize);//Returns an unsorted PageRequest object and PageRequest class in an implementation class of Pageable
		//Page is also an interface
		//Implementing pagination in getAllPosts
		Page<Post> pagePost=postRepo.findAll(p);
		List<Post> allpostsOfPage_1=pagePost.getContent();
		List<PostDto> allPostsOfPage_2=new ArrayList<>();
		Iterator<Post> itr=allpostsOfPage_1.iterator();
		while(itr.hasNext()) {
			PostDto postDto=modelMapper.map(itr.next(),PostDto.class);
			allPostsOfPage_2.add(postDto);
		}
		return allPostsOfPage_2;
	}*/
//-----------------------------------------------------------------------------------------------
//	public PostResponse getAllPosts(Integer pageNumber,Integer pageSize ) {
//		Pageable p=PageRequest.of(pageNumber,pageSize);
//		Page<Post> pageOfPosts=postRepo.findAll(p);
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		//Be careful to import 'Sort' class of 'org.springframework.data.domain' package
		
		   //Pageable p=PageRequest.of(pageNumber,pageSize,Sort.by(sortBy)); 
			//Pageable p=PageRequest.of(pageNumber,pageSize,Sort.by(sortBy).descending());  //By default ascending but we will pass dynamically by adding one more parameter to the method
			//Sort.by() method returns Sort class object

		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable p = PageRequest.of(pageNumber, pageSize, sort);

		Page<Post> pagePost = this.postRepo.findAll(p);

		List<Post> allPosts = pagePost.getContent();

		List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();

		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());

		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
	//	if(categoryRepo.findById(categoryId).isEmpty())
	//	throw new ResourceNotFoundException("Category","CategoryId", categoryId);
		Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
		
		List<Post> list1=this.postRepo.findByCategory(category);
		List<PostDto> list2=new ArrayList<>();
//		for(Post post:list1) {
//			PostResponseDto postResponseDto=modelMapper.map(post,PostResponseDto.class);
//			list2.add(postResponseDto);
		list1.forEach(post->{
			PostDto postResponseDto=modelMapper.map(post,PostDto.class);
			list2.add(postResponseDto);
		});
		return list2;

		/*Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);

		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return postDtos;*/
	}
/*	//Implementing pagination
	@Override
	public PostResponse getAllPostsByCategory(Integer categoryId,Integer pageNumber,Integer pageSize,String sortBy,String sortingOrder) {
		Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category_Id",categoryId));
		//List<Post> post=postRepo.findByCategory(category);
		Sort sort=(sortingOrder.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		Pageable p=PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pageOfPosts=postRepo.findAllByCategory(category, p);
		List<Post> list1=pageOfPosts.getContent();
		List<PostDto> list2=new ArrayList<>();
		for(Post post:list1) {
			PostDto postDto=modelMapper.map(post,PostDto.class);
			list2.add(postResponseDto);
		}
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(list2);
		postResponse.setPageNumber(pageOfPosts.getNumber());
		postResponse.setPageSize(pageOfPosts.getSize());
		postResponse.setTotalElements(pageOfPosts.getTotalElements());
		postResponse.setTotalPages(pageOfPosts.getTotalPages());
		postResponse.setLastPage(pageOfPosts.isLast());
		return postResponse;
	}
*/
	@Override
	public List<PostDto> getPostsByUser(Integer userId) {

		/*User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "userId ", userId));
		List<Post> posts = this.postRepo.findByUser(user);

		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return postDtos;*/
		User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","UserId",userId));
		List<Post> list1=postRepo.findByUser(user);
		List<PostDto> list2=new ArrayList<>();
//		for(Post post:list1) {
//			PostDto postDto=modelMapper.map(post,PostDto.class);
//			list2.add(postDto);
//		}
		list1.forEach(post->{
			PostDto postDto=modelMapper.map(post,PostDto.class);
			list2.add(postDto);
		});
		return list2;
	}
	/*
	//Implementing pagination 
		@Override
		public PostResponse getAllPostsByUser(Integer userId,Integer pageNumber,Integer pageSize,String sortBy,String sortingOrder) {
			if(userRepo.findById(userId).isEmpty())
				throw new ResourceNotFoundException("User","User_Id",userId);
			Sort sort=(sortingOrder.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
			//Pageable p=PageRequest.of(pageNumber, pageSize,Sort.by(sortBy).descending());
			Pageable p=PageRequest.of(pageNumber, pageSize,sort);
			Page<Post> pageOfPosts=postRepo.findByUserId(userId,p);
			//if(pageOfPosts.isEmpty())
				//throw new ResourceNotFoundException("User","User_Id",userId);
			
			List<Post> list1=pageOfPosts.getContent();
			List<PostDto> list2=new ArrayList<>();
			for(Post post:list1) {
				PostDto postDto=this.modelMapper.map(post,PostDto.class);
				list2.add(postDto);
			}
			PostResponse postResponse=new PostResponse();
			postResponse.setContent(list2);
			postResponse.setPageNumber(pageOfPosts.getNumber());
			postResponse.setPageSize(pageOfPosts.getSize());
			postResponse.setTotalElements(pageOfPosts.getTotalElements());
			postResponse.setTotalPages(pageOfPosts.getTotalPages());
			postResponse.setLastPage(pageOfPosts.isLast());
			return postResponse;
		}
	*/

	@Override
	public List<PostDto> searchPosts(String keyword) {
		//List<Post> list1=postRepo.findByTitleContaining(Keywords);
		//Using Custom Query method
		List<Post> list1 = this.postRepo.searchByTitle("%" + keyword + "%");
		/*List<PostDto> list2 = list1.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return list2;*/
		List<PostDto> list2=new ArrayList<>();
		for(Post post:list1) {
			PostDto postDto=this.modelMapper.map(post,PostDto.class);
			list2.add(postDto);
		}
		return list2;
	}

}
