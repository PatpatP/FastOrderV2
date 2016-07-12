package com.fastorder.manager.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fastorder.enumeration.ProductTypeEnum;
import com.fastorder.enumeration.ShopTypeEnum;
import com.fastorder.enumeration.UserTypeEnum;
import com.fastorder.manager.impl.AddressManagerImpl;
import com.fastorder.manager.impl.ShopManagerImpl;
import com.fastorder.manager.impl.UserManagerImpl;
import com.fastorder.model.Product;
import com.fastorder.model.User;
import com.fastorder.utils.UtilsBdd;
import com.mysql.jdbc.Connection;

public class ProductTest {
	
	private ShopManagerImpl shopManager;
	private AddressManagerImpl addressManager;
	private UserManagerImpl userManager;
	private Connection connection;
	private int shopId;
	private int addressIdShop;
	private int addressIdUser;
	private User user;
	
	@Before
	public void setUp(){
		connection = UtilsBdd.connectBDD();
		shopManager = new ShopManagerImpl(connection);
		addressManager = new AddressManagerImpl(connection);
		addressManager.createAddress("tests unit product", "42", "75000", "TestCity", "TestLand");
		addressIdShop = addressManager.getAddressId("tests unit product", "42", "75000", "TestCity", "TestLand");
		addressManager.createAddress("tests unit user", "42", "75000", "TestCity", "TestLand");
		addressIdUser = addressManager.getAddressId("tests unit user", "42", "75000", "TestCity", "TestLand");
		userManager = new UserManagerImpl(connection);
		userManager.createUser("testunit@gmail.com", "0102030405", "test", "unit", UserTypeEnum.CLIENT, addressIdUser, "test1234");
		user = userManager.getUser("testunit@gmail.com");
		shopManager.createShop("Test Unit", "Sell product unittest", ShopTypeEnum.CHINOIS.toString(), user.getId(), addressIdShop, null);
		shopId = shopManager.getShopId("Test Unit", "Sell product unittest", ShopTypeEnum.CHINOIS.toString(), user.getId(), addressIdShop);
		
	}
	
	@After
	public void clean(){
		addressManager.deleteAddress(addressIdUser);
		userManager.deleteUser("testunit@gmail.com");
		addressManager.deleteAddress(addressIdShop);
		shopManager.deleteShop(shopId);
		
	}
	
	@Test
	public void createProductTest(){
		shopManager.createProduct(ProductTypeEnum.DRINK.toString(), "coca", "soda", (float) 3.5, shopId);
		int idProduct = shopManager.getProduct(ProductTypeEnum.DRINK.toString(), "coca", "soda", (float) 3.5, shopId);
		Product product = shopManager.getProduct(idProduct);
		
		assertEquals(ProductTypeEnum.DRINK, product.getProductType());
		assertEquals("coca", product.getName());
		assertEquals("soda", product.getDescription());
		
		shopManager.deleteProduct(idProduct);
	}
	
	@Test
	public void updateProductTest(){
		shopManager.createProduct(ProductTypeEnum.DRINK.toString(), "coca", "soda", (float) 3.5, shopId);
		int idProduct = shopManager.getProduct(ProductTypeEnum.DRINK.toString(), "coca", "soda", (float) 3.5, shopId);
		Product product = shopManager.getProduct(idProduct);
		
		assertEquals(ProductTypeEnum.DRINK, product.getProductType());
		assertEquals("coca", product.getName());
		assertEquals("soda", product.getDescription());
		
		shopManager.updateProduct(idProduct, ProductTypeEnum.DRINK.toString(), "Coca Cola", "Boisson sans alcool", (float) 4.0, shopId);
		
		Product productAfterUpdate = shopManager.getProduct(idProduct);
		
		assertEquals(ProductTypeEnum.DRINK, productAfterUpdate.getProductType());
		assertEquals("Coca Cola", productAfterUpdate.getName());
		assertEquals("Boisson sans alcool", productAfterUpdate.getDescription());
		
		shopManager.deleteProduct(idProduct);
	}
	
	@Test
	public void deleteProductTest(){
		shopManager.createProduct(ProductTypeEnum.DRINK.toString(), "coca", "soda", (float) 3.5, shopId);
		int idProduct = shopManager.getProduct(ProductTypeEnum.DRINK.toString(), "coca", "soda", (float) 3.5, shopId);
		
		boolean isDeleted = shopManager.deleteProduct(idProduct);
		assertTrue("Suppression du produit", isDeleted);
	}
	
	@Test
	public void getShopProductsTest(){
		shopManager.createProduct(ProductTypeEnum.DRINK.toString(), "coca", "soda", (float) 3.5, shopId);
		int idProduct1 = shopManager.getProduct(ProductTypeEnum.DRINK.toString(), "coca", "soda", (float) 3.5, shopId);
		shopManager.createProduct(ProductTypeEnum.DRINK.toString(), "fanta", "soda", (float) 4, shopId);
		int idProduct2 = shopManager.getProduct(ProductTypeEnum.DRINK.toString(), "fanta", "soda", (float) 4, shopId);
		shopManager.createProduct(ProductTypeEnum.DRINK.toString(), "orangina", "soda", (float) 3, shopId);
		int idProduct3 = shopManager.getProduct(ProductTypeEnum.DRINK.toString(), "orangina", "soda", (float) 3, shopId);
		
		List<Product> products = shopManager.getProducts(shopId);
		assertTrue("Vérifie qu'il y a bien 3 produits", products.size()==3);
		
		shopManager.deleteProduct(idProduct1);
		shopManager.deleteProduct(idProduct2);
		shopManager.deleteProduct(idProduct3);
	}
	
	@Test
	public void getProductByIdTest(){
		shopManager.createProduct(ProductTypeEnum.DRINK.toString(), "coca", "soda", (float) 3.5, shopId);
		int idProduct = shopManager.getProduct(ProductTypeEnum.DRINK.toString(), "coca", "soda", (float) 3.5, shopId);
		Product product = shopManager.getProduct(idProduct);
		
		assertEquals(ProductTypeEnum.DRINK, product.getProductType());
		assertEquals("coca", product.getName());
		assertEquals("soda", product.getDescription());
		
		shopManager.deleteProduct(idProduct);
	}


}
