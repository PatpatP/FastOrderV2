package com.fastorder.manager.tests;

import org.junit.Before;

import com.fastorder.manager.impl.ShopManagerImpl;
import com.fastorder.utils.Utils;
import com.mysql.jdbc.Statement;

public class ShopManagerTests {
	
	ShopManagerImpl shopManager;
	Statement statement;
	
	@Before
	public void setUp(){
		statement = Utils.connectBDD();
		shopManager = new ShopManagerImpl(statement);
	}

}
