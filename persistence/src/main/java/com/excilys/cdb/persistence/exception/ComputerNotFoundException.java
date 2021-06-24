package com.excilys.cdb.persistence.exception;

public class ComputerNotFoundException extends Exception {

	private static final long serialVersionUID = 410477738929017041L;

	public ComputerNotFoundException() {
		super("Computer not found");
	}
}
