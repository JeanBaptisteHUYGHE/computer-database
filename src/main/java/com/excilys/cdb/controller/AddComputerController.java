package com.excilys.cdb.controller;

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

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.ComputerDto.ComputerDtoBuilder;
import com.excilys.cdb.dto.mapper.CompanyDtoMapper;
import com.excilys.cdb.dto.mapper.ComputerDtoMapper;
import com.excilys.cdb.dto.validator.ComputerDtoValidator;
import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.excilys.cdb.exception.dto.InvalidComputerException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping("/addComputer")
public class AddComputerController {
	
	private Logger logger;

	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyDtoMapper companyDtoMapper;
	@Autowired
	private ComputerDtoMapper computerDtoMapper;
	@Autowired
	private ComputerDtoValidator computerDtoValidator;
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
		
		List<CompanyDto> companiesDtoList = getCompaniesDtoList(errorsList);

		model.addAttribute("computerDto", new ComputerDtoBuilder().build());
		model.addAttribute("errorsList", errorsList);
		model.addAttribute("companiesDtoList", companiesDtoList);
		
		return "/WEB-INF/jsp/addComputer.jsp";
	}
	
	@PostMapping
	public String postMapping(
			@ModelAttribute("computerDto") ComputerDto computerDto,
			Model model) {
		logger.debug("postMapping({})", computerDto);
		
		List<String> errorsList = new ArrayList<String>(0);
		
		try {
			computerDto.setId("0");
			computerDtoValidator.validate(computerDto);
			addComputerDto(computerDto);
			return "redirect:/dashboard";
		} catch (InvalidComputerException e) {
			logger.debug("InvalidComputerException: {} in {}", e, e.getStackTrace());
			errorsList.add(e.getMessage());
			
		} catch (DatabaseConnectionException e) {
			errorsList.add("Cannot add this computer. " + e.getMessage());
		}
		logger.info("computerDto before sending: {}", computerDto);
		model.addAttribute("errorsList", errorsList);
		model.addAttribute("computerDto", computerDto);
		return "redirect:/addComputer";
	}
	
	private List<CompanyDto> getCompaniesDtoList(List<String> errorsList) {
		List<CompanyDto> companiesDtoList;
		
		try {
			List<Company> companiesList;
			companiesList = companyService.getCompaniesList();
			companiesDtoList = companyDtoMapper.fromCompaniesListToCompaniesDtoList(companiesList);
		} catch (DatabaseConnectionException e) {
			companiesDtoList = new ArrayList<CompanyDto>(0);
			errorsList.add("Cannot get companies list. " + e.getMessage());
		}
		return companiesDtoList;
	}
	
	private void addComputerDto(ComputerDto computerDto) throws DatabaseConnectionException {
		Computer computer = computerDtoMapper.fromComputerDtoToComputer(computerDto);
		computerService.addComputer(computer);
	}
}
