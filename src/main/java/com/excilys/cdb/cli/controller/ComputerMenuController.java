package com.excilys.cdb.cli.controller;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.excilys.cdb.cli.enumeration.EnumComputerMenuActions;
import com.excilys.cdb.cli.input.Input;
import com.excilys.cdb.cli.input.InputForm;
import com.excilys.cdb.cli.view.ComputerMenuView;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.mapper.ComputerDtoMapper;
import com.excilys.cdb.dto.validator.ComputerDtoValidator;
import com.excilys.cdb.exception.cli.InvalidActionChoiceException;
import com.excilys.cdb.exception.cli.InvalidUserChoiceException;
import com.excilys.cdb.exception.dao.ComputerNotFoundException;
import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.excilys.cdb.exception.dto.InvalidComputerException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;

@Controller
public class ComputerMenuController {

	private boolean isRunning;
	private ComputerMenuView computerMenuView;
	@Autowired
	private ComputerService computerService;
	@Autowired
	private ComputerDtoMapper computerDtoMapper;
	@Autowired
	private ComputerDtoValidator computerDtoValidator;
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
				ComputerDto computerDto = computerDtoMapper.fromComputerToComputerDto(computer);
				computerMenuView.drawInterface(computerDto);
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
			ComputerDto computerDto = InputForm.readComputerDto();
			computerDto.setId(computer.getId().get().toString());
			
			computerDtoValidator.validate(computerDto);
			
			computer = computerDtoMapper.fromComputerDtoToComputer(computerDto);
			
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
