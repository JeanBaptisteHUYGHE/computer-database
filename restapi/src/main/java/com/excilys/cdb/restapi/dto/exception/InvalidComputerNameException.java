package com.excilys.cdb.restapi.dto.exception;

public class InvalidComputerNameException extends InvalidComputerException {

	private static final long serialVersionUID = -2985324892708155335L;

	public InvalidComputerNameException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
