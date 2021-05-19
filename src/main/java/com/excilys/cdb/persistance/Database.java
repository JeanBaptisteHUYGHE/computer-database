package com.excilys.cdb.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/computer-database-db";
	private static final String CONNECTION_LOGIN = "admincdb";
	private static final String CONNECTION_PASSWORD = "qwerty1234";
	
	private static Connection connection = null;
	
	/**
	 * Return the database connection.
	 * @return the database connection
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(CONNECTION_URL, CONNECTION_LOGIN, CONNECTION_PASSWORD);
		}
		return connection;
	}
}
