package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet( name = "AddComputerServlet", urlPatterns = "/addComputer")
public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 8762274583542611999L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/addComputer.html" ).forward( request, response );
	}
}

