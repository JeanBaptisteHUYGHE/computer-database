package com.excilys.cdb.cli.view;

import java.util.List;

import com.excilys.cdb.cli.enumeration.EnumPageMenuActions;
import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;

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
	 */
	public void drawComputersDtoPage(List<ComputerDto> computersDtoList) {
		System.out.println("ID\tNAME                                                      \t"
				+ "INTRODUCTION DATE\tDISCONTINUE DATE\tMANUFACTURER ID\tMANUFACTURER NAME");
		for (ComputerDto computerDto : computersDtoList) {
			System.out.println(String.format("%-6s\t%-60s\t%-20s\t%-20s\t%-15s\t%s",
					computerDto.getId(),
					computerDto.getName(),
					computerDto.getIntroductionDate(),
					computerDto.getDiscontinueDate(),
					computerDto.getCompanyId(),
					computerDto.getCompanyName()
					)
				);
		}
	}
	
	public void drawCompaniesDtoPage(List<CompanyDto> companiesDtoList) {
		System.out.println("ID\tNAME");
		for (CompanyDto companyDto : companiesDtoList) {
			System.out.println(String.format("%s\t%s", companyDto.getId(), companyDto.getName()));
		}
	}
}
