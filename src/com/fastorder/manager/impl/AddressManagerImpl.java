package com.fastorder.manager.impl;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.fastorder.manager.IAddressManager;
import com.fastorder.model.Address;
import com.fastorder.utils.Utils;
import com.mysql.jdbc.Statement;

public class AddressManagerImpl implements IAddressManager{

	private Statement statement;
	
	final static Logger logger = Logger.getLogger(AddressManagerImpl.class);

	public AddressManagerImpl(Statement statement) {
		this.statement = statement;
	}

	@Override
	public boolean createAddress(String street, String number, String zipCode, String city, String country) {
		String query = "Insert into address (street,number,zipCode,city,country) VALUES('"+street+"','"+number+"','"+zipCode+"','"+city+"','"+country+"');";
		int res = Utils.insertQuery(statement, query);
		System.out.println(query);
		if(res==1){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getAddressId(String street, String number, String zipCode, String city, String country) {
		int addressId = 0;
		ResultSet resultat;
		String query = "Select * from address WHERE street='"+street+"' AND number='"+number+"' AND zipCode='"+zipCode+"' AND city='"+city+"' AND country='"+country+"';";
		resultat = Utils.selectQuery(statement, query);

		try {
			while(resultat.next()){
				addressId = resultat.getInt("id");
			}
			logger.info("ID de l'adresse récupéré");
		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors de la récupération de l'ID de l'adresse : " + e.getMessage());
		}

		return addressId;
	}

	@Override
	public Address getAddresssById(int id) {
		String query = "Select * from address where id='"+id+"';";
		ResultSet resultat;
		resultat = Utils.selectQuery(statement, query);

		try {
			while(resultat.next()){
				String street =  resultat.getString("street");
				String number =  resultat.getString("number");
				String zipCode =  resultat.getString("zipCode");
				String city =  resultat.getString("city");
				String country =  resultat.getString("country");

				logger.info("Adresse récupérée");
				return new Address(street, number, zipCode, city, country);
			}
		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors de la récupération de l'adresse : " + e.getMessage());
		}

		return null;
	}

	@Override
	public boolean updateAddress(int id, String street, String number, String zipCode, String city, String country) {
		String query = "UPDATE address SET street='"+street+"', number='"+number+"', zipCode='"+zipCode+"', city='"+city+"', country='"+country+"' WHERE id='"+id+"';";
		int res = Utils.updateQuery(statement, query);
		if(res==1){
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public boolean deleteAddress(int id) {
		String query = "DELETE FROM ADDRESS where id='"+id+"';";
		int res = Utils.deleteQuery(statement, query);
		if(res==1){
			return true;
		} else {
			return false;
		}
	}

}
