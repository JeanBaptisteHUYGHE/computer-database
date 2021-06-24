package com.excilys.cdb.console.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.excilys.cdb.console.dto.ComputerCDto;
import com.excilys.cdb.console.dto.exception.InvalidComputerException;
import com.excilys.cdb.console.dto.mapper.ComputerCDtoMapper;
import com.excilys.cdb.console.dto.validator.ComputerCDtoValidator;
import com.excilys.cdb.console.exception.InvalidActionChoiceException;
import com.excilys.cdb.console.exception.InvalidUserChoiceException;
import com.excilys.cdb.console.enumeration.EnumPrincipalMenuActions;
import com.excilys.cdb.console.input.Input;
import com.excilys.cdb.console.input.InputForm;
import com.excilys.cdb.console.view.PrincipalMenuView;
import com.excilys.cdb.core.Computer;
import com.excilys.cdb.core.Page;
import com.excilys.cdb.core.Page.PageBuilder;
import com.excilys.cdb.persistence.exception.DatabaseConnectionException;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Controller
public class PrincipalMenuController {
	
	private boolean isRunning;
	private PrincipalMenuView principalMenuView;
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerCDtoMapper computerCDtoMapper;
	@Autowired
	private ComputerCDtoValidator computerCDtoValidator;
	@Autowired
	private ComputerPaginationMenuController computerPaginationMenuController;
	@Autowired
	private CompanyPaginationMenuController companyPaginationMenuController;
	@Autowired 
	private ComputerMenuController computerMenuController;
	private Logger logger;

	public PrincipalMenuController() {
		isRunning = true;
		principalMenuView = new PrincipalMenuView();
		logger = LoggerFactory.getLogger(PrincipalMenuController.class);		
	}
	
	public void start() {
		isRunning = true;
		logger.debug("start CLI");

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
					case DELETE_COMPANY:
						deleteCompany();
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
			computerPaginationMenuController.start(page);

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
			companyPaginationMenuController.start(page);

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
		computerMenuController.start(computerId);
	}
	
	/**
	 * Add a new computer.
	 */
	private void addNewComputer() {
		try {
			ComputerCDto computerCDto = InputForm.readComputerCDto();
			computerCDtoValidator.validate(computerCDto);
			Computer computer = computerCDtoMapper.fromComputerCDtoToComputer(computerCDto);
			
			computerService.addComputer(computer);
			
			principalMenuView.drawMessage("Computer succesfully added");
		} catch (InvalidComputerException e) {
			principalMenuView.drawMessage("Operation canceled. " + e.getMessage());
			
		} catch (DatabaseConnectionException e) {
			principalMenuView.drawError("Operation canceled. " + e.getMessage());
		}
	}
	
	private void deleteCompany() {
		principalMenuView.drawMessage("Company id ? (number or nothing)");
		String stringId = Input.readString();
		try {
			Integer companyId = Integer.valueOf(stringId);
			companyService.deleteCompanyById(companyId);
			principalMenuView.drawMessage("Operation succesfully deleted.");

		} catch (NumberFormatException e) {
			principalMenuView.drawMessage("Operation canceled. Invalid company id.");
			
		} catch (DatabaseConnectionException e) {
			principalMenuView.drawError("Operation canceled. " + e.getMessage());
		}

	}
}
