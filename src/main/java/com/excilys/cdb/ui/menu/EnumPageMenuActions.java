package com.excilys.cdb.ui.menu;

import java.util.NoSuchElementException;

public enum EnumPageMenuActions {
	NEXT_PAGE(1, "Next page"),
	PREVIOUS_PAGE(2, "Previous page"),
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
	 * @throws NoSuchElementException
	 */
	public static EnumPageMenuActions getAction(int actionId) throws NoSuchElementException {
		for (EnumPageMenuActions myEnum : EnumPageMenuActions.values()) {
			if (myEnum.value == actionId) {
				return myEnum;
			}
		}
		throw new NoSuchElementException();
	}
}
