package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


@WebServlet( name = "AddComputerServlet", urlPatterns = "/addComputer")
public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 8762274583542611999L;
	
	private CompanyService companyService;
	private CompanyDtoMapper companyDtoMapper;
	private ComputerDtoMapper computerDtoMapper;
	private ComputerDtoValidator computerDtoValidator;
	private ComputerService computerService;
	private Logger logger;
	
	// private CompanyDtoService companyDtoService;
	
	public AddComputerServlet() {
		logger = LoggerFactory.getLogger(AddComputerServlet.class);
		companyService = CompanyService.getInstance();
		companyDtoMapper = CompanyDtoMapper.getInstance();
		computerDtoMapper = ComputerDtoMapper.getInstance();
		computerDtoValidator = ComputerDtoValidator.getInstance();
		computerService = ComputerService.getInstance();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("doGet(...)");
		List<String> errorsList = getErrorsList(request.getAttribute("errorsList"));
		List<CompanyDto> companiesDtoList = getCompaniesDto(errorsList);

		request.setAttribute("errorsList", errorsList);
		request.setAttribute("companiesList", companiesDtoList);
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/addComputer.jsp").forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("doPost(...)");
		ComputerDto computerDto = getComputerDtoAttributFromPost(request);
		
		List<String> errorsList = new ArrayList<String>(0);
		
		try {
			computerDtoValidator.validate(computerDto);
			addComputerDto(computerDto);
			response.sendRedirect("dashboard");
		} catch (InvalidComputerException e) {
			errorsList.add(e.getMessage());
			request.setAttribute("errorsList", errorsList);
			doGet(request, response);
		} catch (DatabaseConnectionException e) {
			errorsList.add("Cannot add this computer. " + e.getMessage());
			request.setAttribute("errorsList", errorsList);
			doGet(request, response);
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<String> getErrorsList(Object errorsListObject) {
		logger.info("ici: " + errorsListObject.toString());
		List<String> errorsList = null;
		try {
			errorsList = (List<String>) errorsListObject;
		} catch (ClassCastException e) { }
		
		if (errorsList == null) {
			errorsList = new ArrayList<String>(0);
		}
		return errorsList;
	}
	
	private List<CompanyDto> getCompaniesDto(List<String> errorsList) {
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
	
	/**
	 * Get / read the computer dto from the addComputer form attributs
	 * @param request the user request
	 * @return the computerDto
	 */
	private ComputerDto getComputerDtoAttributFromPost(HttpServletRequest request) {
		String computerName = request.getParameter("computerName");
		String introductionDate = request.getParameter("introductionDate");
		String discontinueDate = request.getParameter("discontinueDate");
		String companyId = request.getParameter("companyId");
		
		ComputerDtoBuilder computerDtoBuilder = new ComputerDtoBuilder();
		computerDtoBuilder.withName(computerName);
		computerDtoBuilder.withIntroductionDate(introductionDate.replace('-', '/'));
		computerDtoBuilder.withDiscontinueDate(discontinueDate.replace('-', '/'));
		computerDtoBuilder.withCompanyId(companyId);
		ComputerDto computerDto = computerDtoBuilder.build();
		logger.debug("ComputerDto readed: {}", computerDto.toString());
		return computerDto;
	}
	
	private void addComputerDto(ComputerDto computerDto) throws DatabaseConnectionException {
		Computer computer = computerDtoMapper.fromComputerDtoToComputer(computerDto);
		computerService.addComputer(computer);
	}
}

