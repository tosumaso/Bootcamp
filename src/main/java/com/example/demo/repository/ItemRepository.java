package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Item;

public interface ItemRepository extends JpaRepository<Item,Integer> {

	List<Item>findAllByOrderByPriceDesc(); //指定したカラムの値を昇順、降順で検索
	List<Item>findByPrice(Integer price); //指定したカラムの値に一致したレコードを検索
	List<Item>findByNameAndPrice(String name,Integer price);//指定した２つのカラムの値に一致したレコードを検索
	List<Item>findByNameLike(String name); //指定したカラムが任意の値をもつか曖昧検索
	
	@Query("select count(i) from Item i where i.name Like :name")
	int countByNameLike(@Param("name")String name);
}
