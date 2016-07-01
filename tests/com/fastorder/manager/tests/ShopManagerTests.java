package com.fastorder.manager.tests;

import org.junit.Before;
import org.junit.Test;

import com.fastorder.manager.impl.ShopManagerImpl;
import com.fastorder.utils.UtilsBdd;
import com.mysql.jdbc.Statement;

public class ShopManagerTests {
	
	ShopManagerImpl shopManager;
	Statement statement;
	
	@Before
	public void setUp(){
		statement = UtilsBdd.connectBDD();
		shopManager = new ShopManagerImpl(statement);
	}

	@Test
	public void createShopTest(){
		
	}
	
	@Test
	public void updateShopTest(){
		
	}
	
	@Test
	public void deleteShopTest(){
		
	}
	
	@Test
	public void getShopWithIdTest(){
		
	}
	
	@Test
	public void getShopWithIdUserTest(){
		
	}
}
