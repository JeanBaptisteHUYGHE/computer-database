package com.excilys.cdb.ui.menu;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.ui.input.Input;
import com.excilys.cdb.ui.input.InputForm;

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
		for (EnumComputerMenuActions action : EnumComputerMenuActions.values()) {
			System.out.println(action);
		}
	}

	/**
	 * Draw computer representation
	 */
	private void drawComputer() {
		System.out.println("Selected Computer:"
			+ "\n\tId: " + computer.getId().orElse(null)
			+ "\n\tName: " + computer.getName()
			+ "\n\tIntroduction date: " + computer.getIntroductionDate().orElse(null)
			+ "\n\tDiscontinue date: " + computer.getDiscontinueDate().orElse(null));
		if (computer.getManufacturer().isPresent()) {
			System.out.println("\tManufacturer:"
				+ "\n\t\tManufacturer id: " + computer.getManufacturer().get().getId()
				+ "\n\t\tManufacturer name: " + computer.getManufacturer().get().getName()
				);
		}
		else {
			System.out.println("\tNo Manufacturer");
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
				EnumComputerMenuActions action = EnumComputerMenuActions.getAction(userChoice);
				isAValidUserChoice = true;
				switch (action) {
					case EDIT_COMPUTER:
						editComputer();
						break;
					case DELETE_COMPUTER:
						deleteComputer();
						break;
					case EXIT:
						isRunning = false;
						break;
					default:
						throw new NoSuchElementException();
				}
			}
			catch(NoSuchElementException e) {
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
			computer.setIntroductionDate(newComputer.getIntroductionDate().get());
			computer.setDiscontinueDate(newComputer.getDiscontinueDate().get());
			computer.setManufacturer(newComputer.getManufacturer().get());
			ComputerService computerService = new ComputerService();
			computerService.updateComputer(computer);
			computer = computerService.getComputer(computer);
			System.out.println("Computer updated successfully");
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
	 * Delete the computer in database
	 */
	private void deleteComputer() {
		try {
			new ComputerService().deleteComputer(computer);
			System.out.println("Computer deleted successfully");
		} catch (SQLException e) {
			System.err.println("An SQL error is occur: " + e.getMessage());
			e.printStackTrace();
		}
		isRunning = false;
	}
}
