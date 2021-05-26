package com.excilys.cdb.persistance;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database {
	
	private static final String DATABASE_PROPERTIES_FILE_PATH = "/database.properties";
	private static final String DATABASE_PROPERTY_NAME_DRIVER = "jdbc.driver";
	private static final String DATABASE_PROPERTY_NAME_URL = "jdbc.url";
	private static final String DATABASE_PROPERTY_NAME_LOGIN = "jdbc.username";
	private static final String DATABASE_PROPERTY_NAME_PASSWORD = "jdbc.password";
	private static String connectionDriver = null;
	private static String connectionUrl = null;
	private static String connectionLogin = null;
	private static String connectionPassword = null;
	
	private static Database database = null;
	private static Connection connection = null;
	private static Logger logger = LoggerFactory.getLogger(Database.class);;
	
	/**
	 * Return the database connection.
	 * @return the database connection
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException {
		if (database == null) {
			database = new Database();
		}
		if (connection == null || connection.isClosed()) {
			try {
				Class.forName(connectionDriver);
			} catch (ClassNotFoundException e) {
				logger.error("Database Driver not found: {} in {}", e, e.getStackTrace());
			}
			connection = DriverManager.getConnection(connectionUrl, connectionLogin, connectionPassword);
		}
		return connection;
	}
	
	private Database() throws SQLException {
		logger.debug("Database()");
		try {
			loadProperties();
			logger.info("Database properties: {}, {}, {}", connectionUrl, connectionLogin, connectionPassword);
		} catch (IOException e) {
			logger.error("Database properties: {} in {}", e, e.getStackTrace());
			throw new SQLException("Error, cannot load database properties");
		}
	}
	
	/**
	 * Load database properties from the properties file.
	 * @throws IOException 
	 */
	private void loadProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(Database.class.getResourceAsStream(DATABASE_PROPERTIES_FILE_PATH));
		connectionDriver = properties.getProperty(DATABASE_PROPERTY_NAME_DRIVER);
		connectionUrl = properties.getProperty(DATABASE_PROPERTY_NAME_URL);
		connectionLogin = properties.getProperty(DATABASE_PROPERTY_NAME_LOGIN);
		connectionPassword = properties.getProperty(DATABASE_PROPERTY_NAME_PASSWORD);
	}
}
