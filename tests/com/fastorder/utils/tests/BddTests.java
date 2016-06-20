package com.fastorder.utils.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.fastorder.enumeration.UserTypeEnum;
import com.fastorder.manager.impl.UserManagerImpl;
import com.fastorder.utils.Utils;
import com.mysql.jdbc.Statement;

public class BddTests {
	
	private UserManagerImpl userManager;
	private Statement statement;
	
	@Before
	public void setUp(){
		statement = Utils.connectBDD();
		userManager = new UserManagerImpl(statement);
	}
	
	@Test
	public void testConnection(){
		assertNotNull("The connection should not be null", Utils.connectBDD());
	}

	@Test
	public void testCreateDeleteUser(){
		assertTrue("Should be alble to create a user", userManager.createUser("pascalpol2002@gmail.com", "0601020304", "Pascal", "POL", UserTypeEnum.CLIENT, 3, "test1234"));
		assertTrue("Should be alble to create a user", true /*La méthode qui permet de supprimer un utilisateur*/);
	}
	
}
