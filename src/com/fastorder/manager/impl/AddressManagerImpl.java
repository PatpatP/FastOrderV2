package com.fastorder.manager.impl;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;

import com.fastorder.manager.IAddressManager;
import com.fastorder.model.Address;
import com.fastorder.utils.UtilsBdd;
import com.mysql.jdbc.PreparedStatement;

public class AddressManagerImpl implements IAddressManager{

	private Connection connection;
	
	final static Logger logger = Logger.getLogger(AddressManagerImpl.class);

	public AddressManagerImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public boolean createAddress(String street, String number, String zipCode, String city, String country) {
		String query = "Insert into address (street,number,zipCode,city,country) VALUES(?,?,?,?,?);";
		
		PreparedStatement preparedStatement;
		int res = 0;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, street, Types.VARCHAR); 
			preparedStatement.setObject(2, number, Types.VARCHAR); 
			preparedStatement.setObject(3, zipCode, Types.VARCHAR); 
			preparedStatement.setObject(4, city, Types.VARCHAR); 
			preparedStatement.setObject(5, country, Types.VARCHAR); 
			res = UtilsBdd.executePreapredStatement(preparedStatement);
			logger.info("Succès - Création de l'adresse");
		} catch (SQLException e) {
			logger.error("Erreur - Création de l'adresse, erreur suivant : "+e.getMessage());
		}
		if(res==1){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getAddressId(String street, String number, String zipCode, String city, String country) {
		String query = "Select * from address WHERE street=? AND number=? AND zipCode=? AND city=? AND country=?;";
		PreparedStatement preparedStatement;
		int addressId = 0;
		ResultSet resultat;
		
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, street, Types.VARCHAR); 
			preparedStatement.setObject(2, number, Types.VARCHAR); 
			preparedStatement.setObject(3, zipCode, Types.VARCHAR); 
			preparedStatement.setObject(4, city, Types.VARCHAR); 
			preparedStatement.setObject(5, country, Types.VARCHAR); 
			resultat = UtilsBdd.selectPreapredStatement(preparedStatement);
			
			while(resultat.next()){
				addressId = resultat.getInt("id");
			}
			logger.info("Succès - Récupération de l'id de l'adresse");
		} catch (SQLException e) {
			logger.error("Erreur - Récupération de l'id de l'adresse, erreur suivant : "+e.getMessage());
		}
		return addressId;
		
	}

	@Override
	public Address getAddresssById(int id) {
		String query = "Select * from address WHERE id=?;";
		PreparedStatement preparedStatement;
		ResultSet resultat;
		
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, id, Types.INTEGER); 
			resultat = UtilsBdd.selectPreapredStatement(preparedStatement);
			
			while(resultat.next()){
				String street =  resultat.getString("street");
				String number =  resultat.getString("number");
				String zipCode =  resultat.getString("zipCode");
				String city =  resultat.getString("city");
				String country =  resultat.getString("country");
				
				return new Address(id, street, number, zipCode, city, country);
			}
			logger.info("Succès - Récupération de l'addresse via l'id");
		} catch (SQLException e) {
			logger.error("Erreur - Récupération de l'addresse via l'id, erreur suivant : "+e.getMessage());
		}
		return null;
		
	}

	@Override
	public boolean updateAddress(int id, String street, String number, String zipCode, String city, String country) {
		String query = "UPDATE address SET street=?, number=?, zipCode=?, city=?, country=? WHERE id=?;";
		PreparedStatement preparedStatement;
		int resultat = 0;
		
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, street, Types.VARCHAR); 
			preparedStatement.setObject(2, number, Types.VARCHAR); 
			preparedStatement.setObject(3, zipCode, Types.VARCHAR); 
			preparedStatement.setObject(4, city, Types.VARCHAR);
			preparedStatement.setObject(5, country, Types.VARCHAR); 
			preparedStatement.setObject(6, id, Types.INTEGER); 
			resultat = UtilsBdd.executePreapredStatement(preparedStatement);
			logger.info("Succès - Mise à jour de l'adresse");
		} catch (SQLException e) {
			logger.error("Erreur - Mise à jour de l'adresse, erreur suivant : "+e.getMessage());
		}
		if(resultat==1){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteAddress(int id) {
		String query = "DELETE from address WHERE id=?;";
		PreparedStatement preparedStatement;
		int resultat = 0;
		
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setObject(1, id, Types.INTEGER); 
			resultat = UtilsBdd.executePreapredStatement(preparedStatement);
			logger.info("Succès - Suppression de l'adresse via son id");
		} catch (SQLException e) {
			logger.error("Erreur - Suppression de l'adresse via son id, erreur générée : "+e.getMessage());
		}
		if(resultat==1){
			return true;
		} else {
			return false;
		}
	}

}