package com.excilys.cdb.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@Configuration
@PropertySource("classpath:application.properties")
@ImportResource("classpath:applicationContext.xml")
public class SpringWebConfig implements WebApplicationInitializer {

	private Logger logger;

	public SpringWebConfig() {
		logger = LoggerFactory.getLogger(SpringWebConfig.class);
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		logger.info("Config Spring web context...");
		
		@SuppressWarnings("resource")
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(SpringWebConfig.class);
		applicationContext.setServletContext(servletContext);
	}
        
	    
}