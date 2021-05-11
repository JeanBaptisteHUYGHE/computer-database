package com.excilys.cdb.ui.menu;

import java.sql.SQLException;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistance.ComputerDao;
import com.excilys.cdb.ui.Input;
import com.excilys.cdb.ui.InputForm;

public class ComputerMenu implements IMenu {

	private Computer computer;
	private boolean isRunning;
	
	public ComputerMenu(Computer computer) {
		this.computer = computer;
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
		System.out.println("[COMPUTER MENU]");
		drawComputer();
		System.out.println();
		System.out.println("[1] Edit");
		System.out.println("[2] Delete");
		System.out.println("[3] Exit");
	}

	/**
	 * Draw computer representation
	 */
	private void drawComputer() {
		System.out.println("Selected Computer:"
				+ "\n\tId: " + computer.getId()
				+ "\n\tName: " + computer.getName()
				+ "\n\tIntroduction date: " + computer.getIntroductionDate()
				+ "\n\tDiscontinue date: " + computer.getDiscontinueDate()
				+ "\n\tManufacturer:"
				+ "\n\t\tManufacturer id: " + computer.getManufacturer().getId()
				+ "\n\t\tManufacturer name: " + computer.getManufacturer().getName()
				);
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
					editComputer();
					break;
				case 2:
					deleteComputer();
					break;
				case 3:
					exit();
					break;
				default:
					isAValidUserChoice = false;
					System.out.println("Invalid choice, please retry");
			}
		}
	}
	
	/**
	 * Edit the computer
	 */
	private void editComputer() {
		System.out.println("Enter new values:");
		try {
			Computer newComputer = InputForm.readComputer();
			computer.setName(newComputer.getName());
			computer.setIntroductionDate(newComputer.getIntroductionDate());
			computer.setDiscontinueDate(newComputer.getDiscontinueDate());
			computer.setManufacturer(newComputer.getManufacturer());
			ComputerDao.updateComputer(computer);
			computer = ComputerDao.getComputer(computer);
			System.out.println("Computer updated successfully");
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
	 * Delete the computer in database
	 */
	private void deleteComputer() {
		try {
			ComputerDao.deleteComputer(computer);
			System.out.println("Computer deleted successfully");
		} catch (SQLException e) {
			System.err.println("An SQL error is occur: " + e.getMessage());
			e.printStackTrace();
		}
		exit();
	}

	/**
	 * Exit this menu
	 */
	private void exit() {
		isRunning = false;
	}

	

}
