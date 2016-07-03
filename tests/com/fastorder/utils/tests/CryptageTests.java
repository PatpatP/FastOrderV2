package com.fastorder.utils.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class CryptageTests {

	private String password = "Test";
	private String correctPassword = "Test";
	private String wrongPassword = "Test2";
	
	@Test
	public void cryptageTest(){
		String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		System.out.println(hashPassword);
		
		boolean samePass = BCrypt.checkpw(correctPassword, hashPassword);
		assertTrue(samePass);
		
		
		boolean wrongPassAnswer = BCrypt.checkpw(wrongPassword, hashPassword);
		assertFalse(wrongPassAnswer);
	}
	
}
