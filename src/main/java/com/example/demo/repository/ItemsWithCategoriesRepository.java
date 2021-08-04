package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Category;
import com.example.demo.entity.ItemsWithCategories;

public interface ItemsWithCategoriesRepository extends JpaRepository<ItemsWithCategories,Integer> {
	
	List<ItemsWithCategories>findByCategory(Category category);
}
