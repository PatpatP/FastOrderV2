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


public class UserManagerImpl implements IUserManager{

	private Connection connection;

	final static Logger logger = Logger.getLogger(UserManagerImpl.class);

	public UserManagerImpl(Connection connection) {
		this.connection = connection;
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
			user = createUserFromResult(resultat);
			logger.info("Succès - Création de la requête préparée");
		} catch (SQLException e) {
			logger.error("Echec - Création de la requête préparée");
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
			user = createUserFromResult(resultat);
			logger.info("Succès - Création de la requête préparée");
		} catch (SQLException e) {
			logger.error("Echec - Création de la requête préparée");
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
				//TODO
				Shop shop = new Shop(shopId, name, description, shopTypeEnum, userId, addressId, null);

				shops.add(shop);
				logger.info("Succès - Ajout du magasin ayant pour id : "+shopId);
			}

			logger.info("Succès - RÃ©cupÃ©ration des magasins de l'utilisateur ayant pour mail : "+mail);

		} catch (SQLException e) {
			logger.error("Echec - RÃ©cupÃ©ration des magasins de l'utilisateur ayant pour mail : "+mail);
		}

		return shops;
	}

	@Override
	public boolean createUser(String mail, String phoneNumber, String firstName, String lastName, UserTypeEnum userType,
			int address, String mdp) {

		String hashmdp = BCrypt.hashpw(mdp, BCrypt.gensalt());

		String query = "Insert into user (mail, phoneNumber, firstName, lastName, userType, address, mdp) VALUES(?,?,?,?,?,?,?);";

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
			preparedStatement.setObject(7, hashmdp, Types.VARCHAR);
			res = UtilsBdd.executePreapredStatement(preparedStatement);
			logger.info("SuccÃ¨s - CrÃ©ation d'un utilisateir");
		} catch (SQLException e) {
			logger.error("Echec - CrÃ©ation d'un utilisateir");
		}

		if(res==1){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateUserData(int userId, String mail, String phoneNumber, String firstName, String lastName,
			UserTypeEnum userType, int address, String mdp) {

		String hashPassword = BCrypt.hashpw(mdp, BCrypt.gensalt());

		String query = "UPDATE user SET mail=?, phoneNumber=?  , firstName=? , lastName=? , userType=?,  mdp=? WHERE id=?;";
		PreparedStatement preparedStatement;
		int res = 0;

		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, mail, Types.VARCHAR);
			preparedStatement.setObject(2, phoneNumber, Types.VARCHAR);
			preparedStatement.setObject(3, firstName, Types.VARCHAR);
			preparedStatement.setObject(4, lastName, Types.VARCHAR);
			preparedStatement.setObject(5, userType, Types.VARCHAR);
			preparedStatement.setObject(6, hashPassword, Types.VARCHAR);
			preparedStatement.setObject(7, userId, Types.INTEGER);

			res = UtilsBdd.executePreapredStatement(preparedStatement);

			logger.info("Succès - Mise Ã  jour de l'utilisateur portant l'id : "+userId);
		} catch (SQLException e) {
			logger.error("Echec - Mise Ã  jour de l'utilisateur portant l'id : "+userId);
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
			logger.info("SuccÃ¨s - Suppression de l'utilisateur portant le mail : "+mail);
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
	public boolean checkLogin(String mail, String mdp) {

		String query = "Select * from user where mail=?";
		PreparedStatement preparedStatement;
		String mdpExpected = "";
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, mail, Types.VARCHAR);

			ResultSet resultat = UtilsBdd.selectPreapredStatement(preparedStatement);
			while (resultat.next()){
				mdpExpected = resultat.getString("mdp");
			}
			logger.info("SuccÃ¨s - RÃ©cupÃ©ration du mot de passe pour la vÃ©rification de l'authentification");
		} catch (SQLException e) {
			logger.error("Echec - RÃ©cupÃ©ration du mot de passe pour la vÃ©rification de l'authentification");
		}
		return BCrypt.checkpw(mdp, mdpExpected);
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

			logger.info("SuccÃ¨s - VÃ©rification si le mail de l'utilisateur existe");
		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors de la vÃƒÂ©rification de l'existance du mail : " + e.getMessage());
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
			logger.info("SuccÃ¨s - RÃ©cupÃ©ration des commandes de l'utilisateur ayant pour id : "+idUser);
		} catch (SQLException e) {
			logger.error("Echec - RÃ©cupÃ©ration des commandes de l'utilisateur ayant pour id : "+idUser);
		}

		return userOrders;
	}

	private User createUserFromResult(ResultSet result){
		User user = null;

		if(result!=null){
			try {
				while(result.next()){
					int id = result.getInt("id");
					String mail = result.getString("mail");
					String phoneNumber = result.getString("phoneNumber");
					String firstName = result.getString("firstName");  
					String lastName = result.getString("lastName");
					String userType = result.getString("userType");
					int addressId = result.getInt("address");
					String mdp = result.getString("mdp");
					Date created = result.getDate("created");

					if(userType.equals("Client")){
						user = new User(id, mail, phoneNumber, firstName, lastName, UserTypeEnum.CLIENT, addressId, mdp, created);
					} else if (userType.equals("Merchant")){
						user = new User(id, mail, phoneNumber, firstName, lastName, UserTypeEnum.MERCHANT, addressId, mdp, created);
					}
				}
				logger.info("Succès - Création de l'utilisateur");
			} catch (SQLException e) {
				logger.error("Echec - Création de l'utilisateur");
			}
		}

		return user;
	}
}
