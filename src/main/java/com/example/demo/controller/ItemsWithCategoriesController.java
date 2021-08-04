package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Category;
import com.example.demo.entity.ItemsWithCategories;
import com.example.demo.repository.ItemsWithCategoriesRepository;



@Controller
public class ItemsWithCategoriesController {
	
	@Autowired
	ItemsWithCategoriesRepository repository;
	
	@RequestMapping("/items/findItemAndCategory")//他のテーブルを外部参照して表示
	public String showItemAndCategoryList(Model model) {
		model.addAttribute("itemsCategories", repository.findAll());
		return "items/item_category_list";
	}
	
	@RequestMapping("/items/searchByCategoryId/{categoryId}") //外部参照キーで検索
	public String searchByCategoryId(@PathVariable int categoryId, Model model) {
		Category category = new Category();
		category.setId(categoryId);
		List<ItemsWithCategories> items=repository.findByCategory(category);
		model.addAttribute("itemsCategories", items);
		return "items/item_category_list";
	}
	
	
}
