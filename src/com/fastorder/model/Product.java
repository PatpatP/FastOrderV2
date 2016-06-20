package com.fastorder.model;

import com.fastorder.enumeration.ProductTypeEnum;

public class Product {
	
	private int id;
	private ProductTypeEnum productType;
	private String name;
	private String description;
	private Float price;
	private int shopId;
	
	public Product(int id, ProductTypeEnum productType, String name, String description, Float price, int shopId) {
		super();
		this.id = id;
		this.productType = productType;
		this.name = name;
		this.description = description;
		this.price = price;
		this.shopId = shopId;
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

	public ProductTypeEnum getProductType() {
		return productType;
	}

	public void setProductType(ProductTypeEnum productType) {
		this.productType = productType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	
}
