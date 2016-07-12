package com.fastorder.manager.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fastorder.enumeration.EstimatedTimeEnum;
import com.fastorder.enumeration.OrderStatusEnum;
import com.fastorder.enumeration.ProductTypeEnum;
import com.fastorder.enumeration.ShopTypeEnum;
import com.fastorder.enumeration.UserTypeEnum;
import com.fastorder.manager.impl.AddressManagerImpl;
import com.fastorder.manager.impl.OrderManagerImpl;
import com.fastorder.manager.impl.ShopManagerImpl;
import com.fastorder.manager.impl.UserManagerImpl;
import com.fastorder.model.Product;
import com.fastorder.model.User;
import com.fastorder.utils.UtilsBdd;
import com.mysql.jdbc.Connection;

public class OrderManagerTests {
	
	private OrderManagerImpl orderManager;
	private UserManagerImpl userManager;
	private ShopManagerImpl shopManager;
	private AddressManagerImpl addressManager;
	private Connection connection;
	private int clientId;
	private int merchantId;
	
	private int productId;
	private int idShop;
	
	private List<Product> productsOrder;
	
	@Before
	public void setUp(){
		connection =  UtilsBdd.connectBDD();
		orderManager = new OrderManagerImpl(connection);
		userManager = new UserManagerImpl(connection);
		shopManager = new ShopManagerImpl(connection);
		
		addressManager.createAddress("Test Street Merchant", "Test number Merchant", "Test Zip Code Merchant", "Test City Merchant", "Test Country Merchant");
		int idAddressMerchant = addressManager.getAddressId("Test Street Merchant", "Test number Merchant", "Test Zip Code Merchant", "Test City Merchant", "Test Country Merchant");
		//Création du compte marchant avec son shop
		userManager.createUser("testMerchant@gmail.com", "0102030405", "Merchant", "Test", UserTypeEnum.MERCHANT, idAddressMerchant, "test1234");
		User merchant = userManager.getUser("testMerchant@gmail.com");
		merchantId = merchant.getId();
		
		addressManager.createAddress("Test Street Shop", "Test number Shop", "Test Zip Code Shop", "Test City Shop", "Test Country Shop");
		int idAddressShop = addressManager.getAddressId("Test Street Shop", "Test number Shop", "Test Zip Code Shop", "Test City Shop", "Test Country Shop");
		shopManager.createShop("Test Shop", "Shop Test Order", ShopTypeEnum.JAPONAIS.toString(), merchantId, idAddressShop, null);
		idShop = shopManager.getShopId("Test Shop", "Shop Test Order", ShopTypeEnum.JAPONAIS.toString(), merchantId, idAddressShop);
		shopManager.createProduct(ProductTypeEnum.DRINK.toString(), "Coca Cola", "Soda", (float) 3.5, idShop);
		productId = shopManager.getProduct(ProductTypeEnum.DRINK.toString(), "Coca Cola", "Soda", (float) 3.5, idShop);
		Product product = shopManager.getProduct(productId);
		
		//Création du compte client
		addressManager.createAddress("Test Street Client", "Test number Client", "Test Zip Code Client", "Test City Client", "Test Country Client");
		int idAddressClient = addressManager.getAddressId("Test Street Client", "Test number Client", "Test Zip Code Client", "Test City Client", "Test Country Client");
		
		userManager.createUser("testClient@gmail.com", "0102030405", "Client", "Test", UserTypeEnum.CLIENT, idAddressClient, "test1234");
		User client = userManager.getUser("testClient@gmail.com");
		clientId = client.getId();
		
		//Création d'un panier 
		productsOrder = new ArrayList<Product>();
		productsOrder.add(product);
	}
	
	@Test
	public void createOrderTest(){
		orderManager.create(productsOrder, EstimatedTimeEnum.FIFTY, OrderStatusEnum.INPROGRESS, 4.5, clientId, idShop);
	}
	
	@Test
	public void updateOrderToInProgressTest(){
		orderManager.create(productsOrder, EstimatedTimeEnum.FIFTY, OrderStatusEnum.INPROGRESS, 4.5, clientId, idShop);
	}
	
	@Test
	public void deleteOrderTest(){
		orderManager.create(productsOrder, EstimatedTimeEnum.FIFTY, OrderStatusEnum.INPROGRESS, 4.5, clientId, idShop);
	}
	
	@Test
	public void getProductsOrderTest(){
		
	}
	
	@Test
	public void getOrderIdTest(){
		
	}

}

