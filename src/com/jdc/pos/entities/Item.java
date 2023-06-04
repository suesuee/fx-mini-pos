package com.jdc.pos.entities;

public class Item {

	private int id;
	private String name;
	private Category category;//Enum
	private int price;
	
	public Item() {
		
	}
	
	public Item(String ... data) { //Convert to obj and put it as array;
		id = Integer.parseInt(data[0]);
		category = Category.valueOf(data[1]); //enum to string;
		name = data[2];
		price = Integer.parseInt(data[3]);
	}

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return name;
	}
	
	

}
