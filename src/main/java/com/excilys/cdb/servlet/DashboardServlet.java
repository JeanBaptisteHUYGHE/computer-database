package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		List<String> errorsList = getErrorsList(request.getAttribute("errorsList"));
		String search = getUserSearch(request.getParameter("search"));
		Integer requestedPage = getRequestedPage(request.getParameter("page"));
		Integer requestedPageSize = getRequestedPageSize(request.getParameter("pageSize"), request.getSession());
		Integer computersCount = getComputersCountForSearch(search, errorsList);
		Page page = getPage(requestedPage, computersCount, requestedPageSize);
		List<ComputerDto> computersDtoList = getComputersDtoListForSearch(search, page, errorsList);
				
		request.setAttribute("errorsList", errorsList);
		request.setAttribute("computersList", computersDtoList);
		request.setAttribute("search", search);
		request.setAttribute("computersCount", computersCount);
		request.setAttribute("page", page);
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("doPost(...)");
		List<String> errorsList = new ArrayList<String>(0);
		String selection = request.getParameter("selection");
		
		try {
			if (selection != null) {
				String[] stringsIds = selection.split(",");
				for (String stringId : stringsIds) {
					try {
						Integer id = Integer.valueOf(stringId);
						computerService.deleteComputer(id);
					} catch (NumberFormatException e) { }
				}
			}
		} catch (DatabaseConnectionException e) {
			errorsList.add("Computer(s) cannot be delete. " + e.getMessage());
		}
		request.setAttribute("errorsList", errorsList);
		doGet(request, response);
	}
	
	private String getUserSearch(String search) {
		if (search == null) {
			return "";
		}
		return search;
	}
	
	private Integer getRequestedPage(String strRequestedPage) {
		Integer requestedPage = Page.MINIMUM_PAGE_INDEX;
		try {
			requestedPage = Integer.valueOf(strRequestedPage);	
		} catch (NumberFormatException e) { }
		return requestedPage;
	}
	
	private Integer getRequestedPageSize(String strRequestedPageSize, HttpSession session) {
		Integer pageSize;
		Integer userPageSize = (Integer) session.getAttribute("userPageSize");
		if (userPageSize == null) {
			pageSize = Page.DEFAULT_PAGE_SIZE;
		} else {
			pageSize = userPageSize;
		}
		try {
			pageSize = Integer.valueOf(strRequestedPageSize);	
		} catch (NumberFormatException e) { }
		session.setAttribute("userPageSize", pageSize);
		return pageSize;
	}
	
	private Integer getComputersCountForSearch(String search, List<String> errorsList) {
		Integer computersCount = DEFAULT_COMPUTERS_COUNT;
		try {
			computersCount = computerService.getComputersCountForSearch(search);
		} catch (DatabaseConnectionException e) {
			errorsList.add("Error while getting the computers count. " + e.getMessage());
		}
		return computersCount;
	}
	
	private Page getPage(Integer requestedPage, Integer computersCount, Integer requestedPageSize) {
		PageBuilder pageBuilder = new PageBuilder()
				.withElementsCount(computersCount)
				.withIndex(requestedPage)
				.withSize(requestedPageSize);
		Page page = pageBuilder.build();
		logger.info(page.toString());
		return page;
	}
	
	private List<ComputerDto> getComputersDtoListForSearch(String search, Page page, List<String> errorsList) {
		List<ComputerDto> computersDtoList;
		try {
			List<Computer> computersList = computerService.getComputersListPageForSearch(search, page);
			computersDtoList = ComputerDtoMapper.getInstance().fromComputersListToComputersDtoList(computersList);

		} catch (DatabaseConnectionException e) {
			computersDtoList = new ArrayList<ComputerDto>();
			errorsList.add("Error while getting the computers list. " + e.getMessage());
		}
		return computersDtoList;
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
}

