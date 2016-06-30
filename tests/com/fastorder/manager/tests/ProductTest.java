package com.fastorder.manager.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.fastorder.enumeration.ProductTypeEnum;
import com.fastorder.enumeration.ShopTypeEnum;
import com.fastorder.enumeration.UserTypeEnum;
import com.fastorder.manager.impl.AddressManagerImpl;
import com.fastorder.manager.impl.ShopManagerImpl;
import com.fastorder.manager.impl.UserManagerImpl;
import com.fastorder.model.Product;
import com.fastorder.model.User;
import com.fastorder.utils.Utils;
import com.mysql.jdbc.Statement;

public class ProductTest {
	
	private ShopManagerImpl shopManager;
	private AddressManagerImpl addressManager;
	private UserManagerImpl userManager;
	private Statement statement;
	private int shopId;
	private int addressIdShop;
	private int addressIdUser;
	private User user;
	
	@Before
	public void setUp(){
		statement = Utils.connectBDD();
		shopManager = new ShopManagerImpl(statement);
		addressManager = new AddressManagerImpl(statement);
		addressManager.createAddress("tests unit product", "42", "75000", "TestCity", "TestLand");
		addressIdShop = addressManager.getAddressId("tests unit product", "42", "75000", "TestCity", "TestLand");
		addressManager.createAddress("tests unit user", "42", "75000", "TestCity", "TestLand");
		addressIdUser = addressManager.getAddressId("tests unit user", "42", "75000", "TestCity", "TestLand");
		userManager = new UserManagerImpl(statement);
		userManager.createUser("testunit@gmail.com", "0102030405", "test", "unit", UserTypeEnum.CLIENT, addressIdUser, "test1234");
		user = userManager.getUser("testunit@gmail.com");
		shopManager.createShop("Test Unit", "Sell product unittest", ShopTypeEnum.CHINOIS.toString(), user.getId(), addressIdShop);
		shopId = shopManager.getShop("Test Unit", "Sell product unittest", ShopTypeEnum.CHINOIS.toString(), user.getId(), addressIdShop);
		
	}
	
	@After
	public void clean(){
		addressManager.deleteAddress(addressIdUser);
		userManager.deleteUser("testunit@gmail.com");
		addressManager.deleteAddress(addressIdShop);
		shopManager.deleteShop(shopId);
		
	}
	
	@Test
	@Ignore
	public void createProductTest(){
		shopManager.createProduct(ProductTypeEnum.DRINK.toString(), "coca", "soda", (float) 3.05, shopId);
		int idProduct = shopManager.getProduct(ProductTypeEnum.DRINK.toString(), "coca", "soda", (float) 3.05, shopId);
		Product product = shopManager.getProduct(idProduct);
		
		assertEquals(ProductTypeEnum.DRINK, product.getProductType());
		assertEquals("coca", product.getName());
		assertEquals("soda", product.getDescription());
		assertTrue(Float.parseFloat("3.05")==product.getPrice());
		
		shopManager.deleteProduct(idProduct);
	}
	
	@Test
	public void updateProductTest(){
		shopManager.createProduct(ProductTypeEnum.DRINK.toString(), "coca", "soda", (float) 3.05, shopId);
		int idProduct = shopManager.getProduct(ProductTypeEnum.DRINK.toString(), "coca", "soda", (float) 3.05, shopId);
		Product product = shopManager.getProduct(idProduct);
		
		//TODO Mettre la partie mettre à jour
		
		shopManager.deleteProduct(idProduct);
	}
	
	@Test
	public void deleteProductTest(){
		shopManager.createProduct(ProductTypeEnum.DRINK.toString(), "coca", "soda", (float) 3.05, shopId);
		int idProduct = shopManager.getProduct(ProductTypeEnum.DRINK.toString(), "coca", "soda", (float) 3.05, shopId);
		Product product = shopManager.getProduct(idProduct);
		
		shopManager.deleteProduct(idProduct);
	}
	
	@Test
	public void getShopProductsTest(){
		
	}
	
	@Test
	public void getProductByIdTest(){
		
	}


}
