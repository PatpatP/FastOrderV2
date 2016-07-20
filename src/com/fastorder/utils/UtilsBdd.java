package com.fastorder.utils;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fastorder.manager.impl.AddressManagerImpl;
import com.fastorder.model.Address;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
public class UtilsBdd {
	
final static Logger logger = Logger.getLogger(UtilsBdd.class);
	
	public static Connection connectBDD(){
		try {
			System.out.println( "Chargement du driver..." );
			Class.forName( "com.mysql.jdbc.Driver" );
			System.out.println( "Driver chargï¿½ !" );
		} catch ( ClassNotFoundException e ) {
			System.out.println( "Erreur lors du chargement : le driver n'a pas ï¿½tï¿½ trouvï¿½ dans le classpath ! <br/>"
					+ e.getMessage() );
		}
		/* Connexion ï¿½ la base de donnï¿½es */
		String url = "jdbc:mysql://localhost:3306/fastorder";
		String user = "root";
		String mdp = "";
		Connection connexion = null;
		
		try {
			connexion = (Connection) DriverManager.getConnection( url, user, mdp );
			logger.info("Connexion effectuée");
			return connexion;
		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors de la connexion : " + e.getMessage());
		}
		return null;
	}
	
	public static int executePreapredStatement(PreparedStatement preparedStatement){
		try {
			int res = preparedStatement.executeUpdate();
			return res;
		} catch (SQLException e) {
			logger.error("Echec - Ex�cution de la requete");
		}
		return 0;
	}
	
	public static ResultSet selectPreapredStatement(PreparedStatement preparedStatement){
		ResultSet resultat = null;
		try {
			resultat = preparedStatement.executeQuery();
		} catch (SQLException e) {
			logger.error("Echec - Ex�cution de la requete select");
		}
		return resultat;
	}
	
	public static void main(String[] args) throws SQLException, IOException {
		AddressManagerImpl addressManager = new AddressManagerImpl(connectBDD());
		addressManager.createAddress("Yala", "Yala", "Yala", "Yala", "Yala");
		
		int id = addressManager.getAddressId("Yala", "Yala", "Yala", "Yala", "Yala");
		
		System.out.println(id);
		
		Address address = addressManager.getAddresssById(id);
		
		System.out.println(address);
	}
}