package com.excilys.cdb.exception.dao;

public class ComputerNotFoundException extends Exception {

	private static final long serialVersionUID = 410477738929017041L;

	public ComputerNotFoundException() {
		super("Computer not found");
	}
}
