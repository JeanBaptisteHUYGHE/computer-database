package com.excilys.cdb.restapi.dto.exception;

public class InvalidComputerDatesPrecedenceException extends InvalidComputerException {

	private static final long serialVersionUID = 7237846501243347183L;
	
	public InvalidComputerDatesPrecedenceException(String exceptionMessage) {
		super(exceptionMessage);
	}
}
