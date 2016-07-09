package com.fastorder.manager.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.fastorder.enumeration.EstimatedTimeEnum;
import com.fastorder.enumeration.OrderStatusEnum;
import com.fastorder.enumeration.ShopTypeEnum;
import com.fastorder.enumeration.UserTypeEnum;
import com.fastorder.manager.IUserManager;
import com.fastorder.model.Order;
import com.fastorder.model.Shop;
import com.fastorder.model.User;
import com.fastorder.utils.Utils;
import com.fastorder.utils.UtilsBdd;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;


public class UserManagerImpl implements IUserManager{

	private Connection connection;

	final static Logger logger = Logger.getLogger(UserManagerImpl.class);

	public UserManagerImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<User> getUsers() {
		String query = "Select * from user";
//		ResultSet resultat = UtilsBdd.selectQuery(statement, query);
//		List<User> users = new ArrayList<User>();
//		try {
//			while(resultat.next()){
//				String mail = resultat.getString("mail");
//				String phoneNumber = resultat.getString("phoneNumber");
//				String firstName = resultat.getString("firstName");
//				String lastName = resultat.getString("lastName");
//				String userType = resultat.getString("userType");
//				int address = resultat.getInt("address");
//				String password = resultat.getString("password");
//				Date created = resultat.getDate("created");
//			}
//			
//			logger.info("Liste des utilisateurs rÃ©cupÃ©rÃ©e");
//		} catch (SQLException e) {
//			logger.error("Une erreur est survenue lors de la rÃ©cupÃ©ration de la liste des utilisateurs : " + e.getMessage());
//		}

		return null;
	}

	@Override
	public User getUser(String mail) {
		User user = null;
		
		String query = "Select * from user where mail=?;";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, mail, Types.VARCHAR);
			
