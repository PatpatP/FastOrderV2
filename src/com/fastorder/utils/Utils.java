package com.fastorder.utils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.fastorder.enumeration.EstimatedTimeEnum;
import com.fastorder.enumeration.OrderStatusEnum;
import com.fastorder.enumeration.ProductTypeEnum;
import com.fastorder.enumeration.ShopTypeEnum;
import com.fastorder.manager.impl.ShopManagerImpl;
import com.fastorder.model.User;
import com.google.gson.Gson;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Utils {
	
	public static Statement connectBDD(){
		try {
			System.out.println( "Chargement du driver..." );
			Class.forName( "com.mysql.jdbc.Driver" );
			System.out.println( "Driver chargé !" );
		} catch ( ClassNotFoundException e ) {
			System.out.println( "Erreur lors du chargement : le driver n'a pas été trouvé dans le classpath ! <br/>"
					+ e.getMessage() );
		}

		/* Connexion à la base de données */
		String url = "jdbc:mysql://localhost:3306/fastorder";
		String user = "root";
		String password = "";
		Connection connexion = null;
		Statement statement = null;
		

		try {
			connexion = (Connection) DriverManager.getConnection( url, user, password );
			statement = (Statement) connexion.createStatement();
			return statement;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static int insertQuery(Statement statement, String query){
		try {
			int res = statement.executeUpdate(query);
			System.out.println("Résultat insert "+res);
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static ResultSet selectQuery(Statement statement, String query){
		ResultSet resultat = null;
		try {
			resultat = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultat;
	}
	
	public static int updateQuery(Statement statement, String query){
		try {
			int res = statement.executeUpdate(query);
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int deleteQuery(Statement statement, String query){
		try {
			int res = statement.executeUpdate(query);
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
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
		ShopManagerImpl sm = new ShopManagerImpl(connectBDD());
		
		User user = sm.getOwner(0);
		System.out.println(user.getFirstName()+" "+user.getLastName());
	}

}
