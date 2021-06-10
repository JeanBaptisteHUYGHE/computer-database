package com.excilys.cdb.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool.PoolInitializationException;

@Configuration
@EnableWebMvc
@PropertySource("classpath:application.properties")
@ImportResource("classpath:applicationContext.xml")
public class SpringWebConfig extends AbstractAnnotationConfigDispatcherServletInitializer implements WebMvcConfigurer {

	private static final String DATABASE_PROPERTIES_FILE_PATH = "/database.properties";

	private Logger logger;

	public SpringWebConfig() {
		logger = LoggerFactory.getLogger(SpringWebConfig.class);
	}
	
	@Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {SpringWebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
            .addResourceLocations("/static/");
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