			ResultSet resultat = UtilsBdd.selectPreapredStatement(preparedStatement);
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
			logger.info("Succès - Récupération de l'utilisateur avec le mail : "+mail);
		} catch (SQLException e) {
			logger.error("Echec - Récupération de l'utilisateur avec le mail : "+mail);
		}

		return user;
	}

	@Override
	public User getUser(int id) {
		User user = null;
		
		String query = "Select * from user where id=?;";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, id, Types.INTEGER);
			
			ResultSet resultat = UtilsBdd.selectPreapredStatement(preparedStatement);
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
			logger.info("Succès - Récupération de l'utilisateur avec l'id : "+id);
		} catch (SQLException e) {
			logger.error("Echec - Récupération de l'utilisateur avec l'id : "+id);
		}
		return user;
	}

	@Override
	public List<Shop> getUserShops(String mail) {
		List<Shop> shops = new ArrayList<Shop>();
		User user = getUser(mail);

		try {
			int idUser = user.getId();

			String queryShop = "Select * from shop where user=?";
			PreparedStatement preparedStatement;
			preparedStatement = (PreparedStatement) connection.prepareStatement(queryShop);
			preparedStatement.setObject(1, idUser, Types.INTEGER);
			ResultSet resultShop = UtilsBdd.selectPreapredStatement(preparedStatement);

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
				logger.info("Succès - Ajout du magasin ayant pour id : "+shopId);
			}
			
			logger.info("Succès - Récupération des magasins de l'utilisateur ayant pour mail : "+mail);

		} catch (SQLException e) {
			logger.error("Echec - Récupération des magasins de l'utilisateur ayant pour mail : "+mail);
		}

		return shops;
	}

	@Override
	public boolean createUser(String mail, String phoneNumber, String firstName, String lastName, UserTypeEnum userType,
			int address, String password) {
		
		String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());

		String query = "Insert into user (mail, phoneNumber, firstName, lastName, userType, address, password) VALUES(?,?,?,?,?,?,?);";
		
		PreparedStatement preparedStatement;
		int res=0;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, mail, Types.VARCHAR);
			preparedStatement.setObject(2, phoneNumber, Types.VARCHAR);
			preparedStatement.setObject(3, firstName, Types.VARCHAR);
			preparedStatement.setObject(4, lastName, Types.VARCHAR);
			preparedStatement.setObject(5, userType, Types.VARCHAR);
			preparedStatement.setObject(6, address, Types.INTEGER);
			preparedStatement.setObject(7, hashPassword, Types.VARCHAR);
			res = UtilsBdd.executePreapredStatement(preparedStatement);
			logger.info("Succès - Création d'un utilisateir");
		} catch (SQLException e) {
			logger.error("Echec - Création d'un utilisateir");
		}
		
		if(res==1){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateUserData(int userId, String mail, String phoneNumber, String firstName, String lastName,
			UserTypeEnum userType, int address, String password) {

		String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		
		String query = "UPDATE user SET mail=?, phoneNumber=?  , firstName=? , lastName=? , password=? WHERE id=?;";
		PreparedStatement preparedStatement;
		int res = 0;
		
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, mail, Types.VARCHAR);
			preparedStatement.setObject(2, phoneNumber, Types.VARCHAR);
			preparedStatement.setObject(3, firstName, Types.VARCHAR);
			preparedStatement.setObject(4, lastName, Types.VARCHAR);
			preparedStatement.setObject(5, hashPassword, Types.VARCHAR);
			preparedStatement.setObject(6, userId, Types.INTEGER);
			
			res = UtilsBdd.executePreapredStatement(preparedStatement);
			
			logger.info("Succès - Mise à jour de l'utilisateur portant l'id : "+userId);
		} catch (SQLException e) {
			logger.error("Echec - Mise à jour de l'utilisateur portant l'id : "+userId);
		}
		
		if(res==1){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteUser(String mail) {
		String query = "Delete FROM user where mail=?;";
		
		PreparedStatement preparedStatement;
		int res=0;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, mail, Types.VARCHAR);
			res = UtilsBdd.executePreapredStatement(preparedStatement);
			logger.info("Succès - Suppression de l'utilisateur portant le mail : "+mail);
		} catch (SQLException e) {
			logger.error("Error - Suppression de l'utilisateur portant le mail : "+mail);
		}
		
		if(res==1){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean checkLogin(String mail, String password) {

		String query = "Select * from user where mail=?";
		PreparedStatement preparedStatement;
		String passwordExpected = "";
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, mail, Types.VARCHAR);
			
			ResultSet resultat = UtilsBdd.selectPreapredStatement(preparedStatement);
			while (resultat.next()){
				passwordExpected = resultat.getString("password");
			}
			logger.info("Succès - Récupération du mot de passe pour la vérification de l'authentification");
		} catch (SQLException e) {
			logger.error("Echec - Récupération du mot de passe pour la vérification de l'authentification");
		}
		return BCrypt.checkpw(password, passwordExpected);
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
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet resultat = UtilsBdd.selectPreapredStatement(preparedStatement);
			while(resultat.next()){
				userMail = resultat.getString("mail");
				if(mail.equals(userMail)){
					return true;
				}
			}
			
			logger.info("Succès - Vérification si le mail de l'utilisateur existe");
		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors de la vÃ©rification de l'existance du mail : " + e.getMessage());
		}
		
		return false;
	}

	@Override
	public List<Order> getUserOrders(int idUser) {
		List<Order> userOrders = new ArrayList<Order>();
		
		String query = "Select * from ordering where user=?";
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, idUser, Types.INTEGER);
			ResultSet result = UtilsBdd.selectPreapredStatement(preparedStatement);
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
			logger.info("Succès - Récupération des commandes de l'utilisateur ayant pour id : "+idUser);
		} catch (SQLException e) {
			logger.error("Echec - Récupération des commandes de l'utilisateur ayant pour id : "+idUser);
		}
		
		return userOrders;
	}
}
