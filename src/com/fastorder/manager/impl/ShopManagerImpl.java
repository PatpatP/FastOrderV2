package com.fastorder.manager.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import com.mysql.jdbc.Statement;

public class ShopManagerImpl implements IShopManager{

	private Statement statement;

	public ShopManagerImpl(Statement statement) {
		this.statement = statement;
	}

	@Override
	public List<Shop> getShops() {
		List<Shop> shops = new ArrayList<Shop>();

		String query = "Select * from shop";
		ResultSet resultat = Utils.selectQuery(statement, query);

		try {
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return shops;
	}

	@Override
	public Shop getShop(Integer id) {
		Shop shop = null;

		String query = "Select * from shop where id='"+id+"';";
		ResultSet resultat = Utils.selectQuery(statement, query);

		try {
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return shop;
	}

	@Override
	public User getOwner(int idShop) {
		Shop shop = getShop(idShop);
		int userId = shop.getUserId();
		
		UserManagerImpl userManager = new UserManagerImpl(statement);
		User user = userManager.getUser(userId);
		return user;
	}

	@Override
	public boolean createShop(String name, String description, String shopType, int userId, int addressId) {
		String query = "Insert into shop (name, description, shopType, user, address) "
				+ "VALUES('"+name+"','"+description+"','"+shopType+"','"+userId+"','"+addressId+"');";

		int res = Utils.insertQuery(statement, query);

		if(res==1){
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void updateShop(int idShop, String name, String description, String shopType, int userId, int addressId) {
		String query = "Update shop SET name='"+name+"' AND description='"+description+"' AND shopType='"+shopType+"' AND user='"+userId+"' AND address='"+addressId+" WHERE id='"+idShop+"';";

		Utils.updateQuery(statement, query);
	}

	@Override
	public void deleteShop(int id) {
		//Get shop address id
		
		//Delete shop address
		
		
		//Delete shop
		String query = "DELETE FROM shop where id='"+id+"';";
		Utils.deleteQuery(statement, query);
	}

	@Override
	public List<Product> getProducts(int shopId) {
		List<Product> products = new ArrayList<Product>();

		String query = "Select * from product where shopId='"+shopId+"';";

		ResultSet resultat = Utils.selectQuery(statement, query);

		try {
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
			}
		} catch (SQLException e) {

		}

		return products;
	}

	@Override
	public Product getProduct(int id) {
		Product product = null;

		String query = "Select * from product where id='"+id+"';";

		ResultSet resultat = Utils.selectQuery(statement, query);

		try {
			while(resultat.next()){
				String productType = resultat.getString("productType");
				String name = resultat.getString("name");
				String description = resultat.getString("description");
				Float price = resultat.getFloat("price");
				int shopId = resultat.getInt("shopId");

				ProductTypeEnum productTypeEnum = Utils.getProductType(productType);
				product = new Product(id, productTypeEnum, name, description, price, shopId);

			}
		} catch (SQLException e) {

		}

		return product; 
	}

	@Override
	public boolean createProduct(String productType, String name, String description, Float price, int idShop) {
		String query = "Insert into product (productType, name, description, price, shopId) "
				+ "VALUES('"+productType.toString()+"','"+name+"','"+description+"','"+price+"','"+idShop+"');";

		int res = Utils.insertQuery(statement, query);

		if(res==1){
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void updateProduct(int idProduct, String productType, String name, String description, Float price, int idShop) {
		String query = "Update shop SET productType='"+productType.toString()+"' AND name='"+name+"' AND description='"+description+"' AND price='"+price+"' AND idShop='"+idShop+" WHERE id='"+idProduct+"';";

		Utils.updateQuery(statement, query);
	}

	@Override
	public boolean deleteProduct(int id) {
		String query = "DELETE FROM product where id='"+id+"';";
		System.out.println(query);
		int res = Utils.deleteQuery(statement, query);
		if(res==1){
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public List<Order> getReceivedOrder(int shop) {
		String query = "Select * from ordering where shop='"+shop+"' AND status='"+OrderStatusEnum.INPROGRESS+"';";
		ResultSet resultOrder = Utils.selectQuery(statement, query);

		List<Order> orders = new ArrayList<Order>();
		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return orders;
	}

	@Override
	public int getShop(String name, String description, String shopType, int userId, int addressId) {
		String query = "Select * from shop WHERE name='"+name+"' AND description='"+description+"' AND user='"+userId+"' AND address='"+addressId+"';";
		ResultSet result = Utils.selectQuery(statement, query);
		int idShop = -1;
		try {
			while(result.next()){
				idShop = result.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return idShop;
	}

	@Override
	public int getProduct(String producType, String name, String description, Float price, int idShop) {
//		String query = "Select * FROM product WHERE productType='"+producType+"' AND name='"+name+"' AND description='"+description+"' AND CAST(price AS DECIMAL)= CAST('"+price+"' AS DECIMAL) AND shopId='"+idShop+"';";
		String query = "Select * FROM product WHERE productType='"+producType+"' AND name='"+name+"' AND description='"+description+"' AND price='"+price+"' AND shopId='"+idShop+"';";
		
		System.out.println(query);
		ResultSet result = Utils.selectQuery(statement, query);
		int idProduct = -1;
		try {
			while(result.next()){
				idProduct = result.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return idProduct;
	}


}
