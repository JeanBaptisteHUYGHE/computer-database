package com.excilys.cdb.restapi.exception;

public class MissingOrInvalidComputerIdException extends Exception {

	private static final long serialVersionUID = 5865182580957751864L;

	public MissingOrInvalidComputerIdException() {
		super("The selected computer don't exist (it dont have a (correct) id");
	}
}
