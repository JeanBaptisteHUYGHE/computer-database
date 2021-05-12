package com.excilys.cdb.ui.menu;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.ui.input.Input;
import com.excilys.cdb.ui.input.InputForm;

public class PrincipalMenu implements IMenu {
	
	private boolean isRunning;

	public PrincipalMenu() {
		isRunning = true;
		while (isRunning) {
			drawInterface();
			readUserChoice();
		}
	}
	
	/**
	 * Draw the menu interface
	 */
	private void drawInterface() {
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
						exit();
						break;
					default:
						throw new NoSuchElementException();
				}
			}
			catch (NoSuchElementException e) {
				isAValidUserChoice = false;
				System.out.println("Invalid choice, please retry");
			}
		}
	}
	
	/**
	 * Display all computers in database
	 */
	private void listAllComputers() {
		try {
			List<Computer> computersList = new ComputerService().getComputersList();
			System.out.println("ID\tNAME                                                      \t"
					+ "INTRODUCTION DATE\tDISCONTINUE DATE\tMANUFACTURER ID");
			for (Computer computer : computersList) {
				System.out.println(String.format("%-6s\t%-60s\t%-20s\t%-20s\t%s",
						computer.getId(), computer.getName(), computer.getIntroductionDate(),
						computer.getDiscontinueDate(), computer.getManufacturer().getId()
						)
					);
			}
		} catch (SQLException e) {
			System.err.println("An SQL error is occur: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Display all companies in database
	 */
	private void listAllCompanies() {
		try {
			List<Company> companiesList = new CompanyService().getCompaniesList();
			System.out.println("ID\tNAME");
			for (Company company : companiesList) {
				System.out.println(String.format("%s\t%s",company.getId(), company.getName()));
			}
		} catch (SQLException e) {
			System.err.println("An SQL error is occur: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Select a computer and switch to the computer menu
	 */
	private void selectComputer() {
		//IMenu nextMenu = new ComputerMenu();
		System.out.println("Computer id ?");
		Integer computerId = Input.readValidInteger();
		
		Computer computer = new Computer(computerId, "");
		try {
			computer = new ComputerService().getComputer(computer);
			new ComputerMenu(computer);
		} 
		catch(NoSuchElementException e) {
			System.out.println("This computer don't exist");
		}
		catch (SQLException e) {
			System.err.println("An SQL error is occur: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Add a new computer
	 */
	private void addNewComputer() {
		try {
			Computer newComputer = InputForm.readComputer();
			new ComputerService().addComputer(newComputer);
			System.out.println("Computer succesfully added");
		}
		catch (IllegalArgumentException e) {
			System.out.println("Operation canceled, argument(s) invalid(s): " + e.getMessage());
		}
		catch (SQLException e) {
			System.err.println("An SQL error is occur: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Exit this menu
	 */
	private void exit() {
		isRunning = false;
		System.out.println("Bye");
	}
}
