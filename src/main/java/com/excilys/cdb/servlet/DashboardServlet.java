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
import com.excilys.cdb.service.ComputerDtoService;


@WebServlet(urlPatterns = {"/home", "/dashboard"})
public class DashboardServlet extends HttpServlet {
	
	private static final long serialVersionUID = -4389678413225540694L;
	
	private ComputerDtoService computerDtoService;
	private Logger logger;
	
	public DashboardServlet() {
		computerDtoService = ComputerDtoService.getInstance();
		logger = LoggerFactory.getLogger(DashboardServlet.class);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("doGet(...)");
		List<ComputerDto> computersDtoList;
		try {
			computersDtoList = computerDtoService.getComputersListPage(1, 10);
		} catch (SQLException e) {
			computersDtoList = new ArrayList<ComputerDto>();
			logger.error("{} in {}", e, e.getStackTrace());
		}
		request.setAttribute("computersList", computersDtoList);
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
	}
}

