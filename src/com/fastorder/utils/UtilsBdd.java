package com.fastorder.utils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
public class UtilsBdd {
	
final static Logger logger = Logger.getLogger(UtilsBdd.class);
	
	public static Statement connectBDD(){
		try {
			System.out.println( "Chargement du driver..." );
			Class.forName( "com.mysql.jdbc.Driver" );
			System.out.println( "Driver charg� !" );
		} catch ( ClassNotFoundException e ) {
			System.out.println( "Erreur lors du chargement : le driver n'a pas �t� trouv� dans le classpath ! <br/>"
					+ e.getMessage() );
		}
		/* Connexion � la base de donn�es */
		String url = "jdbc:mysql://localhost:3306/fastorder";
		String user = "root";
		String password = "";
		Connection connexion = null;
		Statement statement = null;
		
		try {
			connexion = (Connection) DriverManager.getConnection( url, user, password );
			statement = (Statement) connexion.createStatement();
			logger.info("Connexion effectu�e");
			return statement;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Une erreur est survenue lors de la connexion : " + e.getMessage());
		}
		return null;
	}
	
	public static int insertQuery(Statement statement, String query){
		try {
			int res = statement.executeUpdate(query);
			System.out.println("R�sultat insert "+res);
			logger.info("R�sultat insert "+res);
			return res;
		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors de l'insert : " + e.getMessage());
		}
		return 0;
	}
	
	public static ResultSet selectQuery(Statement statement, String query){
		ResultSet resultat = null;
		try {
			resultat = statement.executeQuery(query);
			logger.info("R�sultat select "+resultat);
		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors du select : " + e.getMessage());
		}
		
		return resultat;
	}
	
	public static int updateQuery(Statement statement, String query){
		try {
			int res = statement.executeUpdate(query);
			logger.info("R�sultat update "+res);
			return res;
		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors de l'update : " + e.getMessage());
		}
		return 0;
	}
	
	public static int deleteQuery(Statement statement, String query){
		try {
			int res = statement.executeUpdate(query);
			logger.info("R�sultat delete "+res);
			return res;
		} catch (SQLException e) {
			logger.error("Une erreur est survenue lors du delete : " + e.getMessage());
		}
		return 0;
	}
}