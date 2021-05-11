package com.excilys.cdb.ui.menu;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistance.CompanyDao;
import com.excilys.cdb.persistance.ComputerDao;
import com.excilys.cdb.ui.Input;
import com.excilys.cdb.ui.InputForm;

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
		System.out.println("[" + EnumPrincipalMenuActions.LIST_ALL_COMPUTERS + "] List all computers");
		System.out.println("[" + EnumPrincipalMenuActions.LIST_ALL_COMPANIES + "] List all companies");
		System.out.println("[" + EnumPrincipalMenuActions.SELECT_COMPUTER    + "] Select a computer");
		System.out.println("[" + EnumPrincipalMenuActions.ADD_NEW_COMPUTER   + "] Add a new computers");
		System.out.println("[" + EnumPrincipalMenuActions.EXIT               +  "] Exit");
	}
	
	/**
	 * Read the user choice
	 */
	private void readUserChoice() {
		boolean isAValidUserChoice = false;
		Integer userChoice = null;
		while (!isAValidUserChoice) {
			userChoice = Input.readValidInteger();
			isAValidUserChoice = true;
			switch (userChoice) {
				case 1:
					listAllComputers();
					break;
				case 2:
					listAllCompanies();
					break;
				case 3:
					selectComputer();
					break;
				case 4:
					addNewComputer();
					break;
				case 9:
					exit();
					break;
				default:
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
			List<Computer> computersList = ComputerDao.getComputersList();
			System.out.println("ID\tNAME                                                      \t"
					+ "INTRODUCTION DATE\tDISCONTINUE DATE\tMANUFACTURER");
			for (Computer computer : computersList) {
				System.out.println(String.format("%-6s\t%-56s\t%-20s\t%-20s\t%s30",
						computer.getId(), computer.getName(), computer.getIntroductionDate(),
						computer.getDiscontinueDate(), computer.getManufacturer().getName()
						)
					);
			}
		} catch (SQLException e) {
			System.err.println("An error is occur: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Display all companies in database
	 */
	private void listAllCompanies() {
		try {
			List<Company> companiesList = CompanyDao.getCompaniesList();
			System.out.println("ID\tNAME");
			for (Company company : companiesList) {
				System.out.println(String.format("%s\t%s",company.getId(), company.getName()));
			}
		} catch (SQLException e) {
			System.err.println("An error is occur: " + e.getMessage());
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
			computer = ComputerDao.getComputer(computer);
			new ComputerMenu(computer);
		} 
		catch(NoSuchElementException e) {
			System.out.println("This computer don't exist");
		}
		catch (SQLException e) {
			System.err.println("SQL error: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Add a new computer
	 */
	private void addNewComputer() {
		try {
			Computer newComputer = InputForm.readComputer();
			ComputerDao.addComputer(newComputer);
			System.out.println("Computer succesfully added");
		}
		catch (IllegalArgumentException e) {
			System.out.println("Operation canceled, argument(s) invalid(s)");
		}
		catch (SQLException e) {
			System.err.println("SQL error: " + e.getMessage());
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
