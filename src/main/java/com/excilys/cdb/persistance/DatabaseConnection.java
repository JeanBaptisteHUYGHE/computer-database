package com.excilys.cdb.persistance;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.dao.DatabaseConnectionException;

public class DatabaseConnection {
	
	private static final String DATABASE_PROPERTIES_FILE_PATH = "/database.properties";
	private static final String DATABASE_PROPERTY_NAME_DRIVER = "jdbc.driver";
	private static final String DATABASE_PROPERTY_NAME_URL = "jdbc.url";
	private static final String DATABASE_PROPERTY_NAME_LOGIN = "jdbc.username";
	private static final String DATABASE_PROPERTY_NAME_PASSWORD = "jdbc.password";
	
	private static String connectionDriver;
	private static String connectionUrl;
	private static String connectionLogin;
	private static String connectionPassword;
	
	private static boolean isInitialized = false;
	private static Connection connection = null;
	private static Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
	
	public static Connection getInstance() throws DatabaseConnectionException {
		try {
			if (!isInitialized) {
				loadDatabaseProperties();
			}
			if (connection == null || connection.isClosed()) {
				initializeConnection();
			}
			return connection;
		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}
	
	/**
	 * Load database properties from the properties file.
	 * @throws DatabaseConnectionException 
	 */
	private static void loadDatabaseProperties() throws DatabaseConnectionException {
		try {
			Properties properties = new Properties();
			properties.load(DatabaseConnection.class.getResourceAsStream(DATABASE_PROPERTIES_FILE_PATH));
			connectionDriver = properties.getProperty(DATABASE_PROPERTY_NAME_DRIVER);
			connectionUrl = properties.getProperty(DATABASE_PROPERTY_NAME_URL);
			connectionLogin = properties.getProperty(DATABASE_PROPERTY_NAME_LOGIN);
			connectionPassword = properties.getProperty(DATABASE_PROPERTY_NAME_PASSWORD);
		} catch (IOException e) {
			logger.error("Properties initialization: {} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}
	
	private static void initializeConnection() throws DatabaseConnectionException {
		try {
			Class.forName(connectionDriver);
			connection = DriverManager.getConnection(connectionUrl, connectionLogin, connectionPassword);
			
		} catch (ClassNotFoundException e) {
			logger.error("Database Driver not found: {} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
			
		} catch (SQLException e) {
			logger.error("Database getConnection: {} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}
}
