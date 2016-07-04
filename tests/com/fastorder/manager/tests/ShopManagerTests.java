package com.fastorder.manager.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fastorder.enumeration.ShopTypeEnum;
import com.fastorder.enumeration.UserTypeEnum;
import com.fastorder.manager.impl.AddressManagerImpl;
import com.fastorder.manager.impl.ShopManagerImpl;
import com.fastorder.manager.impl.UserManagerImpl;
import com.fastorder.model.Shop;
import com.fastorder.model.User;
import com.fastorder.utils.UtilsBdd;
import com.mysql.jdbc.Statement;

public class ShopManagerTests {
	
	private ShopManagerImpl shopManager;
	private UserManagerImpl userManager;
	private AddressManagerImpl addressManager;
	private Statement statement;
	private int addressId;
	private int userId;
	private int addressShopId;
	
	private String shopName;
	private String shopDescription;
	private String shopType;
	
	@Before
	public void setUp(){
		statement = UtilsBdd.connectBDD();
		shopManager = new ShopManagerImpl(statement);
		userManager = new UserManagerImpl(statement);
		addressManager = new AddressManagerImpl(statement);
		
		addressManager.createAddress("Test Unit Street", "42", "35000", "TestUnitCity", "TestUnitCountry");
		addressId = addressManager.getAddressId("Test Unit Street", "42", "35000", "TestUnitCity", "TestUnitCountry");
		
		addressManager.createAddress("TU Shop Street", "42", "32000", "Shop Test City", "Shop Test Country");
		addressShopId = addressManager.getAddressId("TU Shop Street", "42", "32000", "Shop Test City", "Shop Test Country");
		
		userManager.createUser("test@gmail.com", "0102030405", "test", "unit", UserTypeEnum.MERCHANT, addressId, "test1234");
		User user = userManager.getUser("test@gmail.com");
		userId = user.getId();
		
		shopName = "Test Shop Name";
		shopDescription = "Test shop Description";
		shopType = ShopTypeEnum.CHINOIS.toString();
		
	}
	
	@After
	public void clean(){
		userManager.deleteUser("test@gmail.com");
		addressManager.deleteAddress(addressId);
		addressManager.deleteAddress(addressShopId);	
	}

	@Test
	public void createShopTest(){
		shopManager.createShop(shopName, shopDescription, shopType, userId, addressId);
		int idShop = shopManager.getShopId(shopName, shopDescription, shopType, userId, addressId);
		Shop shop = shopManager.getShop(idShop);
		
		assertEquals("V�rification Shop Name", shopName, shop.getName());
		assertEquals("V�rification shop Description", shopDescription, shop.getDescription());
		assertEquals("V�rification shop type", ShopTypeEnum.CHINOIS, shop.getShopType());
		
		boolean isDeleted = shopManager.deleteShop(idShop);
		assertTrue("V�rication de la suppression d'un magasin", isDeleted);
	}
	
	@Test
	public void updateShopTest(){
		shopManager.createShop(shopName, shopDescription, shopType, userId, addressId);
		int idShop = shopManager.getShopId(shopName, shopDescription, shopType, userId, addressId);
		Shop shop = shopManager.getShop(idShop);
		
		assertEquals("V�rification Shop Name", shopName, shop.getName());
		assertEquals("V�rification shop Description", shopDescription, shop.getDescription());
		assertEquals("V�rification shop type", ShopTypeEnum.CHINOIS, shop.getShopType());
		
		String newShopName = "New Shop Name Test";
		String newShopDescription = "New Shop Description Test";
		
		shopManager.updateShop(idShop, newShopName, newShopDescription, shopType, userId, addressId);
		//TODO revoir la m�thode de MAJ d'un shop
		
		
		Shop updatedShop = shopManager.getShop(idShop);

		assertEquals("V�rification Shop Name", newShopName, updatedShop.getName());
		assertEquals("V�rification shop Description", newShopDescription, updatedShop.getDescription());
		assertEquals("V�rification shop type", ShopTypeEnum.CHINOIS, updatedShop.getShopType());
		
		boolean isDeleted = shopManager.deleteShop(idShop);
		assertTrue("V�rication de la suppression d'un magasin", isDeleted);
	}
	
	@Test
	public void deleteShopTest(){
		shopManager.createShop(shopName, shopDescription, shopType, userId, addressId);
		int idShop = shopManager.getShopId(shopName, shopDescription, shopType, userId, addressId);
		
		boolean isDeleted = shopManager.deleteShop(idShop);
		assertTrue("V�rication de la suppression d'un magasin", isDeleted);
	}
	
	@Test
	public void getShopWithIdTest(){
		
	}
	
	@Test
	public void getShopWithIdUserTest(){
		
	}
}
