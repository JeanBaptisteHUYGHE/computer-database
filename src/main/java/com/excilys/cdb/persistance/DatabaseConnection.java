package com.excilys.cdb.persistance;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool.PoolInitializationException;

public class DatabaseConnection {
	
	private static final String DATABASE_PROPERTIES_FILE_PATH = "/database.properties";
	
	private static boolean isInitialized = false;
	private static Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
	
	private static HikariConfig hikariConfig;
    private static HikariDataSource hikariDataSource;
	
	public static Connection getInstance() throws DatabaseConnectionException {
		try {
			if (!isInitialized) {
				loadDatabaseProperties();
			}
			return hikariDataSource.getConnection();
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
			hikariConfig = new HikariConfig(DATABASE_PROPERTIES_FILE_PATH);
			
			hikariDataSource = new HikariDataSource(hikariConfig);
						
			isInitialized = true;
			
		} catch (PoolInitializationException e) {
			logger.error("HikariConfig initialization: {} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}
}
