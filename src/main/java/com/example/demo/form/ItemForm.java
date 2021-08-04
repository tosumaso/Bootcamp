package com.example.demo.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ItemForm {
	
	@Max(value=999)
	private int id;
	
	@NotBlank
	@Size(max=5)
	@Pattern(regexp="[a-z][a-z0-9]*")
	private String name;
	
	@NotBlank
	@Max(value=2000)
	private int price;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
}
