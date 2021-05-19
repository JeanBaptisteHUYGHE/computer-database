package com.excilys.cdb.ui.menu;

import java.util.NoSuchElementException;

public enum EnumComputerMenuActions {
	EDIT_COMPUTER(1, "Edit this computer"),
	DELETE_COMPUTER(2, "Delete this computer"),
	EXIT(9, "Exit");

	private final int value;
	private final String representation;
	
	EnumComputerMenuActions(int value, String representation) {
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
	public static EnumComputerMenuActions getAction(int actionId) throws NoSuchElementException {
		for (EnumComputerMenuActions myEnum : EnumComputerMenuActions.values()) {
			if (myEnum.value == actionId) {
				return myEnum;
			}
		}
		throw new NoSuchElementException();
	}
}
