package com.excilys.cdb;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.cdb.cli.CommandLineInterface;


public class Main {

	/**
	 * Class use to run the application.
	 * @param args program arguments
	 */
	public static void main(String[] args) {
		
		Logger logger = LoggerFactory.getLogger(Main.class);
		
		try {
			@SuppressWarnings("resource")
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
			CommandLineInterface commandLineInterface = applicationContext.getBean(CommandLineInterface.class);
			commandLineInterface.start();
			
		} catch (Exception e) {
			logger.error("{} in {}", e, e.getStackTrace());
			e.printStackTrace();
		}
	}

	/**
	 * Display in console potential LogBack (the logger API) warnings or errors
	 * occur during the parsing of the configuration file.
	 */
	@SuppressWarnings("unused")
	private static void printLogbackLogOnConfiguration() {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		StatusPrinter.print(lc);
	}
}
