package com.fastorder.manager.tests;

import org.junit.Before;
import org.junit.Test;

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

	@Test
	public void createAddressTest(){
		String street = "Street Test";
		String number= "30";
		String zipCode= "75012";
		String city= "City Test";
		String country= "Country Test";
		
		addressManager.createAddress(street, number, zipCode, city, country);
	}
}
