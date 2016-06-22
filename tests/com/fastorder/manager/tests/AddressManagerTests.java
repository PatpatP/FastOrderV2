package com.fastorder.manager.tests;

import org.junit.Before;

import com.fastorder.manager.impl.AddressManagerImpl;
import com.fastorder.utils.Utils;
import com.mysql.jdbc.Statement;

public class AddressManagerTests {
	
	AddressManagerImpl addressManager;
	Statement statement;
	
	@Before
	public void setUp(){
		statement = Utils.connectBDD();
		addressManager = new AddressManagerImpl(statement);
	}

}
