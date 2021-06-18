package com.excilys.cdb.console.view;

import com.excilys.cdb.console.enumeration.EnumComputerMenuActions;
import com.excilys.cdb.console.dto.ComputerCDto;

public class ComputerMenuView extends MenuView {

	/**
	 * Draw the menu interface.
	 * @param computerCDto the computerCDto to draw
	 */
	public void drawInterface(ComputerCDto computerCDto) {
		System.out.println("\n======================");
		System.out.println("[COMPUTER MENU]");
		drawComputer(computerCDto);
		System.out.println();
		for (EnumComputerMenuActions action : EnumComputerMenuActions.values()) {
			System.out.println(action);
		}
	}
	
	/**
	 * Draw computer representation.
	 * @param computerCDto the computerCDto to draw
	 */
	private void drawComputer(ComputerCDto computerCDto) {
		System.out.println("Selected Computer:"
			+ "\n\tId: " + computerCDto.getId()
			+ "\n\tName: " + computerCDto.getName()
			+ "\n\tIntroduction date: " + computerCDto.getIntroductionDate()
			+ "\n\tDiscontinue date: " + computerCDto.getDiscontinueDate());
		if (computerCDto.getCompanyId() != null || computerCDto.getCompanyName() != null) {
			System.out.println("\tManufacturer:"
				+ "\n\t\tManufacturer id: " + computerCDto.getCompanyId()
				+ "\n\t\tManufacturer name: " + computerCDto.getCompanyName()
				);
		} else {
			System.out.println("\tNo Manufacturer");
		}
	}
}
