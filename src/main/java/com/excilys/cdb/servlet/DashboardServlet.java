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

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.mapper.ComputerDtoMapper;
import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.Page.PageBuilder;
import com.excilys.cdb.service.ComputerService;


@WebServlet(urlPatterns = {"/home", "/dashboard"})
public class DashboardServlet extends HttpServlet {
	
	private static final long serialVersionUID = -4389678413225540694L;
	private static final int DEFAULT_COMPUTERS_COUNT = 0;
	
	private ComputerService computerService;
	private Logger logger;
	
	public DashboardServlet() {
		computerService = ComputerService.getInstance();
		logger = LoggerFactory.getLogger(DashboardServlet.class);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("doGet(...)");
		List<String> errorsList = new ArrayList<String>(0);
		Integer requestedPage = getRequestedPage(request.getParameter("page"));
		Integer computersCount = getComputersCount(errorsList);
		Page page = getPage(requestedPage, computersCount);
		List<ComputerDto> computersDtoList = getComputersDtoList(page, errorsList);
		
		
		
		request.setAttribute("errorsList", errorsList);
		request.setAttribute("computersList", computersDtoList);
		request.setAttribute("computersCount", computersCount);
		request.setAttribute("page", page);
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
	}
	
	private Integer getRequestedPage(String strRequestedPage) {
		Integer requestedPage = Page.MINIMUM_PAGE_INDEX;
		try {
			requestedPage = Integer.valueOf(strRequestedPage);	
		} catch (NumberFormatException e) { }
		return requestedPage;
	}
	
	private Integer getComputersCount(List<String> errorsList) {
		Integer computersCount = DEFAULT_COMPUTERS_COUNT;
		try {
			computersCount = computerService.getComputerCount();
		} catch (DatabaseConnectionException e) {
			errorsList.add("Error while getting the computers count. " + e.getMessage());
		}
		return computersCount;
	}
	
	private Page getPage(Integer requestedPage, Integer computersCount) {
		PageBuilder pageBuilder = new PageBuilder();
		pageBuilder.withElementsCount(computersCount);
		pageBuilder.withIndex(requestedPage);
		Page page = pageBuilder.build();
		logger.info(page.toString());
		return page;
	}
	
	private List<ComputerDto> getComputersDtoList(Page page, List<String> errorsList) {
		List<ComputerDto> computersDtoList;
		try {
			List<Computer> computersList = computerService.getComputersListPage(page);
			computersDtoList = ComputerDtoMapper.getInstance().fromComputersListToComputersDtoList(computersList);

		} catch (DatabaseConnectionException e) {
			computersDtoList = new ArrayList<ComputerDto>();
			errorsList.add("Error while getting the computers list. " + e.getMessage());
		}
		return computersDtoList;
	}
}

