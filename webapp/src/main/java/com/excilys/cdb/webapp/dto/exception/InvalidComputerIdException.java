package com.excilys.cdb.webapp.dto.exception;

public class InvalidComputerIdException extends InvalidComputerException {

	private static final long serialVersionUID = -3045932030836447674L;
	
	public InvalidComputerIdException(String exceptionMessage) {
		super(exceptionMessage);
	}
}
