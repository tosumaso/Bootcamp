package com.example.demo.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class Test2 {

	@NotNull
	@Max(value=2000)
	private int price;

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
