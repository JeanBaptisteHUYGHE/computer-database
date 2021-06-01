package com.excilys.cdb.cli.view;

import com.excilys.cdb.cli.enumeration.EnumPrincipalMenuActions;

public class PrincipalMenuView extends MenuView {

	/**
	 * Draw the menu interface.
	 */
	public void drawInterface() {
		System.out.println("\n======================");
		System.out.println("[PRINCIPAL MENU]");
		for (EnumPrincipalMenuActions action : EnumPrincipalMenuActions.values()) {
			System.out.println(action);
		}
	}
}
