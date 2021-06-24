package com.excilys.cdb.console.view;

import java.util.List;

import com.excilys.cdb.console.enumeration.EnumPageMenuActions;
import com.excilys.cdb.console.dto.CompanyCDto;
import com.excilys.cdb.console.dto.ComputerCDto;

public class PaginationMenuView extends MenuView {
	
	/**
	 * Draw the menu interface.
	 */
	public  void drawInterface() {
		System.out.println("\n======================");
		System.out.println("[PAGE MENU]\n");
		System.out.println();
		for (EnumPageMenuActions action : EnumPageMenuActions.values()) {
			System.out.println(action);
		}
	}
	
	/**
	 * Draw the current page of computer list.
	 * @param computersCDtoList the computersCDto list to draw
	 */
	public void drawComputersCDtoPage(List<ComputerCDto> computersCDtoList) {
		System.out.println("ID\tNAME                                                      \t"
				+ "INTRODUCTION DATE\tDISCONTINUE DATE\tMANUFACTURER ID\tMANUFACTURER NAME");
		for (ComputerCDto computerCDto : computersCDtoList) {
			System.out.println(String.format("%-6s\t%-60s\t%-20s\t%-20s\t%-15s\t%s",
					computerCDto.getId(),
					computerCDto.getName(),
					computerCDto.getIntroductionDate(),
					computerCDto.getDiscontinueDate(),
					computerCDto.getCompanyId(),
					computerCDto.getCompanyName()
					)
				);
		}
	}
	
	public void drawCompaniesCDtoPage(List<CompanyCDto> companiesCDtoList) {
		System.out.println("ID\tNAME");
		for (CompanyCDto companyCDto : companiesCDtoList) {
			System.out.println(String.format("%s\t%s", companyCDto.getId(), companyCDto.getName()));
		}
	}
}
