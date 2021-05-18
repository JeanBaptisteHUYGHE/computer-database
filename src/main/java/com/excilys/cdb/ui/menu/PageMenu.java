package com.excilys.cdb.ui.menu;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.ui.input.Input;
import com.excilys.cdb.ui.input.InputForm;
import com.excilys.cdb.ui.page.AbstPage;

public class PageMenu implements IMenu {

	private AbstPage page;
	private boolean isRunning;
	
	public PageMenu(AbstPage page) throws SQLException {
		this.page = page;
		isRunning = true;
		
		while (isRunning) {
			drawInterface();
			readUserChoice();
		}
		
	}
	
	/**
	 * Draw the menu interface
	 * @throws SQLException 
	 */
	private void drawInterface() throws SQLException {
		System.out.println("\n======================");
		System.out.println("[PAGE MENU]\n");
		this.page.drawPage();
		System.out.println();
		for (EnumPageMenuActions action : EnumPageMenuActions.values()) {
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
				EnumPageMenuActions action = EnumPageMenuActions.getAction(userChoice);
				isAValidUserChoice = true;
				switch (action) {
					case NEXT_PAGE:
						page.next();
						break;
					case PREVIOUS_PAGE:
						page.previous();
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
}
