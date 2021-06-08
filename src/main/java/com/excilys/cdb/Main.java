package com.excilys.cdb;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.cli.CommandLineInterface;
import com.excilys.cdb.config.SpringWebConfig;


public class Main {

	/**
	 * Class use to run the application.
	 * @param args program arguments
	 */
	public static void main(String[] args) {
		
		Logger logger = LoggerFactory.getLogger(Main.class);
		
		try {
			runCli();
			
		} catch (Exception e) {
			logger.error("{} in {}", e, e.getStackTrace());
			e.printStackTrace();
		}
	}
	
	/**
	 * Run the Command line user interface.
	 */
	public static void runCli() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringWebConfig.class);
		CommandLineInterface commandLineInterface = applicationContext.getBean(CommandLineInterface.class);
		commandLineInterface.start();
		((ConfigurableApplicationContext) applicationContext).close();
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
