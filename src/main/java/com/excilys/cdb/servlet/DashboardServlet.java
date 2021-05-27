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

import com.excilys.cdb.controller.DashboardController;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.Page.PageBuilder;
import com.excilys.cdb.service.ComputerDtoService;
import com.excilys.cdb.service.ComputerService;


@WebServlet(urlPatterns = {"/home", "/dashboard"})
public class DashboardServlet extends HttpServlet {
	
	private static final long serialVersionUID = -4389678413225540694L;
	
	private DashboardController dashboardController;
	private Logger logger;
	
	public DashboardServlet() {
		dashboardController = new DashboardController();
		logger = LoggerFactory.getLogger(DashboardServlet.class);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("doGet(...)");
		dashboardController.setRequestedPage(request.getParameter("page"));

		request.setAttribute("computersList", dashboardController.getComputersDtoList());
		request.setAttribute("computersCount", dashboardController.getComputersCount());
		request.setAttribute("page", dashboardController.getPage());
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
	}
}

