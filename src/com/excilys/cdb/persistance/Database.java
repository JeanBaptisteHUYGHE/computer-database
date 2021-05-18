package com.excilys.cdb.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	private final static String CONNECTION_URL = "jdbc:mysql://localhost:3306/computer-database-db";
	private final static String CONNECTION_LOGIN = "admincdb";
	private final static String CONNECTION_PASSWORD = "qwerty1234";
	
	private static Connection connection = null;
	
	/**
	 * Return the database connection
	 * @return the database connection
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(CONNECTION_URL, CONNECTION_LOGIN, CONNECTION_PASSWORD);
		}
		return connection;
	}
	
	/**
	 * Close the database connection
	 */
	/*public static void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.err.println("Connection cannot be closed:");
			e.printStackTrace();
		}
		finally {
			connection = null;
		}
	}*/
}
