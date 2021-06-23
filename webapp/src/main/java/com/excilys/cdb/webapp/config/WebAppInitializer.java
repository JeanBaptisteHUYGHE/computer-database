package com.excilys.cdb.webapp.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {
	
	private Logger logger;
	
	public WebAppInitializer() {
		logger = LoggerFactory.getLogger(WebAppInitializer.class);
	}

	/**
	 * Call by Spring to initialize the application.
	 * @param servletContext the servlet context
	 * @throws ServletException
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		logger.debug("WebAppInitializer.onStartup(...)");
		
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SpringWebAppConfig.class);
        context.setServletContext(servletContext);
        
        servletContext.addFilter("securityFilter", new DelegatingFilterProxy("springSecurityFilterChain"))
        	.addMappingForUrlPatterns(null, false, "/*");

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("WebApp Dispatcher", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

}
