package com.fastorder.model;

import com.fastorder.enumeration.ShopTypeEnum;

public class Shop {
	
	private int id;
	private String name;
	private String description;
	private ShopTypeEnum shopType;
	private int userId;
	private int addressId;
	
	public Shop(int id, String name, String description, ShopTypeEnum shopType, int userId, int addressId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.shopType = shopType;
		this.userId = userId;
		this.addressId = addressId;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ShopTypeEnum getShopType() {
		return shopType;
	}

	public void setShopType(ShopTypeEnum shopType) {
		this.shopType = shopType;
	}
}
