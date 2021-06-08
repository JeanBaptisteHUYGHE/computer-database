package com.excilys.cdb.persistance;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool.PoolInitializationException;

@Repository
public class DatabaseConnection {
	
	private static final String DATABASE_PROPERTIES_FILE_PATH = "/database.properties";
	
	private Logger logger;
	
	private HikariConfig hikariConfig;
    private HikariDataSource hikariDataSource;
    
    /**
     * Constructor.
     * @throws DatabaseConnectionException
     */
    public DatabaseConnection() throws DatabaseConnectionException {
    	logger = LoggerFactory.getLogger(DatabaseConnection.class);
    	loadDatabaseProperties();
    }
	
    /**
     * Return the database connection.
     * @return the database connection
     * @throws DatabaseConnectionException
     */
	public Connection getConnection() throws DatabaseConnectionException {
		try {
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
	private void loadDatabaseProperties() throws DatabaseConnectionException {
		try {
			hikariConfig = new HikariConfig(DATABASE_PROPERTIES_FILE_PATH);
			
			hikariDataSource = new HikariDataSource(hikariConfig);
									
		} catch (PoolInitializationException e) {
			logger.error("HikariConfig initialization: {} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}
}
