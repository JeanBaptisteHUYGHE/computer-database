package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet( name = "EditComputerServlet", urlPatterns = "/editComputer")
public class EditComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 2713830462205183398L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/editComputer.html" ).forward( request, response );
	}
}

