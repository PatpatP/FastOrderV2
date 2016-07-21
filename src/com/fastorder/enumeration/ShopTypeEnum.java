package com.fastorder.enumeration;

public enum ShopTypeEnum {
	
	JAPONAIS("Japonais"),
	CHINOIS("Chinois");
	
	String name = "";
	
	ShopTypeEnum(String name){
		this.name = name;
	}
	
	@Override
	public String toString(){
		return name;
	}

}
