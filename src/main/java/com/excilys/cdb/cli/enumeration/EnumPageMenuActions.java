package com.excilys.cdb.cli.enumeration;

import com.excilys.cdb.exception.cli.InvalidActionChoiceException;

public enum EnumPageMenuActions {
	FIRST_PAGE(1, "First page"),
	PREVIOUS_PAGE(2, "Previous page"),
	NEXT_PAGE(3, "Next page"),
	LAST_PAGE(4, "Last page"),
	EXIT(9, "Exit");

	private final int value;
	private final String representation;
	
	EnumPageMenuActions(int value, String representation) {
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
	public static EnumPageMenuActions getAction(int actionId) throws InvalidActionChoiceException {
		for (EnumPageMenuActions myEnum : EnumPageMenuActions.values()) {
			if (myEnum.value == actionId) {
				return myEnum;
			}
		}
		throw new InvalidActionChoiceException();
	}
}
