package com.fastorder.utils.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.fastorder.enumeration.UserTypeEnum;
import com.fastorder.manager.impl.UserManagerImpl;
import com.fastorder.utils.Utils;
import com.mysql.jdbc.Statement;

public class BddConnectionTests {
	
	@Test
	public void testConnection(){
		assertNotNull("The connection should not be null", Utils.connectBDD());
	}	
}
