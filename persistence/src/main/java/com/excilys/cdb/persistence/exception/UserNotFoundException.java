package com.excilys.cdb.persistence.exception;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 8811915502222980570L;

	public UserNotFoundException() {
		super("User not found");
	}
}
