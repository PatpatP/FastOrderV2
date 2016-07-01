package com.fastorder.utils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.fastorder.enumeration.EstimatedTimeEnum;
import com.fastorder.enumeration.OrderStatusEnum;
import com.fastorder.enumeration.ProductTypeEnum;
import com.fastorder.enumeration.ShopTypeEnum;
import com.google.gson.Gson;


public class Utils {
	final static Logger logger = Logger.getLogger(Utils.class);

	public static String convertJavaToJson(Object object){
		Gson gson = new Gson();
		String json = gson.toJson(object);

		return json;
	}

	public static String[] convertStringToTabString(String string){
		String newString = string.substring(1, string.length()-1);

		return newString.split(",");
	}

	public static OrderStatusEnum getOrderStatus(String orderStatus){
		OrderStatusEnum orderStatusEnum = null;

		switch (orderStatus) {
		case "In Progress":
			orderStatusEnum = OrderStatusEnum.INPROGRESS;
			break;
		case "Ready":
			orderStatusEnum = OrderStatusEnum.READY;
			break;	
		default:
			break;
		}

		return orderStatusEnum;
	}

	public static EstimatedTimeEnum getEstimatedTime(String estimatedTime){
		EstimatedTimeEnum estimatedTimeEnum = null;

		switch (estimatedTime) {
		case "15 min":
			estimatedTimeEnum = EstimatedTimeEnum.FIFTY;
			break;
		case "30 min":
			estimatedTimeEnum = EstimatedTimeEnum.THIRTY;
			break;
		case "45 min":
			estimatedTimeEnum = EstimatedTimeEnum.FORTYFIVE;
			break;	
		default:
			break;
		}

		return estimatedTimeEnum;
	}

	public static ProductTypeEnum getProductType(String productType){
		ProductTypeEnum productTypeEnum = null;

		switch (productType) {
		case "Drink":
			productTypeEnum = ProductTypeEnum.DRINK;
			break;
		case "Dish":
			productTypeEnum = ProductTypeEnum.DISH;
			break;
		case "Starter":
			productTypeEnum = ProductTypeEnum.STARTER;
			break;
		case "Dessert":
			productTypeEnum = ProductTypeEnum.DESSERT;
		default:
			break;
		}

		return productTypeEnum;
	}

	public static ShopTypeEnum getShopType(String shopType){
		ShopTypeEnum shopTypeEnum = null;

		switch (shopType) {
		case "Japonais":
			shopTypeEnum = ShopTypeEnum.JAPONAIS;
			break;
		case "Chinois":
			shopTypeEnum = ShopTypeEnum.CHINOIS;
			break;
		default:
			break;
		}

		return shopTypeEnum;
	}

	public static List<String> initProductType(){
		List<String> productTypes = new ArrayList<String>();
		productTypes.add(ProductTypeEnum.STARTER.toString());
		productTypes.add(ProductTypeEnum.DISH.toString());
		productTypes.add(ProductTypeEnum.DESSERT.toString());
		productTypes.add(ProductTypeEnum.DRINK.toString());
		return productTypes;
	}

	public static void main(String[] args) throws SQLException {

	}
}