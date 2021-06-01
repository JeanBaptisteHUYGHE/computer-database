package com.excilys.cdb.cli.view;

import com.excilys.cdb.cli.enumeration.EnumComputerMenuActions;
import com.excilys.cdb.dto.ComputerDto;

public class ComputerMenuView extends MenuView {

	/**
	 * Draw the menu interface.
	 */
	public void drawInterface(ComputerDto computerDto) {
		System.out.println("\n======================");
		System.out.println("[COMPUTER MENU]");
		drawComputer(computerDto);
		System.out.println();
		for (EnumComputerMenuActions action : EnumComputerMenuActions.values()) {
			System.out.println(action);
		}
	}
	
	/**
	 * Draw computer representation.
	 */
	private void drawComputer(ComputerDto computerDto) {
		System.out.println("Selected Computer:"
			+ "\n\tId: " + computerDto.getId()
			+ "\n\tName: " + computerDto.getName()
			+ "\n\tIntroduction date: " + computerDto.getIntroductionDate()
			+ "\n\tDiscontinue date: " + computerDto.getDiscontinueDate());
		if (computerDto.getCompanyId() != null || computerDto.getCompanyName() != null) {
			System.out.println("\tManufacturer:"
				+ "\n\t\tManufacturer id: " + computerDto.getCompanyId()
				+ "\n\t\tManufacturer name: " + computerDto.getCompanyName()
				);
		} else {
			System.out.println("\tNo Manufacturer");
		}
	}
}
