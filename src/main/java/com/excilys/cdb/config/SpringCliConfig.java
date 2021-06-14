package com.excilys.cdb.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool.PoolInitializationException;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = { 
		"com.excilys.cdb.cli",
		"com.excilys.cdb.dto",
		"com.excilys.cdb.persistance",
		"com.excilys.cdb.service"
		})
public class SpringCliConfig {

	private static final String DATABASE_PROPERTIES_FILE_PATH = "/database.properties";

	private Logger logger;

	public SpringCliConfig() {
		logger = LoggerFactory.getLogger(SpringCliConfig.class);
	}
	
	@Bean
	public DataSource getDataSource() throws DatabaseConnectionException {
		try {
			HikariConfig hikariConfig = new HikariConfig(DATABASE_PROPERTIES_FILE_PATH);
			
			HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
			
			return hikariDataSource;
									
		} catch (PoolInitializationException e) {
			logger.error("HikariConfig initialization: {} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}
}