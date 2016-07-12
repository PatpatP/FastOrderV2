package com.fastorder.utils.tests;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.fastorder.enumeration.UserTypeEnum;
import com.fastorder.manager.impl.AddressManagerImpl;
import com.fastorder.manager.impl.UserManagerImpl;
import com.fastorder.utils.Utils;
import com.fastorder.utils.UtilsBdd;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class BddTests {
	
	private Connection connection;
	private UserManagerImpl userManager;
	private AddressManagerImpl addressManager;
	
	@Before
	public void setUp(){
		connection = UtilsBdd.connectBDD();
		userManager = new UserManagerImpl(connection);
		addressManager = new AddressManagerImpl(connection);
	}
	
	@Test
	public void testConnection(){
		assertNotNull("The connection should not be null", connection);
	}
	
	@Test
	public void testUtilsBdd() throws SQLException{
		
		//Insertion d'un utilisateur
		addressManager.createAddress("Street Test", "Number Test", "Zip Code Test", "City Test", "Country Test");
		int addressId = addressManager.getAddressId("Street Test", "Number Test", "Zip Code Test", "City Test", "Country Test");
		String query = "Insert into user (mail, phoneNumber, firstName, lastName, userType, address, mdp) VALUES(?,?,?,?,?,?,?);";
		String mail = "test@gmail.com";
		String phoneNumber = "0102030405";
		String firstName = "test";
		String lastName = "test";
		String userType = UserTypeEnum.CLIENT.toString();
		String hashmdp = BCrypt.hashpw("test1234", BCrypt.gensalt());
		
		PreparedStatement preparedStatement;
		
		preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		preparedStatement.setObject(1, mail, Types.VARCHAR);
		preparedStatement.setObject(2, phoneNumber, Types.VARCHAR);
		preparedStatement.setObject(3, firstName, Types.VARCHAR);
		preparedStatement.setObject(4, lastName, Types.VARCHAR);
		preparedStatement.setObject(5, userType, Types.VARCHAR);
		preparedStatement.setObject(6, addressId, Types.INTEGER);
		preparedStatement.setObject(7, hashmdp, Types.VARCHAR);
		
		int res = UtilsBdd.executePreapredStatement(preparedStatement);
		assertTrue("Vérifie que l'insertion a réussi", res==1);
		
		//Récupére l'utilisateur
		String querySelect = "Select * from user where mail=?";
		PreparedStatement preparedStatementSelect;
		
		preparedStatementSelect = (PreparedStatement) connection.prepareStatement(querySelect);
		preparedStatementSelect.setObject(1, mail, Types.VARCHAR);
		
		ResultSet resultSelect = UtilsBdd.selectPreapredStatement(preparedStatementSelect);
		while(resultSelect.next()){
			assertTrue("Right mail", mail.equals(resultSelect.getString("mail")));
			assertTrue("Right First Name", firstName.equals(resultSelect.getString("firstName")));
			assertTrue("Right Last Name", lastName.equals(resultSelect.getString("lastName")));
			assertTrue("Right phonenNumber", phoneNumber.equals(resultSelect.getString("phoneNumber")));
			assertTrue("Right user type", userType.equals(resultSelect.getString("userType")));
		}
		
		//Supprime l'utilisateur
		String queryDelete = "DELETE FROM user where mail=?";
		
		PreparedStatement preparedStatementDelete;
		
		preparedStatementDelete = (PreparedStatement) connection.prepareStatement(queryDelete);
		preparedStatementDelete.setObject(1, mail, Types.VARCHAR);
		
		int resDelete = UtilsBdd.executePreapredStatement(preparedStatement);
		assertTrue("Vérifie que la suppression a été effectuée", resDelete==1);
	}
	
}
