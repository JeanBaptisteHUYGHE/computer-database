package com.excilys.cdb.config;

import java.util.Locale;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
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

	private static final String DATABASE_PROPERTIES_FILE_PATH = "database.properties";
	private static final String LANGUAGE_PATH_AND_NAME = "lang/messages";

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
			HikariConfig hikariConfig = new HikariConfig("classpath:" + DATABASE_PROPERTIES_FILE_PATH);
			
			HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
			
			return hikariDataSource;
									
		} catch (PoolInitializationException e) {
			logger.error("HikariConfig initialization: {} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}
	
	@Bean
	public MessageSource messageSource() {
	    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	    messageSource.setBasenames(LANGUAGE_PATH_AND_NAME);
	    messageSource.setDefaultEncoding("UTF-8");
	    return messageSource;
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH);
		return sessionLocaleResolver;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor =  new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
}