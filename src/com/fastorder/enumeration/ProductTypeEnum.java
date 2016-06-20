package com.fastorder.enumeration;

public enum ProductTypeEnum {
	
	DRINK("Drink"),
	DISH("Dish"),
	STARTER("Starter"),
	DESSERT("Dessert");
	
	String productType = "";
	
	private ProductTypeEnum(String productType) {
		this.productType = productType;
	}
	
	public String toString(){
		return productType;
	}

}
