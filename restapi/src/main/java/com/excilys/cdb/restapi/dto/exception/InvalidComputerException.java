package com.excilys.cdb.restapi.dto.exception;

public class InvalidComputerException extends Exception {

	private static final long serialVersionUID = -4138435511733046721L;

	public InvalidComputerException(String exceptionMessage) {
		super(exceptionMessage);
	}
}
