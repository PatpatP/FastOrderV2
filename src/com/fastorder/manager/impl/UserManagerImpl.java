package com.fastorder.manager.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.mysql.jdbc.Statement;


public class UserManagerImpl implements IUserManager{

	private Statement statement;

	final static Logger logger = Logger.getLogger(UserManagerImpl.class);

	public UserManagerImpl(Statement statement) {
		this.statement = statement;
	}

	@Override
	public List<User> getUsers() {
		String query = "Select * from user";
		ResultSet resultat = UtilsBdd.selectQuery(statement, query);
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
			
			logger.info("Liste des utilisateurs récupérée");
		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors de la récupération de la liste des utilisateurs : " + e.getMessage());
		}

		return null;
	}

	@Override
	public User getUser(String mail) {
		User user = null;
		String query = "Select * from user where mail='"+mail+"';";
		//TODO Mettre en place la méthode avec la Map<clé, valeur>
		ResultSet resultat = UtilsBdd.selectQuery(statement, query);
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
			
			logger.info("Utilisateur récupéré grâce au mail");
		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors de la récupération de l'utilisateur : " + e.getMessage());
		} finally {
			try {
				resultat.close();
			} catch (SQLException e) {
				logger.error("Echec fermeture resultset : " + e.getMessage());
			}
		}

		return user;
	}

	@Override
	public User getUser(int id) {
		User user = null;
		String query = "Select * from user where id='"+id+"';";
		ResultSet resultat = UtilsBdd.selectQuery(statement, query);
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
			logger.info("Utilisateur récupéré grâce à l'ID");
		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors de la récupération de l'utilisateur grâce à l'ID : " + e.getMessage());
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
			
			ResultSet resultShop = UtilsBdd.selectQuery(statement, queryShop);

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
			
			logger.info("Liste des utilisateurs par magasin récupérée grâce au mail");

		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors de la récupération de la liste des utilisateurs par magasin récupérée grâce au mail : " + e.getMessage());
		}

		return shops;
	}

	@Override
	public boolean createUser(String mail, String phoneNumber, String firstName, String lastName, UserTypeEnum userType,
			int address, String password) {
		String userTypeString = userType.toString();
		
		String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());

		String query = "Insert into user (mail, phoneNumber, firstName, lastName, userType, address, password) "
				+ "VALUES('"+mail+"','"+phoneNumber+"','"+firstName+"','"+lastName+"','"+userTypeString+"','"+address+"','"+hashPassword+"');";
		System.out.println(query);
		int res = UtilsBdd.insertQuery(statement, query);
		UtilsBdd.selectQuery(statement, "Select * from user");

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
		
		String query = "UPDATE user SET mail='"+mail+"', phoneNumber='"+phoneNumber+"' , firstName='"+firstName+"' , lastName='"+lastName+"' , password='"+hashPassword+"' WHERE id='"+userId+"';";
		int res = UtilsBdd.updateQuery(statement, query);
		if(res==1){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteUser(String mail) {
		String query = "Delete FROM user where mail='"+mail+"';";
		int res = UtilsBdd.deleteQuery(statement, query);
		if(res==1){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean checkLogin(String mail, String password) {

		String query = "Select * from user where mail='"+mail+"';";
		ResultSet resultat = UtilsBdd.selectQuery(statement, query);
		String passwordExpected = "";
		try {
			while (resultat.next()){
				passwordExpected = resultat.getString("password");
			}
			logger.info("Verification du login effectuée");
		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors de la vérification du login : " + e.getMessage());
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
		ResultSet resultat = UtilsBdd.selectQuery(statement, query);
		try {
			while(resultat.next()){
				userMail = resultat.getString("mail");
				if(mail.equals(userMail)){
					return true;
				}
			}
			
			logger.info("Vérification de l'existance du mail effectuée");
		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors de la vérification de l'existance du mail : " + e.getMessage());
		}
		return false;
	}

	@Override
	public List<Order> getUserOrders(int idUser) {
		List<Order> userOrders = new ArrayList<Order>();
		
		String query = "Select * from ordering where user="+idUser;
		ResultSet result = UtilsBdd.selectQuery(statement, query);   // COntient l'ensemble des commandes de l'utilisateur
		
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
			logger.info("Liste des commandes d'un utilisateur grâce à son ID récupérée");
		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors de la récupération de la liste des commandes d'un utilisateur grâce à son ID : " + e.getMessage());
		}
		
		return userOrders;
	}
}
