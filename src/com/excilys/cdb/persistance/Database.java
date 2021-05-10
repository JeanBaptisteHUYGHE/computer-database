package com.excilys.cdb.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	private static Connection connection = null;
	
	/**
	 * Initialize the database connection
	 * @throws SQLException 
	 */
	private static void initConnection() throws SQLException {
      connection = DriverManager.getConnection(
    		  "jdbc:mysql://localhost:3306/computer-database-db",
    		  "admincdb", "qwerty1234");
	}
	
	/**
	 * Return the database connection
	 * @return the database connection
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException {
		if (connection == null) {
			initConnection();
		}
		return connection;
	}
	
	/**
	 * Close the database connection
	 */
	public static void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.err.println("Connection cannot be closed:");
			e.printStackTrace();
		}
	}
}
