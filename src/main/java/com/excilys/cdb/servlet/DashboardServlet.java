package com.excilys.cdb.servlet;

import java.io.IOException;
import java.sql.SQLException;
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
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.Page.PageBuilder;
import com.excilys.cdb.service.ComputerDtoService;
import com.excilys.cdb.service.ComputerService;


@WebServlet(urlPatterns = {"/home", "/dashboard"})
public class DashboardServlet extends HttpServlet {
	
	private static final long serialVersionUID = -4389678413225540694L;
	
	private ComputerDtoService computerDtoService;
	private ComputerService computerService;
	private Page page;
	private Logger logger;
	
	public DashboardServlet() {
		computerDtoService = ComputerDtoService.getInstance();
		computerService = ComputerService.getInstance();
		logger = LoggerFactory.getLogger(DashboardServlet.class);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("doGet(...)");
		String strRequestedPage = request.getParameter("page");
		Integer requestedPage = Page.MINIMUM_PAGE_INDEX;
		try {
			requestedPage = Integer.valueOf(strRequestedPage);
		}
		catch (NumberFormatException e) { }
		List<ComputerDto> computersDtoList;
		Integer computersCount;
		PageBuilder pageBuilder = new PageBuilder();
		try {
			computersCount = computerService.getComputerCount();
			pageBuilder.withElementsCount(computersCount);
			pageBuilder.withPageIndex(requestedPage);
			page = pageBuilder.build();
			logger.info(page.toString());
			computersDtoList = computerDtoService.getComputersListPage(page.getPageIndex(), page.getPageSize());
		} catch (SQLException e) {
			computersDtoList = new ArrayList<ComputerDto>();
			computersCount = -1;
			logger.error("{} in {}", e, e.getStackTrace());
		}
		request.setAttribute("computersList", computersDtoList);
		request.setAttribute("computersCount", computersCount);
		request.setAttribute("page", page);
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
	}
}

