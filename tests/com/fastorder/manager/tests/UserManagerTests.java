package com.fastorder.manager.tests;

import org.junit.Before;

import com.fastorder.manager.impl.UserManagerImpl;
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
	
	//TODO Chaque méthode de UserManager a tester

}
