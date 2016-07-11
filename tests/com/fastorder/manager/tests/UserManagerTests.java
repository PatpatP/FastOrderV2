package com.fastorder.manager.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.fastorder.enumeration.UserTypeEnum;
import com.fastorder.manager.impl.AddressManagerImpl;
import com.fastorder.manager.impl.UserManagerImpl;
import com.fastorder.model.Order;
import com.fastorder.model.Shop;
import com.fastorder.model.User;
import com.fastorder.utils.UtilsBdd;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class UserManagerTests {
	
	private UserManagerImpl userManager;
	private AddressManagerImpl addressManagerImpl;
	private Connection connection;
	private int addressId;
	private String mail = "testUnit@gmail.com";
	private String phoneNumber = "0102030405";
	private String firstName = "test";
	private String lastName = "unit";
	private UserTypeEnum userType = UserTypeEnum.CLIENT;
	private String mdp = "test1234";
	
	@Before
	public void setUp(){
		connection =  UtilsBdd.connectBDD();
		userManager = new UserManagerImpl(connection);
		
		mail = "testUnit@gmail.com";
		phoneNumber = "0102030405";
		firstName = "test";
		lastName = "unit";
		userType = UserTypeEnum.CLIENT;
		mdp = "test1234";
		
		addressManagerImpl = new AddressManagerImpl(connection);
		
		addressManagerImpl.createAddress("rue du test", "42", "75000", "Paris", "France");
		addressId = addressManagerImpl.getAddressId("rue du test", "42", "75000", "Paris", "France");
	}
	
	@After
	public void clear(){
		addressManagerImpl.deleteAddress(addressId);
	}
	
	@Test
	public void getUserByMailTest(){
		User user = userManager.getUser("pol.patrick1411@gmail.com");
		assertEquals("test le mail", "pol.patrick1411@gmail.com", user.getMail());
		assertEquals("test le prénom", "POL", user.getFirstName());
		assertEquals("test le nom", "Patrick", user.getLastName());
		assertEquals("test le numéro de tel", "0102030405", user.getPhoneNumber());
		assertEquals("test le type d'utilisateur", "Merchant", user.getUserType().toString());
	}
	
	@Test
	public void getUserByIdTest(){
		User user = userManager.getUser("pol.patrick1411@gmail.com");
		assertEquals("test le mail", "pol.patrick1411@gmail.com", user.getMail());
		assertEquals("test le prénom", "POL", user.getFirstName());
		assertEquals("test le nom", "Patrick", user.getLastName());
		assertEquals("test le numéro de tel", "0102030405", user.getPhoneNumber());
		assertEquals("test le type d'utilisateur", "Merchant", user.getUserType().toString());
	}
	
	@Test
	@Ignore
	public void getUserShopsTest(){
		List<Shop> shops = userManager.getUserShops("pol.patrick1411@gmail.com");
		assertTrue("Vérifie que l'utilisateur a bien deux magasins", shops.size()==2);
	}
	
	@Test
	@Ignore
	public void getUsersOrderTest(){
		List<Order> orders = userManager.getUserOrders(1);
		assertTrue("Vérifie que l'utilisateur a bien effectué 8 commandes", orders.size()==8);
	}
	
	@Test
	public void createUserTest() throws SQLException{
		
		int userBeforeInsert = countNbUsersInBase();
		
		userManager.createUser(mail, phoneNumber, firstName, lastName, userType, addressId, mdp);
		
		int cptUsersAfterInsert = countNbUsersInBase();
		assertTrue("Compare le nombre d'utilisateur avant et après", cptUsersAfterInsert==userBeforeInsert+1);
		
		User user = userManager.getUser(mail);
		assertEquals("test le mail", mail, user.getMail());
		assertEquals("test le prénom", firstName, user.getFirstName());
		assertEquals("test le nom", lastName, user.getLastName());
		assertEquals("test le numéro de tel", phoneNumber, user.getPhoneNumber());
		assertEquals("test le type d'utilisateur", userType, user.getUserType());
		assertTrue("Le mot de passe a bien été crypté", BCrypt.checkpw(mdp, BCrypt.hashpw(mdp, BCrypt.gensalt())));
	}
	
	@Test
	public void updateUserTest(){
		userManager.createUser(mail, phoneNumber, firstName, lastName, userType, addressId, mdp);
		User user = userManager.getUser(mail);
		String newFirstName = "Change FirstName test";
		String newLastName= "Change LastName test";
		
		userManager.updateUserData(user.getId(), mail, phoneNumber, newFirstName, newLastName, userType, addressId, mdp);
		
		User userAfterUpdate = userManager.getUser(mail);
		assertEquals("test le mail", mail, userAfterUpdate.getMail());
		assertEquals("test le prénom", newFirstName, userAfterUpdate.getFirstName());
		assertEquals("test le nom", newLastName, userAfterUpdate.getLastName());
		assertEquals("test le numéro de tel", phoneNumber, userAfterUpdate.getPhoneNumber());
		assertEquals("test le type d'utilisateur", userType, userAfterUpdate.getUserType());
		assertTrue("Le mot de passe a bien été crypté", BCrypt.checkpw(mdp, BCrypt.hashpw(mdp, BCrypt.gensalt())));
		
	}
	
	@Test
	public void deleteUserTest(){
		assertTrue("create user", userManager.createUser(mail, phoneNumber, firstName, lastName, userType, addressId, mdp));
		assertTrue("delete user", userManager.deleteUser(mail));
	}
	
	@Test
	public void checkLoginTest(){
		String user1Mail = "pol.patrick1411@gmail.com";
		String user1Mdp = "test1234";
		String user2Mail = "ghansumn@gmail.com";
		String user2FakeMdp = "fakePassword";
		
		Boolean checkLoginUser1 = userManager.checkLogin(user1Mail, user1Mdp);
		Boolean checkLoginUser2 = userManager.checkLogin(user2Mail, user2FakeMdp);
		
		assertTrue("Bon identifiant et mot de passe", checkLoginUser1);
		assertFalse("Bon identifiant et mauvais mot de passe", checkLoginUser2);
	}
	
	@Test
	public void checkMailExistTest(){
		String mail = "pol.patrick1411@gmail.com";
		String mail2 = "testUnit@gmail.com";
		
		Boolean existingMail = userManager.checkMailExist(mail);
		Boolean notExistringMail = userManager.checkMailExist(mail2);
		
		assertTrue("Le mail existe", existingMail);
		assertFalse("Le mail n'existe pas", notExistringMail);
	}
	
	private int countNbUsersInBase() throws SQLException{
		String query = "Select * from user";
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet result = UtilsBdd.selectPreapredStatement(preparedStatement);
		int cpt = 0;
		while(result.next()){
			cpt = cpt+1;
		}
		
		return cpt;
	}

}
