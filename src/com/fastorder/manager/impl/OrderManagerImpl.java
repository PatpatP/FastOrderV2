package com.fastorder.manager.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class OrderManagerImpl implements IOrderManager{

	private Connection connection;

	final static Logger logger = Logger.getLogger(OrderManagerImpl.class);

	public OrderManagerImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Product> getProducts(String[] productsId) {
		ShopManagerImpl shopManager = new ShopManagerImpl(connection);
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

		String query = "SELECT * from ordering WHERE id=?;";
		PreparedStatement prepareStatement;
		Order order = null;
		try {
			prepareStatement = (PreparedStatement) connection.prepareStatement(query);
			prepareStatement.setObject(1, id, Types.INTEGER); 
			ResultSet result = UtilsBdd.selectPreapredStatement(prepareStatement);
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
			logger.info("Succès - Récupération d'une commande via son id : "+id);
		} catch (SQLException e1) {
			logger.error("Erreur - Récupération d'une commande via son id : "+id);
		}
		return order;
	}

	@Override
	public void create(List<Product> products, EstimatedTimeEnum estimatedTime, OrderStatusEnum status, double totalPrice, int userId, int shopId) {

		int nbProduct = 0;
		if(products!=null){
			nbProduct = products.size();
		}
		String queryOrder = "Insert into ordering (estimatedTime, status, totalPrice, user, shop) VALUES(?,?,?,?,?);";
		PreparedStatement prepareStatementOrder;
		PreparedStatement prepareStatementIdOrder;
		int idOrder = 0;
		try {
			prepareStatementOrder = (PreparedStatement) connection.prepareStatement(queryOrder);
			prepareStatementOrder.setObject(1, estimatedTime, Types.VARCHAR); 
			prepareStatementOrder.setObject(2, status, Types.VARCHAR); 
			prepareStatementOrder.setObject(3, totalPrice, Types.DOUBLE); 
			prepareStatementOrder.setObject(4, userId, Types.INTEGER); 
			prepareStatementOrder.setObject(5, shopId, Types.INTEGER); 

			UtilsBdd.executePreapredStatement(prepareStatementOrder);

			String queryIdOrder = "Select * from ordering WHERE user=? AND shop=? AND status=?;";

			prepareStatementIdOrder = (PreparedStatement) connection.prepareStatement(queryIdOrder);
			prepareStatementIdOrder.setObject(1, userId, Types.INTEGER); 
			prepareStatementIdOrder.setObject(2, shopId, Types.INTEGER);
			prepareStatementIdOrder.setObject(3, status, Types.VARCHAR); 
			//On récupére l'id de la commande

			ResultSet resultIdOrder = UtilsBdd.selectPreapredStatement(prepareStatementIdOrder);

			while(resultIdOrder.next()){
				idOrder = resultIdOrder.getInt("id");
			}
			logger.info("Création de la commande effectuée");

			if(products!=null){
				//On créer autant des tables de jointure pour chaque produit
				for(int i = 0; i<nbProduct; i++){
					int idProduct = products.get(i).getId();
					//				String queryOrderHasProduct = "Insert into orderhasproduct(idOrder, idProduct) VALUES('"+idOrder+"','"+idProduct+"');";
					String queryOrderHasProduct = "Insert into orderhasproduct(idOrder, idProduct) VALUES(?,?);";
					PreparedStatement prepareStatementOrderHasProduct;
					prepareStatementOrderHasProduct = (PreparedStatement) connection.prepareStatement(queryOrderHasProduct);
					prepareStatementOrderHasProduct.setObject(1, idOrder, Types.INTEGER); 
					prepareStatementOrderHasProduct.setObject(2, idProduct, Types.INTEGER);
					UtilsBdd.executePreapredStatement(prepareStatementOrderHasProduct);
				}
			}
		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors de la crÃ©ation de la commande : " + e.getMessage());
		}
	}

	@Override
	public boolean delete(int id) {
		String query = "DELETE FROM ordering where id=?";
		PreparedStatement preparedStatement;
		int res = 0;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, id, Types.INTEGER);
			
			res = UtilsBdd.executePreapredStatement(preparedStatement);
			logger.info("Succès - Suppression de la commande");
		} catch (SQLException e) {
			logger.error("Echec - Suppression de la commande");
		}
		
		if(res==1){
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void updateOrderToInProgress(int idOrder) {
		String query = "Update ordering SET status=? WHERE id=?;";
		PreparedStatement prepareStatement;

		try {
			prepareStatement = (PreparedStatement) connection.prepareStatement(query);
			prepareStatement.setObject(1, OrderStatusEnum.READY, Types.VARCHAR); 
			prepareStatement.setObject(2, idOrder, Types.INTEGER); 
			UtilsBdd.executePreapredStatement(prepareStatement);
			logger.info("Succès - Echec de la mise à jour du status d'une commande");
		} catch (SQLException e) {
			logger.error("Erreur - Echec de la mise à jour du status d'une commande, erreur suivante : "+e.getMessage());
		}
	}

}
