package com.excilys.cdb.ui.menu;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.ui.input.Input;
import com.excilys.cdb.ui.input.InputForm;
import com.excilys.cdb.ui.page.CompaniesPage;
import com.excilys.cdb.ui.page.ComputersPage;

public class PrincipalMenu implements IMenu {
	
	private boolean isRunning;
	private Logger logger;

	public PrincipalMenu() {
		isRunning = true;
		logger = LoggerFactory.getLogger(PrincipalMenu.class);
		
		while (isRunning) {
			drawInterface();
			readUserChoice();
		}
	}
	
	/**
	 * Draw the menu interface
	 */
	private void drawInterface() {
		logger.debug("drawInterface()");
		System.out.println("\n======================");
		System.out.println("[PRINCIPAL MENU]");
		for (EnumPrincipalMenuActions action : EnumPrincipalMenuActions.values()) {
			System.out.println(action);
		}
	}
	
	/**
	 * Read the user choice
	 */
	private void readUserChoice() {
		logger.debug("readUserChoice()");
		boolean isAValidUserChoice = false;
		Integer userChoice = null;
		while (!isAValidUserChoice) {
			try {
				userChoice = Input.readValidInteger();
				EnumPrincipalMenuActions action = EnumPrincipalMenuActions.getAction(userChoice);
				isAValidUserChoice = true;
				switch (action) {
					case LIST_ALL_COMPUTERS:
						listAllComputers();
						break;
					case LIST_ALL_COMPANIES:
						listAllCompanies();
						break;
					case SELECT_COMPUTER:
						selectComputer();
						break;
					case ADD_NEW_COMPUTER:
						addNewComputer();
						break;
					case EXIT:
						isRunning = false;
						System.out.println("Bye");
						break;
					default:
						throw new NoSuchElementException();
				}
			}
			catch (NoSuchElementException e) {
				isAValidUserChoice = false;
				System.out.println("Invalid choice, please retry");
				logger.info("Invalid user choice)");
			}
		}
	}
	
	/**
	 * Display all computers in database
	 */
	private void listAllComputers() {
		logger.debug("listAllComputers()");
		try {
			new PageMenu(new ComputersPage());
		} catch (SQLException e) {
			System.err.println("An error has appeared");
			logger.error("{} in {}", e, e.getStackTrace());
		}
	}
	
	/**
	 * Display all companies in database
	 */
	private void listAllCompanies() {
		logger.debug("listAllCompanies()");
		try {
			new PageMenu(new CompaniesPage());
		} catch (SQLException e) {
			System.err.println("An error has appeared");
			logger.error("{} in {}", e, e.getStackTrace());
		}
	}
	
	/**
	 * Select a computer and switch to the computer menu
	 */
	private void selectComputer() {
		logger.debug("selectComputer()");
		System.out.println("Computer id ?");
		Integer computerId = Input.readValidInteger();
		
		Computer computer = new Computer.ComputerBuilder(computerId, "").build();
		try {
			computer = ComputerService.getInstance().getComputer(computer);
			new ComputerMenu(computer);
		} 
		catch(NoSuchElementException e) {
			System.out.println("This computer don't exist");
			logger.info("User selected a computer which don't exist");
		}
		catch (SQLException e) {
			System.err.println("An error has appeared");
			logger.error("{} in {}", e, e.getStackTrace());
		}
	}
	
	/**
	 * Add a new computer
	 */
	private void addNewComputer() {
		logger.debug("addNewComputer()");
		try {
			Computer newComputer = InputForm.readComputer();
			ComputerService.getInstance().addComputer(newComputer);
			System.out.println("Computer succesfully added");
		}
		catch (IllegalArgumentException e) {
			System.out.println("Operation canceled, argument(s) invalid(s): " + e.getMessage());
			logger.info("Operation canceled, argument(s) invalid(s) {} in {}", e, e.getStackTrace());
		}
		catch (SQLException e) {
			System.err.println("An error has appeared");
			logger.error("{} in {}", e, e.getStackTrace());
		}
	}
}
