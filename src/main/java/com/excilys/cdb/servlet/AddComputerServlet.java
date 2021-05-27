package com.excilys.cdb.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
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
import com.excilys.cdb.dto.ComputerDtoMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyDtoService;
import com.excilys.cdb.service.ComputerService;


@WebServlet( name = "AddComputerServlet", urlPatterns = "/addComputer")
public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 8762274583542611999L;
	private Logger logger;
	
	// private CompanyDtoService companyDtoService;
	
	public AddComputerServlet() {
		logger = LoggerFactory.getLogger(AddComputerServlet.class);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("doGet(...)");
		try {
			List<CompanyDto> companiesList = CompanyDtoService.getInstance().getCompaniesDtoList();
			request.setAttribute("companiesList", companiesList);
		} catch (SQLException e) {
			logger.error("Can't get companies list: {} in {}", e, e.getStackTrace());
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/addComputer.jsp").forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("doPost(...)");
		ComputerDto computerDto = getComputerDtoAttributFromPost(request);
		
		String errorMessage = null;
		
		try {
			ComputerDtoMapper computerDtoMapper = ComputerDtoMapper.getInstance();
			Computer computer = computerDtoMapper.getComputer(computerDto);
			ComputerService computerService = ComputerService.getInstance();
			computerService.addComputer(computer);
			response.sendRedirect("dashboard");
		} catch (IllegalArgumentException e) {
			logger.info("Computer not add in POST method, invalid argument: {} in {}", e, e.getStackTrace());
			errorMessage = "Computer name can't be empty and precedence between dates need to be valid.";
			request.setAttribute("errorMessage", errorMessage);
			doGet(request, response);
		} catch (DateTimeParseException e) {
			logger.info("Computer not add in POST method, invalid date format: {} in {}", e, e.getStackTrace());
			errorMessage = "Invalid date format";
			request.setAttribute("errorMessage", errorMessage);
			doGet(request, response);
		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			errorMessage = "Error with the database";
			request.setAttribute("errorMessage", errorMessage);
			doGet(request, response);
		}
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
}

