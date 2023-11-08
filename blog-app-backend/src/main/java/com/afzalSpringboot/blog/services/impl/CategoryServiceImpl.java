package com.afzalSpringboot.blog.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afzalSpringboot.blog.entities.Category;
import com.afzalSpringboot.blog.exceptions.ResourceNotFoundException;
import com.afzalSpringboot.blog.payloads.CategoryDto;
import com.afzalSpringboot.blog.repositories.CategoryRepo;
import com.afzalSpringboot.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category addedCat = this.categoryRepo.save(cat);
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category ", "Category Id", categoryId));

		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());

		Category updatedcat = this.categoryRepo.save(cat);

		return this.modelMapper.map(updatedcat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {

		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category ", "category id", categoryId));
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		ArrayList<CategoryDto> al1 = new ArrayList<>();
		List<Category> al2 = categoryRepo.findAll();
		for (Category category : al2) {
			CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
			al1.add(categoryDto);

		}
		return al1;

		/*
		 * List<Category> categories = this.categoryRepo.findAll(); List<CategoryDto>
		 * catDtos = categories.stream().map((cat) -> this.modelMapper.map(cat,
		 * CategoryDto.class)) .collect(Collectors.toList());
		 * 
		 * return catDtos;
		 */
	}

}
