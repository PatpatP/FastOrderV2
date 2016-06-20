package com.fastorder.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fastorder.enumeration.EstimatedTimeEnum;
import com.fastorder.enumeration.OrderStatusEnum;

public class Order {
	
	private int id;
	private List<Product> products;
	private EstimatedTimeEnum estimatedTime;
	private OrderStatusEnum status;
	private Date created;
	private float priceTotal;
	private int userId;
	private int shopId;
	
	public Order(int id, EstimatedTimeEnum estimatedTime, OrderStatusEnum status,Date created,float priceTotal,  int userId,
			int shopId) {
		super();
		this.id = id;
		this.products = new ArrayList<Product>();
		this.estimatedTime = estimatedTime;
		this.created = created;
		this.priceTotal = priceTotal;
		this.status = status;
		this.userId = userId;
		this.shopId = shopId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public float getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(float priceTotal) {
		this.priceTotal = priceTotal;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EstimatedTimeEnum getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(EstimatedTimeEnum estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public OrderStatusEnum getStatus() {
		return status;
	}

	public void setStatus(OrderStatusEnum status) {
		this.status = status;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void addProduct(Product product) {
		if(products.isEmpty()){
			products = new ArrayList<Product>();
		}
		products.add(product);
	}

	public boolean deleteProduct(Product product){
		if(products.isEmpty()){
			return false;
		} else {
			products.remove(product);
			return true;
		}
	}
	
//	@Override
//	public String toString() {
//		return "idOrder:"+id+", estimatedTime:"+estimatedTime.toString()+", status:"+status.toString()+", userOrderId:"+userId+", shopId:"+shopId;
//	}

}
