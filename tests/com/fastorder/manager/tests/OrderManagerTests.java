package com.fastorder.manager.tests;

import org.junit.Before;

import com.fastorder.manager.impl.OrderManagerImpl;
import com.fastorder.utils.UtilsBdd;
import com.mysql.jdbc.Statement;

public class OrderManagerTests {
	
	OrderManagerImpl orderManager;
	Statement statement;
	
	@Before
	public void setUp(){
		statement =  UtilsBdd.connectBDD();
		orderManager = new OrderManagerImpl(statement);
	}
	
	//TODO Chaque méthode de UserManager a tester

}
