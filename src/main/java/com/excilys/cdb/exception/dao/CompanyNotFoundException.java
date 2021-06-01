package com.excilys.cdb.exception.dao;

public class CompanyNotFoundException extends Exception {

	private static final long serialVersionUID = -2446998977442651941L;

	public CompanyNotFoundException() {
		super("Company not found");
	}
}
