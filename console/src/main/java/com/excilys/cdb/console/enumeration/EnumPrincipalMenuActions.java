package com.excilys.cdb.console.enumeration;

import com.excilys.cdb.console.exception.InvalidActionChoiceException;

public enum EnumPrincipalMenuActions {
	LIST_ALL_COMPUTERS(1, "List all computers"),
	LIST_ALL_COMPANIES(2, "List all companies"),
	SELECT_COMPUTER(3, "Select a computer"),
	ADD_NEW_COMPUTER(4, "Add a new computer"),
	DELETE_COMPANY(5, "Delete a company"),
	EXIT(9, "Exit");

	private final int value;
	private final String representation;
	
	EnumPrincipalMenuActions(int value, String representation) {
		this.value = value;
		this.representation = representation;
	}
	
	public String toString() {
		return String.format("[%s]\t%s", this.value, this.representation);
	}
	
	/**
	 * Return the enumeration for this action.
	 * @param actionId
	 * @return the correspondent enumeration value or an exception
	 * @throws InvalidActionChoiceException
	 */
	public static EnumPrincipalMenuActions getAction(int actionId) throws InvalidActionChoiceException {
		for (EnumPrincipalMenuActions myEnum : EnumPrincipalMenuActions.values()) {
			if (myEnum.value == actionId) {
				return myEnum;
			}
		}
		throw new InvalidActionChoiceException();
	}
}
