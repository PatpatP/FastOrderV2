package com.fastorder.manager.tests;

import org.junit.Before;

import com.fastorder.manager.impl.OrderManagerImpl;
import com.fastorder.utils.UtilsBdd;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class OrderManagerTests {
	
	OrderManagerImpl orderManager;
	Connection connection;
	
	@Before
	public void setUp(){
		connection =  UtilsBdd.connectBDD();
		orderManager = new OrderManagerImpl(connection);
	}
	
	//TODO Chaque méthode de UserManager a tester

}

