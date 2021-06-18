package com.excilys.cdb.console.config;

import com.excilys.cdb.persistence.config.SpringDatabaseConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:application.properties")
@Import(SpringDatabaseConfig.class)
@ComponentScan(basePackages = { 
		"com.excilys.cdb.console",
		"com.excilys.cdb.service"
		})
public class SpringCliConfig {

	private Logger logger;

	public SpringCliConfig() {
		logger = LoggerFactory.getLogger(SpringCliConfig.class);
		logger.info("SpringCliConfig()");
	}

}