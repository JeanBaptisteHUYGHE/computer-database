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
import com.excilys.cdb.exception.dao.ComputerNotFoundException;
import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.excilys.cdb.exception.dto.InvalidComputerException;
import com.excilys.cdb.exception.servlet.MissingOrInvalidComputerIdException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;


@WebServlet(name = "EditComputerServlet", urlPatterns = "/editComputer")
public class EditComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 2713830462205183398L;
	
	private Logger logger;
	private CompanyService companyService;
	private CompanyDtoMapper companyDtoMapper;
	private ComputerDtoMapper computerDtoMapper;
	private ComputerDtoValidator computerDtoValidator;
	private ComputerService computerService;
	
	public EditComputerServlet() {
		logger = LoggerFactory.getLogger(EditComputerServlet.class);
		companyService = CompanyService.getInstance();
		companyDtoMapper = CompanyDtoMapper.getInstance();
		computerDtoMapper = ComputerDtoMapper.getInstance();
		computerDtoValidator = ComputerDtoValidator.getInstance();
		computerService = ComputerService.getInstance();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("doGet(...)");
		List<String> errorsList = getErrorsList(request.getAttribute("errorsList"));
		List<CompanyDto> companiesDtoList = getCompaniesDtoList(errorsList);
		
		try {
			Integer computerId = getComputerId(request.getParameter("id"));
			Computer computer = computerService.getComputerById(computerId);
			ComputerDto computerDto = computerDtoMapper.fromComputerToComputerDto(computer);
			request.setAttribute("computerDto", computerDto);
			
		} catch (MissingOrInvalidComputerIdException | ComputerNotFoundException | DatabaseConnectionException e) {
			errorsList.add(e.getMessage());
		}
		
		request.setAttribute("errorsList", errorsList);
		request.setAttribute("companiesDtoList", companiesDtoList);
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/editComputer.jsp").forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("doPost(...)");
		ComputerDto computerDto = getComputerDtoAttributFromPost(request);
		
		List<String> errorsList = new ArrayList<String>(0);
		
		try {
			computerDtoValidator.validate(computerDto);
			Computer computer = computerDtoMapper.fromComputerDtoToComputer(computerDto);
			computerService.updateComputer(computer);
			response.sendRedirect("dashboard");
		} catch (InvalidComputerException e) {
			errorsList.add(e.getMessage());
			request.setAttribute("errorsList", errorsList);
			doGet(request, response);
		} catch (DatabaseConnectionException e) {
			errorsList.add("Cannot edit this computer. " + e.getMessage());
			request.setAttribute("errorsList", errorsList);
			doGet(request, response);
		}
	}
	
	private Integer getComputerId(String stringId) throws MissingOrInvalidComputerIdException {
		if (stringId == null || stringId.isBlank()) {
			throw new MissingOrInvalidComputerIdException();
		}
		try {
			return Integer.valueOf(stringId);
		} catch (NumberFormatException e) {
			throw new MissingOrInvalidComputerIdException();
		}
	}

	@SuppressWarnings("unchecked")
	private List<String> getErrorsList(Object errorsListObject) {
		List<String> errorsList = null;
		try {
			errorsList = (List<String>) errorsListObject;
		} catch (ClassCastException e) { }
		
		if (errorsList == null) {
			errorsList = new ArrayList<String>(0);
		}
		return errorsList;
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
	
	/**
	 * Get / read the computer dto from the addComputer form attributes.
	 * @param request the user request
	 * @return the computerDto
	 */
	private ComputerDto getComputerDtoAttributFromPost(HttpServletRequest request) {	
		ComputerDtoBuilder computerDtoBuilder = new ComputerDtoBuilder()
			.withId(request.getParameter("computerId"))
			.withName(request.getParameter("computerName"))
			.withIntroductionDate(request.getParameter("introductionDate").replace('-', '/'))
			.withDiscontinueDate(request.getParameter("discontinueDate").replace('-', '/'))
			.withCompanyId(request.getParameter("companyId"));
		ComputerDto computerDto = computerDtoBuilder.build();
		logger.debug("ComputerDto readed: {}", computerDto.toString());
		return computerDto;
	}
}

