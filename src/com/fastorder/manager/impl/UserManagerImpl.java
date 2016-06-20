package com.fastorder.manager.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fastorder.enumeration.EstimatedTimeEnum;
import com.fastorder.enumeration.OrderStatusEnum;
import com.fastorder.enumeration.ShopTypeEnum;
import com.fastorder.enumeration.UserTypeEnum;
import com.fastorder.manager.IUserManager;
import com.fastorder.model.Address;
import com.fastorder.model.Order;
import com.fastorder.model.Product;
import com.fastorder.model.Shop;
import com.fastorder.model.User;
import com.fastorder.utils.Utils;
import com.mysql.jdbc.Statement;


public class UserManagerImpl implements IUserManager{

	private Statement statement;

	public UserManagerImpl(Statement statement) {
		this.statement = statement;
	}

	@Override
	public List<User> getUsers() {
		String query = "Select * from user";
		ResultSet resultat = Utils.selectQuery(statement, query);
		List<User> users = new ArrayList<User>();
		try {
			while(resultat.next()){
				String mail = resultat.getString("mail");
				String phoneNumber = resultat.getString("phoneNumber");
				String firstName = resultat.getString("firstName");
				String lastName = resultat.getString("lastName");
				String userType = resultat.getString("userType");
				int address = resultat.getInt("address");
				String password = resultat.getString("password");
				Date created = resultat.getDate("created");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public User getUser(String mail) {
		User user = null;
		String query = "Select * from user where mail='"+mail+"';";
		ResultSet resultat = Utils.selectQuery(statement, query);
		try {
			while(resultat.next()){
				int id = resultat.getInt("id");
				String phoneNumber = resultat.getString("phoneNumber");
				String firstName = resultat.getString("firstName");
				String lastName = resultat.getString("lastName");
				String userType = resultat.getString("userType");
				int addressId = resultat.getInt("address");
				String password = resultat.getString("password");
				Date created = resultat.getDate("created");

				if(userType.equals("Client")){
					user = new User(id, mail, phoneNumber, firstName, lastName, UserTypeEnum.CLIENT, addressId, password, created);
				} else if (userType.equals("Merchant")){
					user = new User(id, mail, phoneNumber, firstName, lastName, UserTypeEnum.MERCHANT, addressId, password, created);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				resultat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return user;
	}

	@Override
	public User getUser(int id) {
		User user = null;
		String query = "Select * from user where id='"+id+"';";
		ResultSet resultat = Utils.selectQuery(statement, query);
		try {
			while(resultat.next()){
				String mail = resultat.getString("mail");
				String phoneNumber = resultat.getString("phoneNumber");
				String firstName = resultat.getString("firstName");
				String lastName = resultat.getString("lastName");
				String userType = resultat.getString("userType");
				int addressId = resultat.getInt("address");
				String password = resultat.getString("password");
				Date created = resultat.getDate("created");

				if(userType.equals("Client")){
					user = new User(id, mail, phoneNumber, firstName, lastName, UserTypeEnum.CLIENT, addressId, password, created);
				} else if (userType.equals("Merchant")){
					user = new User(id, mail, phoneNumber, firstName, lastName, UserTypeEnum.MERCHANT, addressId, password, created);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public List<Shop> getUserShops(String mail) {
		List<Shop> shops = new ArrayList<Shop>();
		User user = getUser(mail);

		try {
			int idUser = user.getId();

			String queryShop = "Select * from shop where user="+idUser;
			
			ResultSet resultShop = Utils.selectQuery(statement, queryShop);

			while (resultShop.next()) {
				int shopId = resultShop.getInt("id");
				String name = resultShop.getString("name");
				String description = resultShop.getString("description");
				String shopType = resultShop.getString("shopType");
				int userId = resultShop.getInt("user");
				int addressId = resultShop.getInt("address");
				
				ShopTypeEnum shopTypeEnum = Utils.getShopType(shopType);
				Shop shop = new Shop(shopId, name, description, shopTypeEnum, userId, addressId);
				
				shops.add(shop);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return shops;
	}

	@Override
	public boolean createUser(String mail, String phoneNumber, String firstName, String lastName, UserTypeEnum userType,
			int address, String password) {
		String userTypeString = userType.toString();

		String query = "Insert into user (mail, phoneNumber, firstName, lastName, userType, address, password) "
				+ "VALUES('"+mail+"','"+phoneNumber+"','"+firstName+"','"+lastName+"','"+userTypeString+"','"+address+"','"+password+"');";
		System.out.println(query);
		int res = Utils.insertQuery(statement, query);
		Utils.selectQuery(statement, "Select * from user");

		if(res==1){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateUserData(String mail, String phoneNumber, String firstName, String lastName,
			UserTypeEnum userType, int address, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(String mail) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkLogin(String mail, String password) {

		String query = "Select * from user where mail='"+mail+"';";
		ResultSet resultat = Utils.selectQuery(statement, query);
		String passwordExpected = "";
		try {
			while (resultat.next()){
				passwordExpected = resultat.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(password.equals(passwordExpected)){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean forgotPassword(String mail) {
		return false;
	}

	@Override
	public boolean setNewPassword(String password) {
		return false;
	}

	@Override
	public boolean checkMailExist(String mail) {
		String query = "Select * from user";
		String userMail;
		ResultSet resultat = Utils.selectQuery(statement, query);
		try {
			while(resultat.next()){
				userMail = resultat.getString("mail");
				if(mail.equals(userMail)){
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Order> getUserOrders(int idUser) {
		List<Order> userOrders = new ArrayList<Order>();
		
		String query = "Select * from ordering where user="+idUser;
		ResultSet result = Utils.selectQuery(statement, query);   // COntient l'ensemble des commandes de l'utilisateur
		
		try {
			while(result.next()){
				int idOrder = result.getInt("id");
				String estimatedTime = result.getString("estimatedTime");
				String status = result.getString("status");
				int idShop = result.getInt("shop");
				Date created = result.getDate("created");
				float priceTotal = result.getFloat("totalPrice");
				
				EstimatedTimeEnum estimatedTimeEnum = Utils.getEstimatedTime(estimatedTime);
				OrderStatusEnum orderStatusEnum = Utils.getOrderStatus(status);
				
				Order order = new Order(idOrder, estimatedTimeEnum, orderStatusEnum, created, priceTotal, idUser, idShop);
				userOrders.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userOrders;
	}
}
