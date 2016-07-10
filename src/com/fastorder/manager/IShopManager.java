package com.fastorder.manager;

import java.io.InputStream;
import java.util.List;

import com.fastorder.model.Order;
import com.fastorder.model.Product;
import com.fastorder.model.Shop;
import com.fastorder.model.User;

public interface IShopManager {
	
	public List<Shop> getShops();
	public Shop getShop(Integer id);
	public int getShopId(String name, String description, String shopType, int userId, int addressId);
	public User getOwner(int idShop);
	
	public boolean createShop(String name, String description, String shopType, int userId, int addressId, InputStream image);
	public boolean updateShop(int idShop, String name, String description, String shopType, int userId, int addressId);
	public boolean deleteShop(int id);
	
	//Concerne la gestion des produits
	public List<Product> getProducts(int shopId);
	public Product getProduct(int id);
	public int getProduct(String producType, String name, String description, Float price, int idShop);
	public boolean createProduct(String productType, String name, String description, Float price, int idShop);
	public boolean updateProduct(int idProduct, String productType, String name, String description, Float price, int idShop);
	public boolean deleteProduct(int id);
	
	//Méthode pour qu'un magasin récupère les commandes qu'il a recu
	public List<Order> getReceivedOrder(int shop);
	
}	
