package com.excilys.cdb.ui.page;

import static java.lang.Math.max;
import java.sql.SQLException;
import java.util.List;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;

public class ComputersPage extends AbstPage {
		
	/**
	 * Draw the current page of computer list
	 * @throws SQLException
	 */
	@Override
	public void drawPage() throws SQLException {
		List<Computer> computersList = new ComputerService().getComputersListPage(this.pageIndex, PAGE_SIZE);
		System.out.println("ID\tNAME                                                      \t"
				+ "INTRODUCTION DATE\tDISCONTINUE DATE\tMANUFACTURER ID");
		for (Computer computer : computersList) {
			System.out.println(String.format("%-6s\t%-60s\t%-20s\t%-20s\t%s",
					computer.getId(), computer.getName(), computer.getIntroductionDate(),
					computer.getDiscontinueDate(), computer.getManufacturer().getId()
					)
				);
		}
	}
}
