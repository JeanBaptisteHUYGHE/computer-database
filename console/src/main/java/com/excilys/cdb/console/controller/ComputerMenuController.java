package com.excilys.cdb.console.controller;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.excilys.cdb.console.enumeration.EnumComputerMenuActions;
import com.excilys.cdb.console.input.Input;
import com.excilys.cdb.console.input.InputForm;
import com.excilys.cdb.console.view.ComputerMenuView;
import com.excilys.cdb.console.dto.ComputerCDto;
import com.excilys.cdb.console.dto.exception.InvalidComputerException;
import com.excilys.cdb.console.dto.mapper.ComputerCDtoMapper;
import com.excilys.cdb.console.dto.validator.ComputerCDtoValidator;
import com.excilys.cdb.console.exception.InvalidActionChoiceException;
import com.excilys.cdb.console.exception.InvalidUserChoiceException;
import com.excilys.cdb.persistence.exception.ComputerNotFoundException;
import com.excilys.cdb.persistence.exception.DatabaseConnectionException;
import com.excilys.cdb.core.Computer;
import com.excilys.cdb.service.ComputerService;

@Controller
public class ComputerMenuController {

	private boolean isRunning;
	private ComputerMenuView computerMenuView;
	@Autowired
	private ComputerService computerService;
	@Autowired
	private ComputerCDtoMapper computerCDtoMapper;
	@Autowired
	private ComputerCDtoValidator computerCDtoValidator;
	private Computer computer;
	private Logger logger;
	
	public ComputerMenuController() {
		logger = LoggerFactory.getLogger(ComputerMenuController.class);
		isRunning = true;
		computerMenuView = new ComputerMenuView();
	}
	
	public void start(Integer computerId) {
		isRunning = true;
		try {			
			while (isRunning) {
				computer = computerService.getComputerById(computerId);
				ComputerCDto computerCDto = computerCDtoMapper.fromComputerToComputerCDto(computer);
				computerMenuView.drawInterface(computerCDto);
				readUserChoice();
			}
			
		} catch (ComputerNotFoundException e) {
			computerMenuView.drawMessage("It doesn't exist a computer with this id.");
			
		} catch (DatabaseConnectionException e) {
			computerMenuView.drawError("Error while getting the computers list. " + e.getMessage());
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
						throw new InvalidUserChoiceException();
				}
			} catch (InvalidUserChoiceException | InvalidActionChoiceException e) {
				computerMenuView.drawMessage(e.getMessage());
				isAValidUserChoice = false;
			}
		}
	}
	
	/**
	 * Edit the computer.
	 */
	private void editComputer() {
		computerMenuView.drawMessage("Enter new values:");
		try {
			ComputerCDto computerCDto = InputForm.readComputerCDto();
			computerCDto.setId(computer.getId().get().toString());
			
			computerCDtoValidator.validate(computerCDto);
			
			computer = computerCDtoMapper.fromComputerCDtoToComputer(computerCDto);
			
			computerService.updateComputer(computer);
			
			computer = computerService.getComputerById(computer.getId().get());
			
			computerMenuView.drawMessage("Computer updated successfully");
			
		} catch (InvalidComputerException e) {
			computerMenuView.drawMessage("Operation canceled. " + e.getMessage());
			
		} catch (ComputerNotFoundException | DatabaseConnectionException e) {
			computerMenuView.drawError("Operation canceled. " + e.getMessage());
		}
	}
	
	/**
	 * Delete the computer in database.
	 */
	private void deleteComputer() {
		try {
			computerService.deleteComputer(computer.getId().get());
			computerMenuView.drawMessage("Computer deleted successfully");
			isRunning = false;
			
		} catch (DatabaseConnectionException e) {
			computerMenuView.drawError("Operation canceled. " + e.getMessage());
			
		} catch (NoSuchElementException e) {
			computerMenuView.drawError("Operation canceled: The computer don't have an id");
			logger.error("{} in {}", e, e.getStackTrace());
		}
	}
}
