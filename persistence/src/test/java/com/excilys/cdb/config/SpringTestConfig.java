package com.excilys.cdb.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.excilys.cdb.persistence.config.SpringDatabaseConfig;

@Configuration
@Import({SpringDatabaseConfig.class})
public class SpringTestConfig {

	private Logger logger;

	public SpringTestConfig() {
		logger = LoggerFactory.getLogger(SpringTestConfig.class);
		logger.info("SpringTestConfig()");
	}
	
}