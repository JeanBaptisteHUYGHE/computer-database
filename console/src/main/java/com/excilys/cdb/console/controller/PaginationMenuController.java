package com.excilys.cdb.console.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.excilys.cdb.console.enumeration.EnumPageMenuActions;
import com.excilys.cdb.console.view.PaginationMenuView;
import com.excilys.cdb.console.exception.InvalidActionChoiceException;
import com.excilys.cdb.console.exception.InvalidUserChoiceException;
import com.excilys.cdb.console.input.Input;
import com.excilys.cdb.core.Page;

@Controller
public abstract class PaginationMenuController {

	private boolean isRunning;
	protected Page page;
	protected PaginationMenuView paginationMenuView;
	protected Logger logger;
	
	public PaginationMenuController() {
		isRunning = true;
		paginationMenuView = new PaginationMenuView();
		logger = LoggerFactory.getLogger(PaginationMenuController.class);
	}
	
	protected void start(Page page) {
		this.page = page;
		isRunning = true;
		
		updateDisplay();
		while (isRunning) {
			paginationMenuView.drawInterface();
			readUserChoice();
		}
	}

	/**
	 * Read the user choice.
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
					case FIRST_PAGE:
						first();
						break;
					case NEXT_PAGE:
						next();
						break;
					case PREVIOUS_PAGE:
						previous();
						break;
					case LAST_PAGE:
						last();
						break;
					case EXIT:
						isRunning = false;
						break;
					default:
						throw new InvalidUserChoiceException();
				}
			} catch (InvalidUserChoiceException | InvalidActionChoiceException e) {
				isAValidUserChoice = false;
				paginationMenuView.drawMessage(e.getMessage());
			}
		}
	}
	
	private void first() {
		page.first();
		updateDisplay();
	}
	
	private void previous() {
		page.previous();
		updateDisplay();
	}
	
	private void next() {
		page.next();
		updateDisplay();
	}
	
	private void last() {
		page.last();
		updateDisplay();
	}
	
	protected abstract void updateDisplay();
}
