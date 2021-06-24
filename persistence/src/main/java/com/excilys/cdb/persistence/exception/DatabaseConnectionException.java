package com.excilys.cdb.persistence.exception;

public class DatabaseConnectionException extends Exception {

	private static final long serialVersionUID = -49880099918179817L;

	public DatabaseConnectionException() {
		super("An error has occurred during the database connection, please contact your administrator.");
	}
}
