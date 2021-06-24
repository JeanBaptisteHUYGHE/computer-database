package com.excilys.cdb.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Computer;
import com.excilys.cdb.persistence.exception.ComputerNotFoundException;
import com.excilys.cdb.persistence.exception.DatabaseConnectionException;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.webapp.dto.CompanyWDto;
import com.excilys.cdb.webapp.dto.ComputerWDto;
import com.excilys.cdb.webapp.dto.exception.InvalidComputerException;
import com.excilys.cdb.webapp.dto.mapper.CompanyWDtoMapper;
import com.excilys.cdb.webapp.dto.mapper.ComputerWDtoMapper;
import com.excilys.cdb.webapp.dto.validator.ComputerWDtoValidator;
import com.excilys.cdb.webapp.exception.MissingOrInvalidComputerIdException;




@Controller
@RequestMapping("/editComputer")
public class EditComputerController {
	
	private Logger logger;
	
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyWDtoMapper companyWDtoMapper;
	@Autowired
	private ComputerWDtoMapper computerWDtoMapper;
	@Autowired
	private ComputerWDtoValidator computerWDtoValidator;
	@Autowired
	private ComputerService computerService;
	
	public EditComputerController() {
		logger = LoggerFactory.getLogger(EditComputerController.class);
	}
	
	
	@GetMapping
	public String getMapping(
			@RequestParam(name = "id", required = false, defaultValue = "") Integer computerId,
			@RequestParam(name = "errorsList", required = false, defaultValue = "") List<String> errorsList,
			Model model) {
		logger.debug("getMapping(...)");
		
		List<CompanyWDto> companiesWDtoList = getCompaniesWDtoList(errorsList);
		
		try {
			if (computerId == null) {
				throw new MissingOrInvalidComputerIdException();
			}
			Computer computer = computerService.getComputerById(computerId);
			ComputerWDto computerDto = computerWDtoMapper.fromComputerToComputerWDto(computer);
			model.addAttribute("computerDto", computerDto);
			
		} catch (MissingOrInvalidComputerIdException | ComputerNotFoundException | DatabaseConnectionException e) {
			errorsList.add(e.getMessage());
		}
		
		model.addAttribute("errorsList", errorsList);
		model.addAttribute("companiesDtoList", companiesWDtoList);
		return "editComputer";
	}
	
	@PostMapping
	public String postMapping(@ModelAttribute("computerDto") ComputerWDto computerWDto,
			Model model) {
		logger.debug("postMapping({})", computerWDto);
		
		List<String> errorsList = new ArrayList<String>(0);
		
		try {
			computerWDtoValidator.validate(computerWDto);
			Computer computer = computerWDtoMapper.fromComputerWDtoToComputer(computerWDto);
			computerService.updateComputer(computer);
			return "/dashboard";
		} catch (InvalidComputerException e) {
			errorsList.add(e.getMessage());
		} catch (DatabaseConnectionException e) {
			errorsList.add("Cannot edit this computer. " + e.getMessage());
		}
		model.addAttribute("errorsList", errorsList);
		model.addAttribute("id", computerWDto.getId());
		return "redirect:/editComputer";
	}
	
	private List<CompanyWDto> getCompaniesWDtoList(List<String> errorsList) {
		List<CompanyWDto> companiesWDtoList;
		
		try {
			List<Company> companiesList;
			companiesList = companyService.getCompaniesList();
			companiesWDtoList = companyWDtoMapper.fromCompaniesListToCompaniesWDtoList(companiesList);
		} catch (DatabaseConnectionException e) {
			companiesWDtoList = new ArrayList<CompanyWDto>(0);
			errorsList.add("Cannot get companies list. " + e.getMessage());
		}
		return companiesWDtoList;
	}
}

