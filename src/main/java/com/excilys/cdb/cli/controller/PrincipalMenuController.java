package com.excilys.cdb.cli.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.cli.InvalidActionChoiceException;
import com.excilys.cdb.exception.cli.InvalidUserChoiceException;
import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.excilys.cdb.exception.dto.InvalidComputerException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.Page.PageBuilder;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.cli.enumeration.EnumPrincipalMenuActions;
import com.excilys.cdb.cli.input.Input;
import com.excilys.cdb.cli.input.InputForm;
import com.excilys.cdb.cli.view.PrincipalMenuView;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.mapper.ComputerDtoMapper;
import com.excilys.cdb.dto.validator.ComputerDtoValidator;

public class PrincipalMenuController {
	
	private boolean isRunning;
	private PrincipalMenuView principalMenuView;
	private ComputerService computerService;
	private CompanyService companyService;
	private ComputerDtoMapper computerDtoMapper;
	private ComputerDtoValidator computerDtoValidator;
	private Logger logger;

	public PrincipalMenuController() {
		isRunning = true;
		principalMenuView = new PrincipalMenuView();
		computerService = ComputerService.getInstance();
		companyService = CompanyService.getInstance();
		computerDtoMapper = ComputerDtoMapper.getInstance();
		computerDtoValidator = ComputerDtoValidator.getInstance();
		logger = LoggerFactory.getLogger(PrincipalMenuController.class);
		
		logger.debug("new CLI");
		
		while (isRunning) {
			principalMenuView.drawInterface();
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
						throw new InvalidUserChoiceException();
				}
			} catch (InvalidUserChoiceException | InvalidActionChoiceException e) {
				isAValidUserChoice = false;
				principalMenuView.drawMessage(e.getMessage());
			}
		}
	}
	
	/**
	 * Display all computers in database.
	 */
	private void listAllComputers() {
		try {
			Integer computersCount = computerService.getComputersCount();
			PageBuilder pageBuilder = new PageBuilder();
			pageBuilder.withElementsCount(computersCount);
			Page page = pageBuilder.build();
			new ComputerPaginationMenuController(page);

		} catch (DatabaseConnectionException e) {
			principalMenuView.drawError("Error while getting the computers list. " + e.getMessage());
		}
	}
	
	/**
	 * Display all companies in database.
	 */
	private void listAllCompanies() {
		try {
			Integer companiesCount = companyService.getCompaniesCount();
			PageBuilder pageBuilder = new PageBuilder();
			pageBuilder.withElementsCount(companiesCount);
			Page page = pageBuilder.build();
			new CompanyPaginationMenuController(page);

		} catch (DatabaseConnectionException e) {
			principalMenuView.drawError("Error while getting the companies list. " + e.getMessage());
		}
	}
	
	/**
	 * Select a computer and switch to the computer menu.
	 */
	private void selectComputer() {
		principalMenuView.drawMessage("Computer id ?");
		Integer computerId = Input.readValidInteger();
		
		new ComputerMenuController(computerId);
	}
	
	/**
	 * Add a new computer.
	 */
	private void addNewComputer() {
		try {
			ComputerDto computerDto = InputForm.readComputerDto();
			computerDtoValidator.validate(computerDto);
			Computer computer = computerDtoMapper.fromComputerDtoToComputer(computerDto);
			
			computerService.addComputer(computer);
			
			principalMenuView.drawMessage("Computer succesfully added");
		} catch (InvalidComputerException e) {
			principalMenuView.drawMessage("Operation canceled. " + e.getMessage());
			
		} catch (DatabaseConnectionException e) {
			principalMenuView.drawError("Operation canceled. " + e.getMessage());
		}
	}
}
