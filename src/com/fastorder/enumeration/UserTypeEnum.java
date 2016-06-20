package com.fastorder.enumeration;

public enum UserTypeEnum {
	
	ADMIN("Admin"),
	CLIENT("Client"),
	MERCHANT("Merchant");
	
	private String name = "";
	
	UserTypeEnum(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}

}
