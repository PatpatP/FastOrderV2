package com.fastorder.manager.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
import com.mysql.jdbc.Connection;

public class ShopManagerTests {
	
	private ShopManagerImpl shopManager;
	private UserManagerImpl userManager;
	private AddressManagerImpl addressManager;
	private Connection connection;
	private int addressId;
	private int userId;
	private int addressShopId;
	
	private String shopName;
	private String shopDescription;
	private String shopType;
	
	@Before
	public void setUp(){
		connection = UtilsBdd.connectBDD();
		shopManager = new ShopManagerImpl(connection);
		userManager = new UserManagerImpl(connection);
		addressManager = new AddressManagerImpl(connection);
		
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
		shopManager.createShop(shopName, shopDescription, shopType, userId, addressId, null);
		int idShop = shopManager.getShopId(shopName, shopDescription, shopType, userId, addressId);
		Shop shop = shopManager.getShop(idShop);
		
		assertEquals("Vérification Shop Name", shopName, shop.getName());
		assertEquals("Vérification shop Description", shopDescription, shop.getDescription());
		assertEquals("Vérification shop type", ShopTypeEnum.CHINOIS, shop.getShopType());
		
		boolean isDeleted = shopManager.deleteShop(idShop);
		assertTrue("Vérication de la suppression d'un magasin", isDeleted);
	}
	
	@Test
	public void updateShopTest(){
		shopManager.createShop(shopName, shopDescription, shopType, userId, addressId, null);
		int idShop = shopManager.getShopId(shopName, shopDescription, shopType, userId, addressId);
		Shop shop = shopManager.getShop(idShop);
		
		assertEquals("Vérification Shop Name", shopName, shop.getName());
		assertEquals("Vérification shop Description", shopDescription, shop.getDescription());
		assertEquals("Vérification shop type", ShopTypeEnum.CHINOIS, shop.getShopType());
		
		String newShopName = "New Shop Name Test";
		String newShopDescription = "New Shop Description Test";
		
		shopManager.updateShop(idShop, newShopName, newShopDescription, shopType, userId, addressId);
		
		Shop updatedShop = shopManager.getShop(idShop);

		assertEquals("Vérification Shop Name", newShopName, updatedShop.getName());
		assertEquals("Vérification shop Description", newShopDescription, updatedShop.getDescription());
		assertEquals("Vérification shop type", ShopTypeEnum.CHINOIS, updatedShop.getShopType());
		
		boolean isDeleted = shopManager.deleteShop(idShop);
		assertTrue("Vérication de la suppression d'un magasin", isDeleted);
	}
	
	@Test
	public void deleteShopTest(){
		shopManager.createShop(shopName, shopDescription, shopType, userId, addressId, null);
		int idShop = shopManager.getShopId(shopName, shopDescription, shopType, userId, addressId);
		
		boolean isDeleted = shopManager.deleteShop(idShop);
		assertTrue("Vérication de la suppression d'un magasin", isDeleted);
	}
	
	@Test
	public void getShopWithIdTest(){
		shopManager.createShop(shopName, shopDescription, shopType, userId, addressId, null);
		int idShop = shopManager.getShopId(shopName, shopDescription, shopType, userId, addressId);
		
		Shop shop = shopManager.getShop(idShop);
		
		assertEquals("Vérification Shop Name", shopName, shop.getName());
		assertEquals("Vérification shop Description", shopDescription, shop.getDescription());
		assertEquals("Vérification shop type", ShopTypeEnum.CHINOIS, shop.getShopType());
		
		shopManager.deleteShop(idShop);
	}
	
	@Test
	public void getShopWithIdUserTest(){
		shopManager.createShop(shopName, shopDescription, shopType, userId, addressId, null);
		List<Shop> shops = userManager.getUserShops("test@gmail.com");
		assertTrue("On v�rifie qu'il y a qu'un seul magasin", shops.size()==1);
		
		Shop shop = shops.get(0);
		
		assertEquals("Vérification Shop Name", shopName, shop.getName());
		assertEquals("Vérification shop Description", shopDescription, shop.getDescription());
		assertEquals("Vérification shop type", ShopTypeEnum.CHINOIS, shop.getShopType());
		
		int idShop = shopManager.getShopId(shopName, shopDescription, shopType, userId, addressId);
		shopManager.deleteShop(idShop);
	}
}
