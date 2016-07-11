package com.fastorder.utils.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class CryptageTests {

	private String mdp = "Test";
	private String correctmdp = "Test";
	private String wrongmdp = "Test2";
	
	@Test
	public void cryptageTest(){
		String hashPassword = BCrypt.hashpw(mdp, BCrypt.gensalt());
		System.out.println(hashPassword);
		
		boolean samePass = BCrypt.checkpw(correctmdp, hashPassword);
		assertTrue(samePass);
		
		
		boolean wrongPassAnswer = BCrypt.checkpw(wrongmdp, hashPassword);
		assertFalse(wrongPassAnswer);
	}
	
}
