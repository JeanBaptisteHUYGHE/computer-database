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
import com.excilys.cdb.persistence.exception.DatabaseConnectionException;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.webapp.dto.CompanyWDto;
import com.excilys.cdb.webapp.dto.ComputerWDto;
import com.excilys.cdb.webapp.dto.ComputerWDto.ComputerWDtoBuilder;
import com.excilys.cdb.webapp.dto.mapper.CompanyWDtoMapper;
import com.excilys.cdb.webapp.dto.mapper.ComputerWDtoMapper;
import com.excilys.cdb.webapp.dto.validator.ComputerWDtoValidator;
import com.excilys.cdb.webapp.dto.exception.InvalidComputerException;


@Controller
@RequestMapping("/addComputer")
public class AddComputerController {
	
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

	
	
	public AddComputerController() {
		logger = LoggerFactory.getLogger(AddComputerController.class);
	}
	
	@GetMapping
	public String getMapping(
			@RequestParam(name = "errorsList", required = false, defaultValue = "") List<String> errorsList,
			Model model) {
		logger.debug("getMapping(model={})", model);
		
		List<CompanyWDto> companiesDtoList = getCompaniesWDtoList(errorsList);

		model.addAttribute("computerDto", new ComputerWDtoBuilder().build());
		model.addAttribute("errorsList", errorsList);
		model.addAttribute("companiesDtoList", companiesDtoList);
		
		return "addComputer";
	}
	
	@PostMapping
	public String postMapping(
			@ModelAttribute("computerDto") ComputerWDto computerWDto,
			Model model) {
		logger.debug("postMapping({})", computerWDto);
		
		List<String> errorsList = new ArrayList<String>(0);
		
		try {
			computerWDto.setId("0");
			computerWDtoValidator.validate(computerWDto);
			addComputerWDto(computerWDto);
			return "redirect:/dashboard";
		} catch (InvalidComputerException e) {
			logger.debug("InvalidComputerException: {} in {}", e, e.getStackTrace());
			errorsList.add(e.getMessage());
			
		} catch (DatabaseConnectionException e) {
			errorsList.add("Cannot add this computer. " + e.getMessage());
		}
		logger.info("computerDto before sending: {}", computerWDto);
		model.addAttribute("errorsList", errorsList);
		model.addAttribute("computerDto", computerWDto);
		return "redirect:/addComputer";
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
	
	private void addComputerWDto(ComputerWDto computerWDto) throws DatabaseConnectionException {
		Computer computer = computerWDtoMapper.fromComputerWDtoToComputer(computerWDto);
		computerService.addComputer(computer);
	}
}
