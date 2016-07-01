package com.fastorder.utils.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.fastorder.utils.Utils;
import com.fastorder.utils.UtilsBdd;
import com.mysql.jdbc.Statement;

public class BddTests {
	
	Statement statement;
	
	@Before
	public void setUp(){
		statement = UtilsBdd.connectBDD();
	}
	
	@Test
	public void testConnection(){
		assertNotNull("The connection should not be null", statement);
	}
	
	@Test
	public void testSelect() throws SQLException{
		String query = "Select * from user where id="+0;
		ResultSet result = UtilsBdd.selectQuery(statement, query);
		while(result.next()){
			assertEquals("pol.patrick1411@gmail.com",result.getString("mail"));
			assertEquals("669468518",result.getString("phoneNumber"));
			assertEquals("Patrick",result.getString("firstName"));
			assertEquals("POL",result.getString("lastName"));
			assertEquals("Merchant",result.getString("userType"));
		}
	}
	
	@Test
	public void testInsert() throws SQLException{
		String query = "Insert into user (mail, phoneNumber, firstName, lastName, userType, address, password) "
				+ "VALUES('testUnit@gmail.com','0102030405','test first name','test last name','Merchant','10','test1234');";
		UtilsBdd.insertQuery(statement, query);
		String querySelect = "Select * from user where mail='testUnit@gmail.com'";
		ResultSet result = UtilsBdd.selectQuery(statement, querySelect);
		while(result.next()){
			assertEquals("testUnit@gmail.com",result.getString("mail"));
			assertEquals("0102030405",result.getString("phoneNumber"));
			assertEquals("test first name",result.getString("firstName"));
			assertEquals("test last name",result.getString("lastName"));
			assertEquals("Merchant",result.getString("userType"));
		}
		
		String queryDelete ="DELETE FROM USER WHERE mail='testUnit@gmail.com';";
		UtilsBdd.deleteQuery(statement, queryDelete);
	}
	
	@Test
	public void testUpdate() throws SQLException{
		String query = "Insert into user (mail, phoneNumber, firstName, lastName, userType, address, password) "
				+ "VALUES('testUnit@gmail.com','0102030405','test first name','test last name','Merchant','10','test1234');";
		UtilsBdd.insertQuery(statement, query);
		
		String updateQuery = "Update user SET firstName='new first name', lastName='new last name' WHERE mail='testUnit@gmail.com'";
		UtilsBdd.updateQuery(statement, updateQuery);
		String querySelect = "Select * from user where mail='testUnit@gmail.com'";
		ResultSet result = UtilsBdd.selectQuery(statement, querySelect);
		while(result.next()){
			assertEquals("new first name",result.getString("firstName"));
			assertEquals("new last name",result.getString("lastName"));
		}
		
		String queryDelete ="DELETE FROM USER WHERE mail='testUnit@gmail.com';";
		UtilsBdd.deleteQuery(statement, queryDelete);
		
	}
	
	@Test
	public void testDelete(){
		String query = "Insert into user (mail, phoneNumber, firstName, lastName, userType, address, password) "
				+ "VALUES('testUnit@gmail.com','0102030405','test first name','test last name','Merchant','10','test1234');";
		UtilsBdd.insertQuery(statement, query);
		String queryDelete ="DELETE FROM USER WHERE mail='testUnit@gmail.com';";
		UtilsBdd.deleteQuery(statement, queryDelete);
		
	}
}
