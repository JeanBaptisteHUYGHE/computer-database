package com.excilys.cdb;

import com.excilys.cdb.ui.CommandLineInterface;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {

	/**
	 * Class use to run the application
	 * @param args program arguments
	 */
	public static void main(String[] args) {	

		/*
		System.out.println(new Computer(1, "ROG Strix GL703VM"));		
		System.out.println(new Computer("ROG Strix GL703VM", new LocalDate(), new LocalDate(), new Company("ASUS")));
		 */

		//try {
		/*
			System.out.println(CompanyDao.getCompaniesList());
			System.out.println(ComputerDao.getComputersList());

			Computer newComputer = new Computer("My beautifull PC", null, null, 
					new Company(1, "My beautifull company"));
			System.out.println(newComputer);
			ComputerDao.addComputer(newComputer);
			System.out.println(newComputer);
		 */
		/*Computer myComputer = new Computer(577, "My beautifull PC", null, null, 
					new Company(1, "My beautifull company"));
			ComputerDao.deleteComputer(myComputer);
		 */

		/*
			// get
			Computer myComputer = new Computer(1, "My beautifull PC", null, null, 
			new Company(1, "My beautifull company"));
			System.out.println(ComputerDao.getComputer(myComputer));
		 */

		/*
			// update
			LocalDate ld1 = LocalDate.of(2018, 04, 15);
			LocalDate ld2 = LocalDate.now();
			Computer myComputer = new Computer(579, "ThinkPad", ld1, ld2, 
				new Company(2, "My beautifull company"));
			ComputerDao.updateComputer(myComputer);
		 */


		/*} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}*/



		printLogbackLogOnConfiguration();

		Logger logger = LoggerFactory.getLogger(Main.class);
		try {
			CommandLineInterface cli = new CommandLineInterface();
		}
		catch (Exception e) {
			logger.error("{} in {}", e, e.getStackTrace());
			e.printStackTrace();
		}
	}

	/**
	 * Display in console potential LogBack (the logger API) warnings or errors
	 * occur during the parsing of the configuration file.
	 */
	private static void printLogbackLogOnConfiguration() {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		StatusPrinter.print(lc);
	}
}
