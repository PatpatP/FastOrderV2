package com.fastorder.manager;

import java.util.List;

import com.fastorder.enumeration.EstimatedTimeEnum;
import com.fastorder.enumeration.OrderStatusEnum;
import com.fastorder.model.Order;
import com.fastorder.model.Product;
import com.fastorder.model.Shop;
import com.fastorder.model.User;

public interface IOrderManager {
	
	public List<Product> getProducts(String[] productsId);
	public Order getOrder(int id);
	
	public void create(List<Product> products, EstimatedTimeEnum estimatedTime, OrderStatusEnum status, float totalPrice, int userId, int shopId);
	public void updateOrderToInProgress(int idOrder);
	
	public void delete(int id);

}
