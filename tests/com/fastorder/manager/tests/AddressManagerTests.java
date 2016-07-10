package com.fastorder.manager.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.fastorder.manager.impl.AddressManagerImpl;
import com.fastorder.model.Address;
import com.fastorder.utils.UtilsBdd;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class AddressManagerTests {
	
	private AddressManagerImpl addressManager;
	private Connection connection;
	
	private String street;
	private String number;
	private String zipCode;
	private String city;
	private String country;
	
	@Before
	public void setUp(){
		connection = UtilsBdd.connectBDD();
		addressManager = new AddressManagerImpl(connection);
		
		street = "Street Test";
		number= "30";
		zipCode= "75012";
		city= "City Test";
		country= "Country Test";
	}

	@Test
	public void createAddressTest(){
		boolean isCreated = addressManager.createAddress(street, number, zipCode, city, country);
		assertTrue("Cr�ation de l'adresse", isCreated);
		
		int idAddress = addressManager.getAddressId(street, number, zipCode, city, country);
		Address address = addressManager.getAddresssById(idAddress);
		
		assertEquals("V�rification du nom de rue", street, address.getStreet());
		assertEquals("Verification du num�ro de rue", number, address.getNumber());
		assertEquals("V�rification du code postal", zipCode, address.getZipCode());
		assertEquals("V�rification de la ville", city, address.getCity());
		assertEquals("V�rification du pays", country, address.getCountry());
		
		boolean isDeleted = addressManager.deleteAddress(idAddress);
		assertTrue("Suppression de l'adresse", isDeleted);
	}
	
	@Test
	public void updateAddressTest(){
		boolean isCreated = addressManager.createAddress(street, number, zipCode, city, country);
		assertTrue("Cr�ation de l'adresse", isCreated);
		
		int idAddress = addressManager.getAddressId(street, number, zipCode, city, country);
		Address address = addressManager.getAddresssById(idAddress);
		
		assertEquals("V�rification du nom de rue", street, address.getStreet());
		assertEquals("Verification du num�ro de rue", number, address.getNumber());
		assertEquals("V�rification du code postal", zipCode, address.getZipCode());
		assertEquals("V�rification de la ville", city, address.getCity());
		assertEquals("V�rification du pays", country, address.getCountry());
		
		String newStreetName = "New Street Test";
		String newNumber = "42";
		String newCity = "New City Test";
		
		boolean isUpdated = addressManager.updateAddress(idAddress, newStreetName, newNumber, zipCode, newCity, country);
		assertTrue("Mise � jour de l'address", isUpdated);
		
		Address newAddress = addressManager.getAddresssById(idAddress);
		
		assertEquals("V�rification du nom de rue", newStreetName, newAddress.getStreet());
		assertEquals("Verification du num�ro de rue", newNumber, newAddress.getNumber());
		assertEquals("V�rification du code postal", zipCode, newAddress.getZipCode());
		assertEquals("V�rification de la ville", newCity, newAddress.getCity());
		assertEquals("V�rification du pays", country, newAddress.getCountry());
		
		boolean isDeleted = addressManager.deleteAddress(idAddress);
		assertTrue("Suppression de l'adresse", isDeleted);
	}
	
	@Test
	public void deleteAddressTest(){
		addressManager.createAddress(street, number, zipCode, city, country);
		
		int idAddress = addressManager.getAddressId(street, number, zipCode, city, country);

		boolean isDeleted = addressManager.deleteAddress(idAddress);
		assertTrue("Suppression de l'adresse", isDeleted);
	}
}
