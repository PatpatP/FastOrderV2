package com.fastorder.manager.tests;

import org.junit.Before;

import com.fastorder.manager.impl.OrderManagerImpl;
import com.fastorder.utils.Utils;
import com.mysql.jdbc.Statement;

public class OrderManagerTests {
	
	OrderManagerImpl orderManager;
	Statement statement;
	
	@Before
	public void setUp(){
		statement =  Utils.connectBDD();
		orderManager = new OrderManagerImpl(statement);
	}
	
	//TODO Chaque m�thode de UserManager a tester

}
