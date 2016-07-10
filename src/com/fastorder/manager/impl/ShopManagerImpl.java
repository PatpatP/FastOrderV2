package com.fastorder.manager.impl;

import java.io.InputStream;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.fastorder.enumeration.EstimatedTimeEnum;
import com.fastorder.enumeration.OrderStatusEnum;
import com.fastorder.enumeration.ProductTypeEnum;
import com.fastorder.enumeration.ShopTypeEnum;
import com.fastorder.manager.IShopManager;
import com.fastorder.model.Order;
import com.fastorder.model.Product;
import com.fastorder.model.Shop;
import com.fastorder.model.User;
import com.fastorder.utils.Utils;
import com.fastorder.utils.UtilsBdd;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class ShopManagerImpl implements IShopManager{

	private Connection connection;

	final static Logger logger = Logger.getLogger(ShopManagerImpl.class);
	
	public ShopManagerImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Shop> getShops() {
		List<Shop> shops = new ArrayList<Shop>();

		String query = "Select * from shop";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet resultat = UtilsBdd.selectPreapredStatement(preparedStatement);
			while(resultat.next()){
				Shop shop = null;
				int id = resultat.getInt("id");
				String name = resultat.getString("name");
				String description = resultat.getString("description");
				String shopTypeString = resultat.getString("shopType");
				int idUser = resultat.getInt("user");
				int idAddress = resultat.getInt("address");

				ShopTypeEnum shopType = Utils.getShopType(shopTypeString);
				
				shop = new Shop(id, name, description, shopType, idUser, idAddress);

				shops.add(shop);
			}
			logger.info("Liste des magasins récupérée");
		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors de la récupération de la liste des magasins : " + e.getMessage());
		}

		return shops;
	}

	@Override
	public Shop getShop(Integer id) {
		Shop shop = null;

		String query = "Select * from shop where id=?;";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, id, Types.INTEGER);
			ResultSet resultat = UtilsBdd.selectPreapredStatement(preparedStatement);
			while(resultat.next()){
				int idShop = resultat.getInt("id");
				String name = resultat.getString("name");
				String description = resultat.getString("description");
				String shopTypeString = resultat.getString("shopType");
				int idUser = resultat.getInt("user");
				int idAddress = resultat.getInt("address");

				ShopTypeEnum shopType = Utils.getShopType(shopTypeString);
				shop = new Shop(idShop, name, description, shopType, idUser, idAddress);
			}
			logger.info("La magasin "+id+" a bien été récupéré");
		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors de la tentative de récupération d'un magasin à l'aide de l'ID : " + e.getMessage());
		}

		return shop;
	}

	@Override
	public User getOwner(int idShop) {
		Shop shop = getShop(idShop);
		int userId = shop.getUserId();
		
		UserManagerImpl userManager = new UserManagerImpl(connection);
		User user = userManager.getUser(userId);
		return user;
	}

	@Override
	public boolean createShop(String name, String description, String shopType, int userId, int addressId, InputStream image) {
		String query = "Insert into shop (name, description, shopType, user, address, image) VALUES(?,?,?,?,?,?);";
		System.out.println(query);
		
		PreparedStatement preparedStatement;
		int res = 0;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(5, addressId, Types.INTEGER);
			preparedStatement.setObject(1, name, Types.VARCHAR); 
			preparedStatement.setObject(2, description, Types.VARCHAR);
			preparedStatement.setObject(3, shopType, Types.VARCHAR); 
			preparedStatement.setObject(4, userId, Types.INTEGER);
			preparedStatement.setObject(6, image, Types.BLOB); 
			res = UtilsBdd.executePreapredStatement(preparedStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		} 

		if(res==1){
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean updateShop(int idShop, String name, String description, String shopType, int userId, int addressId) {
		String query = "Update shop SET name=?, description=?, shopType=?, user=?, address=? WHERE id=?;";
		PreparedStatement preparedStatement;
		int res = 0;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, name, Types.VARCHAR);
			preparedStatement.setObject(2, description, Types.VARCHAR);
			preparedStatement.setObject(3, shopType, Types.VARCHAR);
			preparedStatement.setObject(4, userId, Types.INTEGER);
			preparedStatement.setObject(5, addressId, Types.INTEGER);
			preparedStatement.setObject(6, idShop, Types.INTEGER);
			res = UtilsBdd.executePreapredStatement(preparedStatement);
			logger.info("Succès de la mise à jour du magasin "+idShop);
		} catch (SQLException e) {
			logger.error("Erreur lors de la mise à jour du magasin "+idShop+" "+e.getMessage());
		}
		
		if(res == 1){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteShop(int id) {
		String query = "DELETE FROM shop where id=?;";
		
		PreparedStatement preparedStatement;
		int res = 0;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, id);
			res = UtilsBdd.executePreapredStatement(preparedStatement);
			logger.info("Succès de la suppression du magasin "+id);
		} catch (SQLException e) {
			logger.error("Erreur lors de suppression du magasin "+id+" "+e.getMessage());
		}
		
		if(res==1){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Product> getProducts(int shopId) {
		List<Product> products = new ArrayList<Product>();

		String query = "Select * from product where shopId=?;";
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet resultat = UtilsBdd.selectPreapredStatement(preparedStatement);
			while(resultat.next()){
				Product product = null;
				int id = resultat.getInt("id");
				String productType = resultat.getString("productType");
				String name = resultat.getString("name");
				String description = resultat.getString("description");
				float price = resultat.getFloat("price");

				ProductTypeEnum productTypeEnum = Utils.getProductType(productType);
				product = new Product(id, productTypeEnum, name, description, price, shopId);
				products.add(product);
				
				logger.info("Succès - Récupération puis insertion du produit portant l'id : "+id); 
			}
			logger.info("Succès - Récupération des produits du magasin portant l'id :"+ shopId); 
		} catch (SQLException e1) {
			logger.error("Echec - Récupération des produits du magasin portant l'id :"+ shopId); 
		}
		
		return products;
	}

	@Override
	public Product getProduct(int id) {
		Product product = null;

		String query = "Select * from product where id=?;";
		PreparedStatement preparedStatement;
		
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, id, Types.INTEGER);
			ResultSet resultat = UtilsBdd.selectPreapredStatement(preparedStatement);
			while(resultat.next()){
				String productType = resultat.getString("productType");
				String name = resultat.getString("name");
				String description = resultat.getString("description");
				Float price = resultat.getFloat("price");
				int shopId = resultat.getInt("shopId");

				ProductTypeEnum productTypeEnum = Utils.getProductType(productType);
				product = new Product(id, productTypeEnum, name, description, price, shopId);
			}
			logger.info("Succès - Récupération du produit portant l'id : "+id);
		} catch (SQLException e1) {
			logger.error("Echec - Récupération du produit portant l'id : "+id);
		}

		return product; 
	}

	@Override
	public boolean createProduct(String productType, String name, String description, Float price, int idShop) {
		String query = "Insert into product (productType, name, description, price, shopId) VALUES(?,?,?,?,?);";

		PreparedStatement preparedStatement;
		int res = 0;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, productType, Types.VARCHAR);
			preparedStatement.setObject(2, name, Types.VARCHAR);
			preparedStatement.setObject(3, description, Types.VARCHAR);
			preparedStatement.setObject(4, price, Types.FLOAT);
			preparedStatement.setObject(5, idShop, Types.INTEGER);
			res = UtilsBdd.executePreapredStatement(preparedStatement);
			logger.info("Succès - Création du produit pour le magasin portant l'id : "+idShop);
		} catch (SQLException e) {
			logger.error("Echec - Création du produit pour le magasin portant l'id : "+idShop);
		}

		if(res==1){
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean updateProduct(int idProduct, String productType, String name, String description, Float price, int idShop) {
		String query = "Update product SET productType=? , name=? , description=? , price=? , shopId=? WHERE id=?;";
		
		PreparedStatement preparedStatement;
		int res = 0;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, productType, Types.VARCHAR);
			preparedStatement.setObject(2, name, Types.VARCHAR);
			preparedStatement.setObject(3, description, Types.VARCHAR);
			preparedStatement.setObject(4, price, Types.FLOAT);
			preparedStatement.setObject(5, idShop, Types.INTEGER);
			preparedStatement.setObject(6, idProduct, Types.INTEGER);
			res = UtilsBdd.executePreapredStatement(preparedStatement);
			
			logger.info("Succès - Mise à jour du produit portant l'id : "+idProduct);
		} catch (SQLException e) {
			logger.error("Echec - Mise à jour du produit portant l'id : "+idProduct);
		}
		
		if(res==1){
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public boolean deleteProduct(int id) {
		String query = "DELETE FROM product where id=?;";
		PreparedStatement preparedStatement;
		int res = 0;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, id, Types.INTEGER);
			res = UtilsBdd.executePreapredStatement(preparedStatement);
			logger.info("Succès - Suppresion du produit avec l'id : "+id);
		} catch (SQLException e) {
			logger.error("Echec - Suppresion du produit avec l'id : "+id);
		}
		
		if(res==1){
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public List<Order> getReceivedOrder(int shop) {
		String query = "Select * from ordering where shop=? AND status=?;";
		
		PreparedStatement preparedStatement;
		List<Order> orders = new ArrayList<Order>();
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, shop, Types.INTEGER);
			preparedStatement.setObject(2, OrderStatusEnum.INPROGRESS, Types.VARCHAR);
			
			ResultSet resultOrder = UtilsBdd.selectPreapredStatement(preparedStatement);
			while (resultOrder.next()) {
				int idOrder = resultOrder.getInt("id");
				String estimatedTime = resultOrder.getString("estimatedTime");
				String status = resultOrder.getString("status");
				int idUser = resultOrder.getInt("user");
				int idShop = resultOrder.getInt("shop");
				float priceTotal = resultOrder.getFloat("totalPrice");
				Date date = resultOrder.getDate("created");
				
				EstimatedTimeEnum estimatedTimeEnum = Utils.getEstimatedTime(estimatedTime);
				OrderStatusEnum orderStatusEnum = Utils.getOrderStatus(status);
				
				Order order = new Order(idOrder, estimatedTimeEnum, orderStatusEnum,date, priceTotal, idUser, idShop);
				orders.add(order);
			}
			logger.info("Succès - Récupération des commandes du magasin portant l'id : "+shop);
		} catch (SQLException e1) {
			logger.error("Echec - Récupération des commandes du magasin portant l'id : "+shop);
		}
		
		return orders;
	}

	@Override
	public int getShopId(String name, String description, String shopType, int userId, int addressId) {
		String query = "Select * from shop WHERE name=? AND description=?  AND shopType=? AND user=? AND address=?;";
		PreparedStatement preparedStatement;
		int idShop = -1;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, name, Types.VARCHAR);
			preparedStatement.setObject(2, description, Types.VARCHAR);
			preparedStatement.setObject(3, shopType, Types.VARCHAR);
			preparedStatement.setObject(4, userId, Types.INTEGER);
			preparedStatement.setObject(5, addressId, Types.INTEGER);
			ResultSet result = UtilsBdd.selectPreapredStatement(preparedStatement);
			while(result.next()){
				idShop = result.getInt("id");
			}
			logger.info("Récupération du magasin via son id : "+idShop);
		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors de la récupération du magasin : " + e.getMessage());
		}
		return idShop;
	}

	@Override
	public int getProduct(String producType, String name, String description, Float price, int idShop) {
		String query = "Select * FROM product WHERE productType=? AND name=? AND description=? AND price=? AND shopId=?;";
		
		PreparedStatement preparedStatement;
		int idProduct = -1;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, producType, Types.VARCHAR);
			preparedStatement.setObject(2, name, Types.VARCHAR);
			preparedStatement.setObject(3, description, Types.VARCHAR);
			preparedStatement.setObject(4, price, Types.FLOAT);
			preparedStatement.setObject(5, idShop, Types.INTEGER);
			ResultSet result = UtilsBdd.selectPreapredStatement(preparedStatement);
			while(result.next()){
				idProduct = result.getInt("id");
			}
			logger.info("Récupération de l'id du produit réussi");
		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors de la récupération du produit : " + e.getMessage());
		}

		return idProduct;
	}


}
