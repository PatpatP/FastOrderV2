package com.fastorder.manager.tests;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fastorder.enumeration.UserTypeEnum;
import com.fastorder.manager.impl.UserManagerImpl;
import com.fastorder.model.Order;
import com.fastorder.model.Shop;
import com.fastorder.model.User;
import com.fastorder.utils.Utils;
import com.mysql.jdbc.Statement;

public class UserManagerTests {
	
	UserManagerImpl userManager;
	Statement statement;
	
	@Before
	public void setUp(){
		statement =  Utils.connectBDD();
		userManager = new UserManagerImpl(statement);
	}
	
	@Test
	public void getUserByMailTest(){
		User user = userManager.getUser("pol.patrick1411@gmail.com");
		assertEquals("test le mail", "pol.patrick1411@gmail.com", user.getMail());
		assertEquals("test le prénom", "Patrick", user.getFirstName());
		assertEquals("test le nom", "POL", user.getLastName());
		assertEquals("test le numéro de tel", "669468518", user.getPhoneNumber());
		assertEquals("test le type d'utilisateur", "Merchant", user.getUserType().toString());
	}
	
	@Test
	public void getUserByIdTest(){
		User user = userManager.getUser("pol.patrick1411@gmail.com");
		assertEquals("test le mail", "pol.patrick1411@gmail.com", user.getMail());
		assertEquals("test le prénom", "Patrick", user.getFirstName());
		assertEquals("test le nom", "POL", user.getLastName());
		assertEquals("test le numéro de tel", "669468518", user.getPhoneNumber());
		assertEquals("test le type d'utilisateur", "Merchant", user.getUserType().toString());
	}
	
	@Test
	public void getUserShopsTest(){
		List<Shop> shops = userManager.getUserShops("pol.patrick1411@gmail.com");
		assertTrue("Vérifie que l'utilisateur a bien deux magasins", shops.size()==2);
	}
	
	@Test
	public void getUsersOrderTest(){
		List<Order> orders = userManager.getUserOrders(1);
		assertTrue("Vérifie que l'utilisateur a bien effectué 3 commandes", orders.size()==3);
	}
	
	@Test
	public void createUserTest() throws SQLException{
		
		String queryBefore = "Select * from User";
		ResultSet resultBefore = Utils.selectQuery(statement, queryBefore);
		int cptBefore = 0;
		while(resultBefore.next()){
			cptBefore = cptBefore+1;
		}
		
		String mail = "testUnit@gmail.com";
		String phoneNumber = "0102030405";
		String firstName = "test";
		String lastName = "unit";
		UserTypeEnum userType = UserTypeEnum.CLIENT;
		int address = 0;
		String password = "test1234";
		userManager.createUser(mail, phoneNumber, firstName, lastName, userType, address, password);
		
		String queryAfter = "Select * from user";
		ResultSet resultAfter = Utils.selectQuery(statement, queryAfter);
		int cptAfter = 0;
		while(resultAfter.next()){
			cptAfter = cptAfter+1;
		}
		
		System.out.println(cptBefore + "    "+cptAfter);
		
//		String queryDelete = "DELETE FROM USER WHERE mail='"+mail+"';";
//		Utils.deleteQuery(statement, queryDelete);
		
	}
	
	@Test
	public void updateUserTest(){
		
	}
	
	@Test
	public void deleteUserTest(){
		
	}
	
	@Test
	public void checkLoginTest(){
		String user1Mail = "pol.patrick1411@gmail.com";
		String user1Password = "bo49cobo";
		String user2Mail = "ghansumn@gmail.com";
		String user2FakePassword = "fakePassword";
		
		Boolean checkLoginUser1 = userManager.checkLogin(user1Mail, user1Password);
		Boolean checkLoginUser2 = userManager.checkLogin(user2Mail, user2FakePassword);
		
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

}
