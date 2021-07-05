package com.excilys.cdb.restapi.dto.exception;

public class InvalidComputerDateFormatException extends InvalidComputerException {

	private static final long serialVersionUID = 8960549640144396941L;

	public InvalidComputerDateFormatException(String exceptionMessage) {
		super(exceptionMessage);
	}
}
