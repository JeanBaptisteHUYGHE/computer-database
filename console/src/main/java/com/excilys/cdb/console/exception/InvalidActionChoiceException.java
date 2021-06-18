package com.excilys.cdb.console.exception;

public class InvalidActionChoiceException extends Exception {

	private static final long serialVersionUID = 2709128506149630575L;

	public InvalidActionChoiceException() {
		super("Invalid action in this menu, please retry");
	}
}
