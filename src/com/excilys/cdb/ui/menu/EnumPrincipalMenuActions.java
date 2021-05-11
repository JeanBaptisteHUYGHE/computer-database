package com.excilys.cdb.ui.menu;

public enum EnumPrincipalMenuActions {
	LIST_ALL_COMPUTERS(1),
	LIST_ALL_COMPANIES(2),
	SELECT_COMPUTER(3),
	ADD_NEW_COMPUTER(4),
	EXIT(9);

	private int value;
	
	EnumPrincipalMenuActions(int value) {
		this.value = value;
	}
}
