package com.fastorder.manager.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.fastorder.enumeration.EstimatedTimeEnum;
import com.fastorder.enumeration.OrderStatusEnum;
import com.fastorder.manager.IOrderManager;
import com.fastorder.model.Order;
import com.fastorder.model.Product;
import com.fastorder.utils.Utils;
import com.fastorder.utils.UtilsBdd;
import com.mysql.jdbc.Statement;

public class OrderManagerImpl implements IOrderManager{

	private Statement statement;

	final static Logger logger = Logger.getLogger(OrderManagerImpl.class);

	public OrderManagerImpl(Statement statement) {
		this.statement = statement;
	}

	@Override
	public List<Product> getProducts(String[] productsId) {
		ShopManagerImpl shopManager = new ShopManagerImpl(statement);
		List<Product> products = new ArrayList<Product>();

		for(String id : productsId){
			int idProduct = Integer.parseInt(id);
			if(idProduct>=0){
				Product product = shopManager.getProduct(Integer.parseInt(id));
				products.add(product);
			}
		}

		return products;
	}

	@Override
	public Order getOrder(int id){
		String query = "SELECT * from ordering WHERE id='"+id+"';";
		ResultSet result = UtilsBdd.selectQuery(statement, query);
		Order order = null;
		try {
			while(result.next()){
				String estimatedTime = result.getString("estimatedTime");
				String status = result.getString("status");
				Date created = result.getDate("created");
				float totalPrice = result.getFloat("totalPrice");
				int idUser = result.getInt("user");
				int shop = result.getInt("shop");

				EstimatedTimeEnum estimatedTimeEnum = Utils.getEstimatedTime(estimatedTime);
				OrderStatusEnum orderStatusEnum = Utils.getOrderStatus(status);

				order = new Order(id,  estimatedTimeEnum, orderStatusEnum, created, totalPrice, idUser, shop);

			}
			logger.info("Commande récupérée");
		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors de la récupération de la commande : " + e.getMessage());
		}

		return order;
	}

	@Override
	public void create(List<Product> products, EstimatedTimeEnum estimatedTime, OrderStatusEnum status, double totalPrice, int userId, int shopId) {

		int nbProduct = 0;
		if(products!=null){
			nbProduct = products.size();
		}
		// On créer la commande
		String queryOrder = "Insert into ordering (estimatedTime, status, totalPrice, user, shop) VALUES('"+estimatedTime+"','"+status+"','"+totalPrice+"','"+userId+"','"+shopId+"');";
		UtilsBdd.insertQuery(statement, queryOrder);

		//On récupère l'id de la commande
		String queryIdOrder = "Select * from ordering WHERE user='"+userId+"' AND shop='"+shopId+"' AND status='"+status+"';";
		int idOrder = 0;
		ResultSet resultIdOrder = UtilsBdd.selectQuery(statement, queryIdOrder);
		try {
			while(resultIdOrder.next()){
				idOrder = resultIdOrder.getInt("id");
			}
			logger.info("Création de la commande effectuée");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Une erreur est survenue lors de la création de la commande : " + e.getMessage());
		}
		if(products!=null){
			//On créer autant des tables de jointure pour chaque produit
			for(int i = 0; i<nbProduct; i++){
				int idProduct = products.get(i).getId();
				String queryOrderHasProduct = "Insert into orderhasproduct(idOrder, idProduct) VALUES('"+idOrder+"','"+idProduct+"');";
				UtilsBdd.insertQuery(statement, queryOrderHasProduct);
			}
		}
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateOrderToInProgress(int idOrder) {
		String query = "Update ordering SET status='"+OrderStatusEnum.READY+"' WHERE id='"+idOrder+"';";
		UtilsBdd.updateQuery(statement, query);
	}

}
