package com.excilys.cdb.exception.cli;

public class InvalidUserChoiceException extends Exception {

	private static final long serialVersionUID = 2709128506069630575L;

	public InvalidUserChoiceException() {
		super("Invalid choice, please retry");
	}
}
