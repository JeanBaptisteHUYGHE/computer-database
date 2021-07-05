package com.excilys.cdb.restapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.excilys.cdb.persistence.config.SpringDatabaseConfig;

@Configuration
@EnableWebMvc
@ComponentScan({
	"com.excilys.cdb.restapi", 
	"com.excilys.cdb.service"
	})
@Import({
	SpringDatabaseConfig.class,
	SpringRestApiSecurityConfig.class
	})
public class SpringRestApiConfig implements WebMvcConfigurer {

	private Logger logger;

	public SpringRestApiConfig() {
		logger = LoggerFactory.getLogger(SpringRestApiConfig.class);
		logger.info("SpringRestApiConfig()");
	}


	/**
	 * Permit to load static resources.
	 * @param registry the resource handler registry
	 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/index.html")
        .addResourceLocations("/index.html");
    }

}
